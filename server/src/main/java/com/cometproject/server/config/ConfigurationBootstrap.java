package com.cometproject.server.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.ConfigurationSource;

/**
 * Builds and installs the composite runtime configuration chain.
 */
public final class ConfigurationBootstrap {
    private ConfigurationBootstrap() {
    }

    /**
     * Creates and installs the composite configuration chain.
     *
     * @param overrides Optional in-memory overrides with the highest priority.
     * @return The installed composite configuration source.
     */
    public static ConfigurationSource initialize(final Map<String, String> overrides) {
        final List<ConfigurationSource> sources = new ArrayList<>();

        if (overrides != null && !overrides.isEmpty()) {
            sources.add(new MapConfigurationSource(overrides));
        }

        sources.add(new EnvConfigurationSource());
        sources.add(new DotEnvConfigurationSource("."));
        sources.add(new DefaultsConfigurationSource());

        final ConfigurationSource configurationSource = new CompositeConfigurationSource(sources);
        Configuration.setConfiguration(configurationSource);
        return configurationSource;
    }
}