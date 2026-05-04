package com.cometproject.api.config;

/**
 * Holds the active configuration source for the running process.
 */
public final class Configuration {
    private static volatile ConfigurationSource configuration;

    private Configuration() {
    }

    /**
     * Returns the active configuration source.
     *
     * @return The active configuration source.
     * @throws IllegalStateException When the configuration source has not been initialized yet.
     */
    public static ConfigurationSource currentConfig() {
        if (configuration == null) {
            throw new IllegalStateException("Configuration has not been initialised");
        }

        return configuration;
    }

    /**
     * Replaces the active configuration source.
     *
     * @param conf The new configuration source.
     */
    public static void setConfiguration(final ConfigurationSource conf) {
        configuration = conf;
    }
}
