package com.cometproject.api.server;

import java.util.concurrent.ScheduledExecutorService;

import com.cometproject.api.events.EventHandler;
import com.cometproject.api.networking.sessions.ISessionManager;
import com.google.inject.Injector;

public interface IGameService {
    ISessionManager getSessionManager();

    EventHandler getEventHandler();

    ScheduledExecutorService getExecutorService();

    /**
     * Returns the plugin child injector for the supplied module.
     *
     * @param moduleName The configured module name.
     * @return The child injector for the module, or {@code null} when the module has no child injector.
     */
    Injector getPluginInjector(String moduleName);
}
