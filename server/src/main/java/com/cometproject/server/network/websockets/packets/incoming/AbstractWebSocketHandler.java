package com.cometproject.server.network.websockets.packets.incoming;

import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.google.gson.Gson;

/**
 * Describes abstract web socket handler behavior for the networking subsystem.
 */
public abstract class AbstractWebSocketHandler<TData> implements IWebSocketHandler {

    private static Gson gson = new Gson();

    private final Class<TData> type;

    /**
     * Creates a abstract web socket handler instance for the networking subsystem.
     *
     * @param type Type supplied by the caller.
     */
    public AbstractWebSocketHandler(Class<TData> type) {
        this.type = type;
    }

    /**
     * Executes handle for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @param data Data supplied by the caller.
     */
    public abstract void handle(WebSocketClientConnection ctx, TData data);

    /**
     * Executes handle for this networking contract.
     *
     * @param ctx Netty channel context for the current operation.
     * @param data Data supplied by the caller.
     */
    @Override
    public void handle(WebSocketClientConnection ctx, String data) {
        handle(ctx, gson.fromJson(data, this.type));
    }
}
