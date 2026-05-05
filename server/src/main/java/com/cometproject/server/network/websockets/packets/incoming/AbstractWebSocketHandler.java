package com.cometproject.server.network.websockets.packets.incoming;

import com.cometproject.server.network.websockets.WebSocketClientConnection;
import com.google.gson.Gson;

public abstract class AbstractWebSocketHandler<TData> implements IWebSocketHandler {

    private static Gson gson = new Gson();

    private final Class<TData> type;

    public AbstractWebSocketHandler(Class<TData> type) {
        this.type = type;
    }

    public abstract void handle(WebSocketClientConnection ctx, TData data);

    @Override
    public void handle(WebSocketClientConnection ctx, String data) {
        handle(ctx, gson.fromJson(data, this.type));
    }
}
