package com.cometproject.gamecenter.fastfood.net;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;
import com.cometproject.networking.api.sessions.INetSession;

public class FastFoodNetSession implements INetSession<FastFoodGameSession> {

    private final Connection connection;
    private final FastFoodGameSession gameSession;
    private final IMessageHandler messageHandler;

    public FastFoodNetSession(Connection connection, FastFoodGameSession gameSession, IMessageHandler messageHandler) {
        this.connection = connection;
        this.gameSession = gameSession;
        this.messageHandler = messageHandler;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public IMessageHandler getMessageHandler() {
        return this.messageHandler;
    }

    @Override
    public FastFoodGameSession getGameSession() {
        return this.gameSession;
    }
}
