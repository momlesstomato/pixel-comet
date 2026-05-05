package com.cometproject.server.storage.migration;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import com.cometproject.storage.api.migration.IMigrationService;

/**
 * Flyway-backed implementation of the storage migration service.
 *
 * <p>Structural migrations always run against {@code flyway_schema_history}. Seed migrations are
 * optional and tracked in a separate {@code flyway_seed_history} table so that the two Flyway
 * instances never see each other's version numbers.
 */
public final class FlywayMigrationService implements IMigrationService {
    private static final String MIGRATION_LOCATION = "classpath:db/migration";
    private static final String SEED_LOCATION = "classpath:db/seed";
    private static final String BASELINE_VERSION = "1";
    private static final String INITIAL_SEED_BASELINE_VERSION = "99";
    private static final String LEGACY_SEED_BASELINE_VERSION = "115";
    private static final String SEED_HISTORY_TABLE = "flyway_seed_history";

    private final Flyway flyway;
    private final Flyway flywaySeeds;
    private final String seedBaselineVersion;

    /**
     * Creates a migration service using the production migration and seed locations.
     *
     * @param dataSource the data source that Flyway should migrate.
     * @param seedEnabled whether seed migrations should be applied.
     */
    public FlywayMigrationService(final DataSource dataSource, final boolean seedEnabled) {
        this(dataSource, seedEnabled, List.of(MIGRATION_LOCATION), List.of(SEED_LOCATION));
    }

    FlywayMigrationService(
            final DataSource dataSource,
            final boolean seedEnabled,
            final List<String> migrationLocations,
            final List<String> seedLocations) {
        this.flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(migrationLocations.toArray(String[]::new))
                .baselineOnMigrate(true)
                .baselineVersion(BASELINE_VERSION)
                .validateOnMigrate(false)
                .load();

            this.seedBaselineVersion = seedEnabled ? this.resolveSeedBaselineVersion(dataSource) : null;

        this.flywaySeeds = seedEnabled
                ? Flyway.configure()
                        .dataSource(dataSource)
                        .locations(seedLocations.toArray(String[]::new))
                        .table(SEED_HISTORY_TABLE)
                    .baselineVersion(this.seedBaselineVersion == null ? INITIAL_SEED_BASELINE_VERSION : this.seedBaselineVersion)
                        .validateOnMigrate(false)
                        .load()
                : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void migrate() {
        this.flyway.migrate();
        if (this.flywaySeeds != null) {
            if (this.seedBaselineVersion != null) {
                this.flywaySeeds.baseline();
            }

            this.flywaySeeds.migrate();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String currentVersion() {
        final MigrationInfo current = this.flyway.info().current();
        return current == null ? "" : current.getVersion().getVersion();
    }

    private String resolveSeedBaselineVersion(final DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            final DatabaseMetaData metaData = connection.getMetaData();
            final String catalog = connection.getCatalog();
            final String schema = connection.getSchema();
            final boolean hasSeedHistory = this.hasTable(metaData, catalog, schema, SEED_HISTORY_TABLE);

            if (hasSeedHistory) {
                return null;
            }

            return this.hasAnyTable(metaData, catalog, schema)
                    ? LEGACY_SEED_BASELINE_VERSION
                    : INITIAL_SEED_BASELINE_VERSION;
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to inspect existing schema before seed migration.", exception);
        }
    }

    private boolean hasAnyTable(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema
    ) throws SQLException {
        try (ResultSet resultSet = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"})) {
            return resultSet.next();
        }
    }

    private boolean hasTable(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final String tableName
    ) throws SQLException {
        try (ResultSet resultSet = metaData.getTables(catalog, schema, tableName, new String[]{"TABLE"})) {
            return resultSet.next();
        }
    }
}
