package com.cometproject.api.config.logging;

import java.util.Map;

/**
 * Declares runtime logging configuration keys and defaults.
 */
public final class LoggingConfiguration {
    public static final String LEVEL = "comet.logging.level";
    public static final String FORMAT = "comet.logging.format";

    private LoggingConfiguration() {
    }

    /**
     * Returns the default values for the logging configuration group.
     *
     * @return The default logging values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                LEVEL, "info",
                FORMAT, "console"
        );
    }
}