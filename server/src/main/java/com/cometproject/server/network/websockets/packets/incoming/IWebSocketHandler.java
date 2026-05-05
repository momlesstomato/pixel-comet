package com.cometproject.server.network.websockets.packets.incoming;

import com.cometproject.server.network.websockets.WebSocketClientConnection;

public interface IWebSocketHandler {
    void handle(WebSocketClientConnection ctx, String data);
}
