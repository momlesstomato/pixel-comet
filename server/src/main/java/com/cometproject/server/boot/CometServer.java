package com.cometproject.server.boot;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.ConfigurationSource;
import com.cometproject.server.config.ConfigurationBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Describes comet server behavior for the boot lifecycle subsystem.
 */
public class CometServer {
    public static final String CLIENT_VERSION = "PRODUCTION-201709192204-203982672";
    private final Logger LOGGER = LoggerFactory.getLogger(CometServer.class);
    private final CometBootstrap bootstrap;

    /**
     * Creates a comet server instance for the boot lifecycle subsystem.
     *
     * @param overridenConfig Overriden config supplied by the caller.
     */
    public CometServer(Map<String, String> overridenConfig) {
        ConfigurationBootstrap.initialize(overridenConfig);
        this.bootstrap = new CometBootstrap();
    }

    /**
     * Initialize Comet Server.
     */
    public void init() {
        this.bootstrap.start();
    }

    /**
     * Stops the server lifecycle.
     */
    public void stop() {
        this.bootstrap.stop();
    }

    /**
     * Get the Comet configuration.
     *
     * @return Comet configuration
     */
    public ConfigurationSource getConfig() {
        return Configuration.currentConfig();
    }

    /**
     * Returns the logger for this boot lifecycle contract.
     *
     * @return Value exposed by the contract.
     */
    public Logger getLogger() {
        return LOGGER;
    }
}
