package com.cometproject.storage.api.migration;

/**
 * Applies and inspects database schema migrations.
 *
 * <p>Implementations are responsible for bringing the configured schema to the latest version
 * before any storage access is attempted.
 */
public interface IMigrationService {
    /**
     * Applies all pending structural and optional seed migrations.
     */
    void migrate();

    /**
     * Returns the current schema version after migrations have been inspected.
     *
     * @return the current Flyway version string, or an empty string when no version exists.
     */
    String currentVersion();
}