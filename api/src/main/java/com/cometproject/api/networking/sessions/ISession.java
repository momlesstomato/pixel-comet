package com.cometproject.api.networking.sessions;

import org.slf4j.Logger;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.messages.IMessageComposer;

/**
 * Defines the i session contract for the networking subsystem.
 */
public interface ISession {
    /**
     * Returns the player associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IPlayer getPlayer();

    /**
     * Executes the disconnect operation for this networking contract.
     */
    void disconnect();

    /**
     * Executes the send operation for this networking contract.
     *
     * @param messageComposer Message composer value supplied by the caller.
     * @return Result produced by the operation.
     */
    ISession send(IMessageComposer messageComposer);

    /**
     * Executes the send queue operation for this networking contract.
     *
     * @param messageComposer Message composer value supplied by the caller.
     * @return Result produced by the operation.
     */
    ISession sendQueue(IMessageComposer messageComposer);

    /**
     * Returns the IP address associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getIpAddress();

    /**
     * Returns the logger associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Logger getLogger();

    /**
     * Returns the connection associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Connection getConnection();

    /**
     * Executes the flush operation for this networking contract.
     */
    void flush();
}
