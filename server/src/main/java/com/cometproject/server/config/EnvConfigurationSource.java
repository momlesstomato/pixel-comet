package com.cometproject.server.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cometproject.api.config.ConfigurationKeys;
import com.cometproject.api.config.ConfigurationSource;

/**
 * Reads configuration values from the process environment.
 */
public final class EnvConfigurationSource implements ConfigurationSource {
    private final Map<String, String> environment;
    private final Map<String, String> normalizedValues;

    /**
     * Creates a new environment-backed configuration source.
     */
    public EnvConfigurationSource() {
        this(System.getenv());
    }

    EnvConfigurationSource(final Map<String, String> environment) {
        this.environment = Map.copyOf(environment);

        final Map<String, String> values = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : this.environment.entrySet()) {
            values.put(entry.getKey(), entry.getValue());
            values.put(ConfigurationKeys.toPropertyKey(entry.getKey()), entry.getValue());
        }

        this.normalizedValues = Map.copyOf(values);
    }

    @Override
    public String get(final String key) {
        final String directValue = this.normalizedValues.get(key);
        if (directValue != null) {
            return directValue;
        }

        return this.environment.get(ConfigurationKeys.toEnvKey(key));
    }

    @Override
    public Map<String, String> getAll() {
        return this.normalizedValues;
    }
}