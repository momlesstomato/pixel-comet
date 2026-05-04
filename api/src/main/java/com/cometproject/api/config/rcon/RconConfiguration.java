package com.cometproject.api.config.rcon;

import java.util.Map;

/**
 * Declares RCON configuration keys and defaults.
 */
public final class RconConfiguration {
    public static final String TCP_PASSWORD = "comet.rcon.tcp.password";
    public static final String TCP_PORT = "comet.rcon.tcp.port";

    private RconConfiguration() {
    }

    /**
     * Returns the default values for the RCON configuration group.
     *
     * @return The default RCON values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                TCP_PASSWORD, "changeme",
                TCP_PORT, "7777"
        );
    }
}