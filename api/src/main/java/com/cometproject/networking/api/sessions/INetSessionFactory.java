package com.cometproject.networking.api.sessions;

import com.cometproject.api.networking.connections.Connection;

/**
 * Defines the i net session factory contract for the networking subsystem.
 */
public interface INetSessionFactory {

    /**
     * Creates session data for this networking contract.
     *
     * @param connection Connection value supplied by the caller.
     * @return Result produced by the mutation.
     */
    INetSession createSession(Connection connection);

    /**
     * Executes the dispose session operation for this networking contract.
     *
     * @param session Session value supplied by the caller.
     */
    void disposeSession(INetSession session);

}
