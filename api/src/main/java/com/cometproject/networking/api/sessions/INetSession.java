package com.cometproject.networking.api.sessions;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.networking.api.messages.IMessageHandler;

/**
 * Defines the i net session contract for the networking subsystem.
 */
public interface INetSession<T> {

    /**
     * Returns the connection associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Connection getConnection();

    /**
     * Returns the message handler associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IMessageHandler getMessageHandler();

    /**
     * Returns the game session associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    T getGameSession();

}
