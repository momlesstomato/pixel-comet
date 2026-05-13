package com.cometproject.server.storage.migration;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.MariaDBContainer;

import javax.sql.DataSource;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CurrencyInventoryMigrationTest {
    private static final String MIGRATION_RESOURCE = "db/migration/V6__currency_inventory_schema.sql";
    private static final String CATALOG_PRICE_MIGRATION_RESOURCE = "db/migration/V7__catalog_item_prices.sql";
    private static final String CURRENCY_POLISH_MIGRATION_RESOURCE = "db/migration/V8__currency_polish_roles_aliases.sql";
    private static final String PRODUCTION_MARIADB_IMAGE = "mariadb:11.4";
    private static final int PRODUCTION_MARIADB_PORT = 3306;

    @Test
    void currencyInventoryMigration_createsAndBackfillsInventory_onH2() throws Exception {
        final DataSource dataSource = this.createH2DataSource();

        this.createPlayersFixture(dataSource);
        this.runCurrencyMigration(dataSource);

        this.assertCurrencySchema(dataSource);
    }

    @Test
    void catalogPriceMigration_backfillsNormalizedPrices_onH2() throws Exception {
        final DataSource dataSource = this.createH2DataSource();

        this.createPlayersFixture(dataSource);
        this.createCatalogItemsFixture(dataSource);
        this.runCurrencyAndCatalogPriceMigrations(dataSource);

        assertEquals(3, this.queryForInt(dataSource, "SELECT COUNT(*) FROM catalog_item_prices"));
        assertEquals(10, this.queryForInt(dataSource,
                "SELECT cip.amount FROM catalog_item_prices cip JOIN currencies c ON c.id = cip.currency_id WHERE cip.catalog_item_id = 1 AND c.code = 'credits'"));
        assertEquals(5, this.queryForInt(dataSource,
                "SELECT cip.amount FROM catalog_item_prices cip JOIN currencies c ON c.id = cip.currency_id WHERE cip.catalog_item_id = 1 AND c.code = 'currency_0'"));
        assertEquals(7, this.queryForInt(dataSource,
                "SELECT cip.amount FROM catalog_item_prices cip JOIN currencies c ON c.id = cip.currency_id WHERE cip.catalog_item_id = 2 AND c.code = 'currency_5'"));
        assertEquals(1, this.queryForInt(dataSource,
                "SELECT COUNT(*) FROM currency_aliases ca JOIN currencies c ON c.id = ca.currency_id WHERE ca.alias = 'protocol_0' AND c.code = 'currency_0'"));
        assertEquals(1, this.queryForInt(dataSource,
                "SELECT COUNT(*) FROM currencies WHERE code = 'currency_105' AND noun_plural = 'Currency 105'"));
    }

    @Test
    @Tag("integration")
    void currencyInventoryMigration_createsAndBackfillsInventory_onMariaDb() throws Exception {
        Assumptions.assumeTrue(
                DockerClientFactory.instance().isDockerAvailable(),
                "Docker is required for the currency migration integration test.");

        try (MariaDBContainer<?> mariaDb = new MariaDBContainer<>(PRODUCTION_MARIADB_IMAGE)
                .withDatabaseName("pixel_comet_currency_test")
                .withUsername("pixel")
                .withPassword("pixel")) {
            mariaDb.start();

            try (HikariDataSource dataSource = this.createMariaDbDataSource(mariaDb)) {
                this.createPlayersFixture(dataSource);
                this.runCurrencyMigration(dataSource);

                this.assertCurrencySchema(dataSource);
            }
        }
    }

    private void runCurrencyMigration(final DataSource dataSource) throws Exception {
        final Path migrationDirectory = Files.createTempDirectory("currency_inventory_migration");
        final String migrationSql = this.readMigrationSql();

        Files.writeString(migrationDirectory.resolve("V1__currency_inventory_schema.sql"), migrationSql);

        Flyway.configure()
                .dataSource(dataSource)
                .locations("filesystem:" + migrationDirectory)
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .load()
                .migrate();
    }

    private void runCurrencyAndCatalogPriceMigrations(final DataSource dataSource) throws Exception {
        final Path migrationDirectory = Files.createTempDirectory("catalog_price_migration");

        Files.writeString(migrationDirectory.resolve("V1__currency_inventory_schema.sql"), this.readMigrationSql());
        Files.writeString(migrationDirectory.resolve("V2__catalog_item_prices.sql"), this.readResource(CATALOG_PRICE_MIGRATION_RESOURCE));
        Files.writeString(migrationDirectory.resolve("V3__currency_polish_roles_aliases.sql"), this.readResource(CURRENCY_POLISH_MIGRATION_RESOURCE));

        Flyway.configure()
                .dataSource(dataSource)
                .locations("filesystem:" + migrationDirectory)
                .baselineOnMigrate(true)
                .baselineVersion("0")
                .load()
                .migrate();
    }

    private String readMigrationSql() throws Exception {
        return this.readResource(MIGRATION_RESOURCE);
    }

    private String readResource(final String resource) throws Exception {
        try (InputStream inputStream = Objects.requireNonNull(
                this.getClass().getClassLoader().getResourceAsStream(resource))) {
            return new String(inputStream.readAllBytes());
        }
    }

    private void assertCurrencySchema(final DataSource dataSource) throws Exception {
        assertEquals(5, this.queryForInt(dataSource, "SELECT COUNT(*) FROM currencies"));
        assertEquals(10, this.queryForInt(dataSource, "SELECT COUNT(*) FROM player_currency_balances"));
        assertEquals(100, this.queryForInt(dataSource,
                "SELECT pcb.balance FROM player_currency_balances pcb JOIN currencies c ON c.id = pcb.currency_id WHERE pcb.player_id = 1 AND c.code = 'credits'"));
        assertEquals(25, this.queryForInt(dataSource,
                "SELECT pcb.balance FROM player_currency_balances pcb JOIN currencies c ON c.id = pcb.currency_id WHERE pcb.player_id = 1 AND c.code = 'currency_0'"));
        assertEquals(7, this.queryForInt(dataSource,
                "SELECT pcb.balance FROM player_currency_balances pcb JOIN currencies c ON c.id = pcb.currency_id WHERE pcb.player_id = 2 AND c.code = 'currency_5'"));
        assertEquals(0, this.queryForInt(dataSource, "SELECT COUNT(*) FROM player_currency_movements"));
        assertEquals(0, this.queryForInt(dataSource,
                "SELECT COUNT(*) FROM information_schema.columns WHERE table_name = 'players' AND column_name IN ('activity_points', 'vip_points', 'seasonal_points', 'black_money')"));
    }

    private DataSource createH2DataSource() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        final String databaseName = "currency_inventory_migration_" + UUID.randomUUID();

        dataSource.setURL("jdbc:h2:mem:" + databaseName + ";MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    private HikariDataSource createMariaDbDataSource(final MariaDBContainer<?> mariaDb) {
        final HikariDataSource dataSource = new HikariDataSource();

        dataSource.setJdbcUrl(String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC",
                mariaDb.getHost(),
                mariaDb.getMappedPort(PRODUCTION_MARIADB_PORT),
                mariaDb.getDatabaseName()));
        dataSource.setUsername(mariaDb.getUsername());
        dataSource.setPassword(mariaDb.getPassword());
        dataSource.setMaximumPoolSize(2);
        return dataSource;
    }

    private void createPlayersFixture(final DataSource dataSource) throws Exception {
        this.execute(dataSource, """
                CREATE TABLE players (
                  id int NOT NULL,
                  credits int NOT NULL DEFAULT 0,
                  activity_points int NOT NULL DEFAULT 0,
                  vip_points int NOT NULL DEFAULT 0,
                  seasonal_points int NOT NULL DEFAULT 0,
                  black_money int NOT NULL DEFAULT 0,
                  PRIMARY KEY (id)
                )
                """);
        this.execute(dataSource, """
                INSERT INTO players (
                  id,
                  credits,
                  activity_points,
                  vip_points,
                  seasonal_points,
                  black_money
                ) VALUES
                  (1, 100, 25, 5, 3, 1),
                  (2, 200, 50, 7, 4, 2)
                """);
    }

    private void createCatalogItemsFixture(final DataSource dataSource) throws Exception {
        this.execute(dataSource, """
                CREATE TABLE catalog_items (
                  id int unsigned NOT NULL,
                  cost_credits int NOT NULL DEFAULT 0,
                  cost_pixels int NOT NULL DEFAULT 0,
                  cost_diamonds int NOT NULL DEFAULT 0,
                  cost_seasonal int NOT NULL DEFAULT 0,
                  cost_tokens int NOT NULL DEFAULT 0,
                  PRIMARY KEY (id)
                )
                """);
        this.execute(dataSource, """
                INSERT INTO catalog_items (
                  id,
                  cost_credits,
                  cost_pixels,
                  cost_diamonds,
                  cost_seasonal,
                  cost_tokens
                ) VALUES
                  (1, 10, 5, 0, 0, 0),
                  (2, 0, 0, 7, 0, 0)
                """);
    }

    private int queryForInt(final DataSource dataSource, final String sql) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

    private void execute(final DataSource dataSource, final String sql) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
}
