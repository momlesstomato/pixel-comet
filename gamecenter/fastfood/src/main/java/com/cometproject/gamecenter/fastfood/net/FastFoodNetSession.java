package com.cometproject.gamecenter.fastfood.net;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;
import com.cometproject.networking.api.sessions.INetSession;

/**
 * Describes fast food net session behavior for the Fast Food game subsystem.
 */
public class FastFoodNetSession implements INetSession<FastFoodGameSession> {

    private final Connection connection;
    private final FastFoodGameSession gameSession;
    private final IMessageHandler messageHandler;

    /**
     * Creates a fast food net session instance for the Fast Food game subsystem.
     *
     * @param connection Connection supplied by the caller.
     * @param gameSession Game session supplied by the caller.
     * @param messageHandler Message handler supplied by the caller.
     */
    public FastFoodNetSession(Connection connection, FastFoodGameSession gameSession, IMessageHandler messageHandler) {
        this.connection = connection;
        this.gameSession = gameSession;
        this.messageHandler = messageHandler;
    }

    /**
     * Returns the connection for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Returns the message handler for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IMessageHandler getMessageHandler() {
        return this.messageHandler;
    }

    /**
     * Returns the game session for this Fast Food game contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public FastFoodGameSession getGameSession() {
        return this.gameSession;
    }
}
