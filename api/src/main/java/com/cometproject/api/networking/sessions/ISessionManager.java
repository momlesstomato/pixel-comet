package com.cometproject.api.networking.sessions;

import java.util.Map;
import java.util.Set;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.messages.IMessageComposer;

/**
 * Defines the i session manager contract for the networking subsystem.
 */
public interface ISessionManager {
    /**
     * Executes the disconnect by player id operation for this networking contract.
     *
     * @param id Id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean disconnectByPlayerId(int id);

    /**
     * Returns the by player id associated with this networking contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ISession getByPlayerId(int id);

    /**
     * Returns the by player permission associated with this networking contract.
     *
     * @param permission Permission value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Set<ISession> getByPlayerPermission(String permission);

    /**
     * Returns the by player username associated with this networking contract.
     *
     * @param username Username value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ISession getByPlayerUsername(String username);

    /**
     * Returns the users online count associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getUsersOnlineCount();

    /**
     * Returns the sessions associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, ISession> getSessions();

    /**
     * Executes the broadcast operation for this networking contract.
     *
     * @param msg Msg value supplied by the caller.
     */
    void broadcast(IMessageComposer msg);

    /**
     * Executes the broadcast to moderators operation for this networking contract.
     *
     * @param messageComposer Message composer value supplied by the caller.
     */
    void broadcastToModerators(IMessageComposer messageComposer);

    /**
     * Executes the parse command operation for this networking contract.
     *
     * @param message Message value supplied by the caller.
     * @param connection Connection value supplied by the caller.
     */
    void parseCommand(String[] message, Connection connection);
}
