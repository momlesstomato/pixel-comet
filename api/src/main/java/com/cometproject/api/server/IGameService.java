package com.cometproject.api.server;

import java.util.concurrent.ScheduledExecutorService;

import com.cometproject.api.events.EventHandler;
import com.cometproject.api.networking.sessions.ISessionManager;
import com.google.inject.Injector;

/**
 * Defines the i game service contract for the Comet subsystem.
 */
public interface IGameService {
    /**
     * Returns the session manager associated with this server contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ISessionManager getSessionManager();

    /**
     * Returns the event handler associated with this server contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    EventHandler getEventHandler();

    /**
     * Returns the executor service associated with this server contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ScheduledExecutorService getExecutorService();

    /**
     * Returns the plugin child injector for the supplied module.
     *
     * @param moduleName The configured module name.
     * @return The child injector for the module, or {@code null} when the module has no child injector.
     */
    Injector getPluginInjector(String moduleName);
}
