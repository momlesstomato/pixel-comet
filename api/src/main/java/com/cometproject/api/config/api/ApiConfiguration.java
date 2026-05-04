package com.cometproject.api.config.api;

import java.util.Map;

/**
 * Declares management API configuration keys and defaults.
 */
public final class ApiConfiguration {
    public static final String ENABLED = "comet.api.enabled";
    public static final String PORT = "comet.api.port";
    public static final String TOKEN = "comet.api.token";

    private ApiConfiguration() {
    }

    /**
     * Returns the default values for the management API configuration group.
     *
     * @return The default management API values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                ENABLED, "false",
                PORT, "30003",
                TOKEN, "changeme"
        );
    }
}