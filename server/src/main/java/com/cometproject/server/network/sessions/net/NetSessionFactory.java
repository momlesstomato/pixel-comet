package com.cometproject.server.network.sessions.net;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;
import com.cometproject.networking.api.sessions.INetSession;
import com.cometproject.networking.api.sessions.INetSessionFactory;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.network.sessions.SessionManager;

public class NetSessionFactory implements INetSessionFactory {
    private final SessionManager sessionManager;
    private final IMessageHandler messageHandler;

    public NetSessionFactory(SessionManager sessionManager, IMessageHandler messageHandler) {
        this.sessionManager = sessionManager;
        this.messageHandler = messageHandler;

    }

    @Override
    public INetSession createSession(final Connection connection) {
        final Session session = this.sessionManager.add(connection);

        if (session == null) {
            return null;
        }
        final INetSession netSession = new NetSession(session, this.messageHandler);

        return netSession;
    }

    @Override
    public void disposeSession(INetSession session) {
        ((Session) session.getGameSession()).onDisconnect();

        this.sessionManager.remove(session.getConnection());
    }
}
