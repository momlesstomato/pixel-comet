package com.cometproject.server.network.sessions.net;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;
import com.cometproject.networking.api.sessions.INetSession;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes net session behavior for the network session subsystem.
 */
public class NetSession implements INetSession<Session> {

    private final Session session;
    private final IMessageHandler messageHandler;

    /**
     * Creates a net session instance for the network session subsystem.
     *
     * @param session Session participating in the operation.
     * @param messageHandler Message handler supplied by the caller.
     */
    public NetSession(Session session, IMessageHandler messageHandler) {
        this.session = session;
        this.messageHandler = messageHandler;
    }

    /**
     * Returns the connection for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Connection getConnection() {
        return this.session.getConnection();
    }

    /**
     * Returns the message handler for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IMessageHandler getMessageHandler() {
        return this.messageHandler;
    }

    /**
     * Returns the game session for this network session contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Session getGameSession() {
        return this.session;
    }
}
