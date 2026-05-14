package com.cometproject.server.modules;

import java.util.concurrent.ScheduledExecutorService;

import com.cometproject.api.events.EventHandler;
import com.cometproject.api.networking.sessions.ISessionManager;
import com.cometproject.api.server.IGameService;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.tasks.CometThreadManager;
import com.google.inject.Injector;

/**
 * Coordinates comet game behavior for the module subsystem.
 */
public class CometGameService implements IGameService {
    /**
     * The main system-wide event handler.
     */
    private final EventHandler eventHandler;
    private final ModuleManager moduleManager;

    /**
     * Default constructor.
     */
    CometGameService(final EventHandler eventHandler, final ModuleManager moduleManager) {
        this.eventHandler = eventHandler;
        this.moduleManager = moduleManager;
    }

    /**
     * Get the main event handler.
     *
     * @return EventHandler instance
     */
    @Override
    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    /**
     * Returns the executor service for this module contract.
     *
     * @return Value exposed by the contract.
     */
    public ScheduledExecutorService getExecutorService() {
        return CometThreadManager.getInstance().getCoreExecutor();
    }

    /**
     * Returns the session manager for this module contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public ISessionManager getSessionManager() {
        return NetworkManager.getInstance().getSessions();
    }

    /**
     * Returns the plugin injector for this module contract.
     *
     * @param moduleName Module name supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Injector getPluginInjector(final String moduleName) {
        return this.moduleManager.getPluginInjector(moduleName);
    }
}
