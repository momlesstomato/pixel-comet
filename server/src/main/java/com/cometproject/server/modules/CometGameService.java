package com.cometproject.server.modules;

import java.util.concurrent.ScheduledExecutorService;

import com.cometproject.api.events.EventHandler;
import com.cometproject.api.networking.sessions.ISessionManager;
import com.cometproject.api.server.IGameService;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.tasks.CometThreadManager;
import com.google.inject.Injector;

public class CometGameService implements IGameService {
    /**
     * The main system-wide event handler
     */
    private final EventHandler eventHandler;
    private final ModuleManager moduleManager;

    /**
     * Default constructor
     */
    CometGameService(final EventHandler eventHandler, final ModuleManager moduleManager) {
        this.eventHandler = eventHandler;
        this.moduleManager = moduleManager;
    }

    /**
     * Get the main event handler
     *
     * @return EventHandler instance
     */
    @Override
    public EventHandler getEventHandler() {
        return this.eventHandler;
    }

    public ScheduledExecutorService getExecutorService() {
        return CometThreadManager.getInstance().getCoreExecutor();
    }

    @Override
    public ISessionManager getSessionManager() {
        return NetworkManager.getInstance().getSessions();
    }

    @Override
    public Injector getPluginInjector(final String moduleName) {
        return this.moduleManager.getPluginInjector(moduleName);
    }
}
