package com.cometproject.server.logging;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;


/**
 * Manages log runtime state for the logging subsystem.
 */
public class LogManager implements Startable {
    public static final boolean ENABLED = Configuration.currentConfig().get("comet.game.logging.enabled").equals("true");
    private LogStore store;

    /**
     * Creates a log manager instance for the logging subsystem.
     */
    public LogManager() {
    }

    /**
     * Returns the instance for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public static LogManager getInstance() {
        return CometBootstrap.resolve(LogManager.class);
    }

    /**
     * Starts this logging component.
     */
    @Override
    public void start() {
        this.store = new LogStore();
    }

    /**
     * Returns the store for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public LogStore getStore() {
        return store;
    }
}