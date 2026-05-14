package com.cometproject.server.network.sessions.net;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;
import com.cometproject.networking.api.sessions.INetSession;
import com.cometproject.networking.api.sessions.INetSessionFactory;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.sessions.SessionManager;

/**
 * Creates net session instances for the network session subsystem.
 */
public class NetSessionFactory implements INetSessionFactory {
    private final SessionManager sessionManager;
    private final IMessageHandler messageHandler;

    /**
     * Creates a net session factory instance for the network session subsystem.
     *
     * @param sessionManager Session manager supplied by the caller.
     * @param messageHandler Message handler supplied by the caller.
     */
    public NetSessionFactory(SessionManager sessionManager, IMessageHandler messageHandler) {
        this.sessionManager = sessionManager;
        this.messageHandler = messageHandler;

    }

    /**
     * Creates session for this network session contract.
     *
     * @param connection Connection supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public INetSession createSession(final Connection connection) {
        final Session session = this.sessionManager.add(connection);

        if (session == null) {
            return null;
        }
        final INetSession netSession = new NetSession(session, this.messageHandler);

        return netSession;
    }

    /**
     * Executes dispose session for this network session contract.
     *
     * @param session Session participating in the operation.
     */
    @Override
    public void disposeSession(INetSession session) {
        ((Session) session.getGameSession()).onDisconnect();

        this.sessionManager.remove(session.getConnection());
    }
}
