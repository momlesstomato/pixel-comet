package com.cometproject.api.config.cache;

import java.util.Map;

/**
 * Declares Redis cache configuration keys and defaults.
 */
public final class RedisConfiguration {
    public static final String ENABLED = "comet.cache.enabled";
    public static final String PREFIX = "comet.cache.prefix";
    public static final String CONNECTION_HOST = "comet.cache.connection.host";
    public static final String CONNECTION_PORT = "comet.cache.connection.port";

    private RedisConfiguration() {
    }

    /**
     * Returns the default values for the Redis cache configuration group.
     *
     * @return The default Redis cache values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                ENABLED, "false",
                PREFIX, "comet",
                CONNECTION_HOST, "127.0.0.1",
                CONNECTION_PORT, "6379"
        );
    }
}