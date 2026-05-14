package com.cometproject.networking.api;

import com.cometproject.networking.api.config.NetworkingServerConfig;
import com.cometproject.networking.api.sessions.INetSessionFactory;

/**
 * Defines the i networking server factory contract for the networking subsystem.
 */
public interface INetworkingServerFactory {

    /**
     * Creates server data for this networking contract.
     *
     * @param serverConfig Server config value supplied by the caller.
     * @param sessionFactory Session factory value supplied by the caller.
     * @return Result produced by the mutation.
     */
    INetworkingServer createServer(NetworkingServerConfig serverConfig, INetSessionFactory sessionFactory);

}
