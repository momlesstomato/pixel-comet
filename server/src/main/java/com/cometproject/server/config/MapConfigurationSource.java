package com.cometproject.server.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cometproject.api.config.ConfigurationKeys;
import com.cometproject.api.config.ConfigurationSource;

/**
 * Exposes in-memory overrides as a configuration source.
 */
final class MapConfigurationSource implements ConfigurationSource {
    private final Map<String, String> values;

    MapConfigurationSource(final Map<String, String> values) {
        final Map<String, String> normalizedValues = new LinkedHashMap<>();
        for (Map.Entry<String, String> entry : values.entrySet()) {
            normalizedValues.put(entry.getKey(), entry.getValue());
            normalizedValues.put(ConfigurationKeys.toPropertyKey(entry.getKey()), entry.getValue());
        }
        this.values = Map.copyOf(normalizedValues);
    }

    /**
     * Executes get for this configuration contract.
     *
     * @param key Key supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public String get(final String key) {
        final String value = this.values.get(key);
        if (value != null) {
            return value;
        }

        return this.values.get(ConfigurationKeys.toEnvKey(key));
    }

    /**
     * Returns the all for this configuration contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<String, String> getAll() {
        return this.values;
    }
}