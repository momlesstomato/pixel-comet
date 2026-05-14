package com.cometproject.server.network.websockets;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.google.gson.Gson;

/**
 * Manages web socket session runtime state for the networking subsystem.
 */
public class WebSocketSessionManager {

    private Queue<WebSocketClientConnection> channelHandlerContexts = new ConcurrentLinkedQueue<>();

    /**
     * Adds channel to this networking contract.
     *
     * @param channel Channel supplied by the caller.
     */
    public void addChannel(WebSocketClientConnection channel) {
        this.channelHandlerContexts.add(channel);
    }

    /**
     * Removes channel from this networking contract.
     *
     * @param channel Channel supplied by the caller.
     */
    public void removeChannel(WebSocketClientConnection channel) {
        this.channelHandlerContexts.remove(channel);
    }

    /**
     * Executes send message for this networking contract.
     *
     * @param message Message supplied by the caller.
     */
    public void sendMessage(String message) {
        for (WebSocketClientConnection context : this.channelHandlerContexts) {
            if (context != null) {
                context.sendText(message);
            }
        }
    }

    /**
     * Executes send message for this networking contract.
     *
     * @param session Session participating in the operation.
     * @param obj Obj supplied by the caller.
     */
    public void sendMessage(WebSocketClientConnection session, Object obj) {
        String message = gson.toJson(obj);

        if (session == null)
            return;

        session.sendText(message);
    }

    /**
     * Executes send message for this networking contract.
     *
     * @param obj Obj supplied by the caller.
     */
    public void sendMessage(Object obj) {
        this.sendMessage(gson.toJson(obj));
    }

    private static Gson gson = new Gson();
    private static WebSocketSessionManager instance = new WebSocketSessionManager();

    /**
     * Returns the instance for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public static WebSocketSessionManager getInstance() {
        return instance;
    }

}
