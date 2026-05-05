package com.cometproject.server.storage.migration;

import com.cometproject.storage.api.migration.IMigrationService;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import javax.sql.DataSource;
import java.util.List;

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
    private static final String SEED_HISTORY_TABLE = "flyway_seed_history";

    private final Flyway flyway;
    private final Flyway flywaySeeds;

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

        this.flywaySeeds = seedEnabled
                ? Flyway.configure()
                        .dataSource(dataSource)
                        .locations(seedLocations.toArray(String[]::new))
                        .table(SEED_HISTORY_TABLE)
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
}
