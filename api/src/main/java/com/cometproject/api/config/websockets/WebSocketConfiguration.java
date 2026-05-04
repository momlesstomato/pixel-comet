package com.cometproject.api.config.websockets;

import java.util.Map;

/**
 * Declares WebSocket listener configuration keys and defaults.
 */
public final class WebSocketConfiguration {
    public static final String ENABLED = "comet.websockets.enable";
    public static final String PORT = "comet.websockets.port";

    private WebSocketConfiguration() {
    }

    /**
     * Returns the default values for the WebSocket configuration group.
     *
     * @return The default WebSocket values.
     */
    public static Map<String, String> defaults() {
        return Map.of(
                ENABLED, "true",
                PORT, "87"
        );
    }
}