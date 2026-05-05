package com.cometproject.server.storage.migration;

import com.zaxxer.hikari.HikariDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.MariaDBContainer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FlywayMigrationServiceTest {
    private static final List<String> TEST_MIGRATION_LOCATIONS = List.of("classpath:db/test-migration");
    private static final List<String> TEST_SEED_LOCATIONS = List.of("classpath:db/test-seed");
    private static final String PRODUCTION_MARIADB_IMAGE = "mariadb:11.4";
    private static final int PRODUCTION_MARIADB_PORT = 3306;

    @Test
    void migrate_appliesBaselineMigration_successfully() throws Exception {
        final DataSource dataSource = this.createDataSource();
        final FlywayMigrationService service = new FlywayMigrationService(
            dataSource,
            false,
            TEST_MIGRATION_LOCATIONS,
            TEST_SEED_LOCATIONS);

        service.migrate();

        assertEquals(1, this.queryForInt(dataSource, "SELECT COUNT(*) FROM flyway_schema_history WHERE version = '1'"));
    }

    @Test
    void migrate_isIdempotent_whenRunTwice() throws Exception {
        final DataSource dataSource = this.createDataSource();
        final FlywayMigrationService service = new FlywayMigrationService(
                dataSource,
                false,
                TEST_MIGRATION_LOCATIONS,
                TEST_SEED_LOCATIONS);

        service.migrate();
        service.migrate();

        assertEquals(1, this.queryForInt(dataSource, "SELECT COUNT(*) FROM flyway_schema_history WHERE version = '1' AND success = TRUE"));
    }

    @Test
    void currentVersion_returnsCorrectVersion_afterMigration() {
        final FlywayMigrationService service = this.createService(false);

        service.migrate();

        assertEquals("1", service.currentVersion());
    }

    @Test
    void migrate_withSeedEnabled_appliesSeedScripts() throws Exception {
        final DataSource dataSource = this.createDataSource();
        final FlywayMigrationService service = new FlywayMigrationService(
                dataSource,
                true,
                TEST_MIGRATION_LOCATIONS,
                TEST_SEED_LOCATIONS);

        service.migrate();

        assertEquals(1, this.queryForInt(dataSource, "SELECT COUNT(*) FROM test_seed_values"));
        assertTrue(this.queryForInt(dataSource, "SELECT COUNT(*) FROM flyway_seed_history WHERE version = '100'") > 0);
    }

    @Test
    void migrate_withSeedEnabled_onLegacyPopulatedSchema_baselinesSeedHistoryWithoutReplayingSeedScripts() throws Exception {
        final DataSource dataSource = this.createDataSource();
        this.execute(dataSource, "CREATE TABLE flyway_probe (id INT PRIMARY KEY, name VARCHAR(64) NOT NULL)");
        this.execute(dataSource, "CREATE TABLE test_seed_values (id INT PRIMARY KEY, seed_value VARCHAR(64) NOT NULL)");
        this.execute(dataSource, "INSERT INTO flyway_probe (id, name) VALUES (1, 'legacy')");
        this.execute(dataSource, "INSERT INTO test_seed_values (id, seed_value) VALUES (1, 'legacy-seeded')");

        final FlywayMigrationService service = new FlywayMigrationService(
                dataSource,
                true,
                TEST_MIGRATION_LOCATIONS,
                TEST_SEED_LOCATIONS);

        service.migrate();

        assertEquals(1, this.queryForInt(dataSource, "SELECT COUNT(*) FROM test_seed_values"));
        assertEquals(0, this.queryForInt(dataSource, "SELECT COUNT(*) FROM flyway_seed_history WHERE version = '100'"));
        assertTrue(this.queryForInt(dataSource, "SELECT COUNT(*) FROM flyway_seed_history WHERE version = '115'") > 0);
    }

    @Test
    void migrate_withProductionLocations_appliesCurrentSchemaAndRuntimeSeed() throws Exception {
        Assumptions.assumeTrue(
            DockerClientFactory.instance().isDockerAvailable(),
            "Docker is required for the production Flyway migration integration test.");

        try (MariaDBContainer<?> mariaDb = new MariaDBContainer<>(PRODUCTION_MARIADB_IMAGE)
                .withDatabaseName("pixel_comet_test")
                .withUsername("pixel")
                .withPassword("pixel")) {
            mariaDb.start();

            try (HikariDataSource dataSource = this.createMariaDbDataSource(mariaDb)) {
                final FlywayMigrationService service = new FlywayMigrationService(dataSource, true);

                service.migrate();

                assertEquals("3", service.currentVersion());
                assertTrue(this.queryForInt(dataSource, "SELECT COUNT(*) FROM flyway_seed_history WHERE version = '115' AND success = TRUE") > 0);
                assertTrue(this.queryForInt(dataSource, "SELECT COUNT(*) FROM furniture") > 0);
                assertTrue(this.queryForInt(dataSource, "SELECT COUNT(*) FROM catalog_pages") > 0);
                assertTrue(this.queryForInt(dataSource, "SELECT COUNT(*) FROM catalog_items") > 0);
            }
        }
    }

    private FlywayMigrationService createService(final boolean seedEnabled) {
        return new FlywayMigrationService(
                this.createDataSource(),
                seedEnabled,
                TEST_MIGRATION_LOCATIONS,
                TEST_SEED_LOCATIONS);
    }

    private DataSource createDataSource() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        final String databaseName = "flyway_migration_" + UUID.randomUUID();

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