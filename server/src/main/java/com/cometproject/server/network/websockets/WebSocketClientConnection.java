package com.cometproject.server.network.websockets;

import io.javalin.websocket.WsContext;

/**
 * Wraps a browser-facing side-channel WebSocket session.
 */
public final class WebSocketClientConnection {
    private final WsContext context;

    /**
     * Creates a new side-channel WebSocket wrapper.
     *
     * @param context The backing Javalin WebSocket context.
     */
    public WebSocketClientConnection(final WsContext context) {
        this.context = context;
    }

    /**
     * Sends a JSON text payload to the browser session.
     *
     * @param payload The text payload to send.
     */
    public void sendText(final String payload) {
        this.context.send(payload);
    }

    /**
     * Closes the underlying browser session.
     */
    public void close() {
        this.context.session.close();
    }
}