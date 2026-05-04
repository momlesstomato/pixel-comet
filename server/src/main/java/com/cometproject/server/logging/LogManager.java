package com.cometproject.server.logging;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;


public class LogManager implements Startable {
    public static final boolean ENABLED = Configuration.currentConfig().get("comet.game.logging.enabled").equals("true");
    private LogStore store;

    public LogManager() {
    }

    public static LogManager getInstance() {
        return CometBootstrap.resolve(LogManager.class);
    }

    @Override
    public void start() {
        this.store = new LogStore();
    }

    public LogStore getStore() {
        return store;
    }
}