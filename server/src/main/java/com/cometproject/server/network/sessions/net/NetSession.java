package com.cometproject.server.network.sessions.net;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;
import com.cometproject.networking.api.sessions.INetSession;
import com.cometproject.server.network.sessions.Session;

public class NetSession implements INetSession<Session> {

    private final Session session;
    private final IMessageHandler messageHandler;

    public NetSession(Session session, IMessageHandler messageHandler) {
        this.session = session;
        this.messageHandler = messageHandler;
    }

    @Override
    public Connection getConnection() {
        return this.session.getConnection();
    }

    @Override
    public IMessageHandler getMessageHandler() {
        return this.messageHandler;
    }

    @Override
    public Session getGameSession() {
        return this.session;
    }
}
