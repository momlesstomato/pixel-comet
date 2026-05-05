package com.cometproject.api.config.network;

import java.util.Map;

/**
 * Declares connection registry configuration keys and defaults.
 */
public final class ConnectionRegistryConfiguration {
    public static final String IMPLEMENTATION = "comet.connection.registry.implementation";
    public static final String TTL_SECONDS = "comet.connection.registry.ttlSeconds";

    private ConnectionRegistryConfiguration() {
    }

    /**
     * Returns the default values for the connection registry configuration group.
     *
     * @return The default connection registry values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                IMPLEMENTATION, "inmemory",
                TTL_SECONDS, "3600"
        );
    }
}