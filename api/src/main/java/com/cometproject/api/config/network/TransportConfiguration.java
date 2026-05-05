package com.cometproject.api.config.network;

import java.util.Map;

/**
 * Declares inbound transport configuration keys and defaults.
 */
public final class TransportConfiguration {
    public static final String TCP_ENABLED = "comet.transport.tcp.enabled";
    public static final String WEBSOCKETS_ENABLED = "comet.transport.websockets.enabled";
    public static final String WEBSOCKETS_PORT = "comet.transport.websockets.port";

    private TransportConfiguration() {
    }

    /**
     * Returns the default values for the transport configuration group.
     *
     * @return The default transport values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                TCP_ENABLED, "true",
                WEBSOCKETS_ENABLED, "true",
                WEBSOCKETS_PORT, "87"
        );
    }
}