package com.cometproject.networking.api;

import com.cometproject.networking.api.config.NetworkingServerConfig;
import com.cometproject.networking.api.sessions.INetSessionFactory;

/**
 * Defines the i networking server contract for the networking subsystem.
 */
public interface INetworkingServer {

    /**
     * Executes the start operation for this networking contract.
     */
    void start();

    /**
     * Returns the server config associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    NetworkingServerConfig getServerConfig();

    /**
     * Returns the session factory associated with this networking contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    INetSessionFactory getSessionFactory();
}
