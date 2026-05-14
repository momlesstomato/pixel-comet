package com.cometproject.server.network.websockets.packets.incoming;

import com.cometproject.server.network.websockets.WebSocketClientConnection;

/**
 * Defines the i web socket handler contract for the networking subsystem.
 */
public interface IWebSocketHandler {
    /**
     * Executes handle for this Comet contract.
     *
     * @param ctx Ctx supplied by the caller.
     * @param data Data supplied by the caller.
     */
    void handle(WebSocketClientConnection ctx, String data);
}
