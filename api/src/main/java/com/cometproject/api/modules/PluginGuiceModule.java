package com.cometproject.api.modules;

import com.cometproject.api.server.IGameService;
import com.google.inject.AbstractModule;

/**
 * Base Guice module for plugin-scoped bindings.
 *
 * <p>Implementations may bind plugin-local services while still having access
 * to the shared game service bridge exposed by the host server.
 */
public abstract class PluginGuiceModule extends AbstractModule {
    private final IGameService gameService;

    /**
     * Creates a plugin Guice module for the supplied game service bridge.
     *
     * @param gameService The game service bridge exposed to the plugin.
     */
    protected PluginGuiceModule(final IGameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Returns the game service bridge available to the plugin injector.
     *
     * @return The game service bridge.
     */
    protected final IGameService getGameService() {
        return this.gameService;
    }
}