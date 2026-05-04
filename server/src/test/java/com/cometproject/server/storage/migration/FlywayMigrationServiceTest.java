package com.cometproject.server.storage.migration;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Test;

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
        assertTrue(this.queryForInt(dataSource, "SELECT COUNT(*) FROM flyway_schema_history WHERE version = '100'") > 0);
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

    private int queryForInt(final DataSource dataSource, final String sql) throws Exception {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            resultSet.next();
            return resultSet.getInt(1);
        }
    }

}