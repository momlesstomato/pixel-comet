package com.cometproject.gamecenter.fastfood.net;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;
import com.cometproject.networking.api.sessions.INetSession;
import com.cometproject.networking.api.sessions.INetSessionFactory;

/**
 * Creates session instances for the Fast Food game subsystem.
 */
public class SessionFactory implements INetSessionFactory {

    private final IMessageHandler messageHandler;

    /**
     * Creates a session factory instance for the Fast Food game subsystem.
     *
     * @param messageHandler Message handler supplied by the caller.
     */
    public SessionFactory(final IMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Creates session for this Fast Food game contract.
     *
     * @param connection Connection supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public INetSession createSession(Connection connection) {
        return new FastFoodNetSession(connection, new FastFoodGameSession(), this.messageHandler);
    }

    /**
     * Executes dispose session for this Fast Food game contract.
     *
     * @param session Session participating in the operation.
     */
    @Override
    public void disposeSession(INetSession session) {
        final FastFoodNetSession netSession = (FastFoodNetSession) session;

        // Leave games, dispose session idk?
    }
}
