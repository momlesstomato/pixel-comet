package com.cometproject.server.network.websockets;

import com.google.gson.Gson;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class WebSocketSessionManager {

    private Queue<WebSocketClientConnection> channelHandlerContexts = new ConcurrentLinkedQueue<>();

    public void addChannel(WebSocketClientConnection channel) {
        this.channelHandlerContexts.add(channel);
    }

    public void removeChannel(WebSocketClientConnection channel) {
        this.channelHandlerContexts.remove(channel);
    }

    public void sendMessage(String message) {
        for (WebSocketClientConnection context : this.channelHandlerContexts) {
            if (context != null) {
                context.sendText(message);
            }
        }
    }

    public void sendMessage(WebSocketClientConnection session, Object obj) {
        String message = gson.toJson(obj);

        if (session == null)
            return;

        session.sendText(message);
    }

    public void sendMessage(Object obj) {
        this.sendMessage(gson.toJson(obj));
    }

    private static Gson gson = new Gson();
    private static WebSocketSessionManager instance = new WebSocketSessionManager();

    public static WebSocketSessionManager getInstance() {
        return instance;
    }

}
