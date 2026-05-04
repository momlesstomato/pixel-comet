package com.cometproject.api.config.database;

import java.util.Map;

/**
 * Declares database configuration keys and defaults.
 */
public final class DatabaseConfiguration {
    public static final String HOST = "comet.db.host";
    public static final String USERNAME = "comet.db.username";
    public static final String PASSWORD = "comet.db.password";
    public static final String NAME = "comet.db.name";
    public static final String POOL_MAX = "comet.db.pool.max";
    public static final String POOL_MIN = "comet.db.pool.min";
    public static final String POOL_COUNT = "comet.db.pool.count";
    public static final String POOL_IDLE_MAX_AGE_SECONDS = "comet.db.pool.idleMaxAgeSeconds";
    public static final String POOL_MAX_CONNECTION_AGE_SECONDS = "comet.db.pool.maxConnectionAgeSeconds";
    public static final String POOL_ACQUIRE_RETRY_ATTEMPTS = "comet.db.pool.acquireRetryAttempts";
    public static final String POOL_AUTO_RECONNECT = "comet.db.pool.autoReconnect";
    public static final String POOL_TCP_KEEP_ALIVE = "comet.db.pool.tcpKeepAlive";

    private DatabaseConfiguration() {
    }

    /**
     * Returns the default values for the database configuration group.
     *
     * @return The default database values.
     */
    public static Map<String, String> defaults() {
        return Map.ofEntries(
                Map.entry(HOST, "127.0.0.1"),
                Map.entry(USERNAME, "root"),
                Map.entry(PASSWORD, "changeme"),
                Map.entry(NAME, "pixel_comet"),
                Map.entry(POOL_MAX, "100"),
                Map.entry(POOL_MIN, "10"),
                Map.entry(POOL_COUNT, "2"),
                Map.entry(POOL_IDLE_MAX_AGE_SECONDS, "30"),
                Map.entry(POOL_MAX_CONNECTION_AGE_SECONDS, "60"),
                Map.entry(POOL_ACQUIRE_RETRY_ATTEMPTS, "50"),
                Map.entry(POOL_AUTO_RECONNECT, "false"),
                Map.entry(POOL_TCP_KEEP_ALIVE, "true")
        );
    }
}