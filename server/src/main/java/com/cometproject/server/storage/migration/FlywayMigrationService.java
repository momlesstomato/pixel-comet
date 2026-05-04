package com.cometproject.server.storage.migration;

import com.cometproject.storage.api.migration.IMigrationService;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * Flyway-backed implementation of the storage migration service.
 *
 * <p>The service always applies structural migrations and conditionally applies seed migrations
 * when the database seed flag is enabled.
 */
public final class FlywayMigrationService implements IMigrationService {
    private static final String MIGRATION_LOCATION = "classpath:db/migration";
    private static final String SEED_LOCATION = "classpath:db/seed";
    private static final String BASELINE_VERSION = "1";

    private final Flyway flyway;

    /**
     * Creates a migration service using the production migration and seed locations.
     *
     * @param dataSource the data source that Flyway should migrate.
     * @param seedEnabled whether seed migrations should be included in the Flyway run.
     */
    public FlywayMigrationService(final DataSource dataSource, final boolean seedEnabled) {
        this(dataSource, seedEnabled, List.of(MIGRATION_LOCATION), List.of(SEED_LOCATION));
    }

    FlywayMigrationService(
            final DataSource dataSource,
            final boolean seedEnabled,
            final List<String> migrationLocations,
            final List<String> seedLocations) {
        final List<String> locations = new ArrayList<>(migrationLocations);

        if (seedEnabled) {
            locations.addAll(seedLocations);
        }

        this.flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(locations.toArray(String[]::new))
                .baselineOnMigrate(true)
                .baselineVersion(BASELINE_VERSION)
                .load();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void migrate() {
        this.flyway.migrate();
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