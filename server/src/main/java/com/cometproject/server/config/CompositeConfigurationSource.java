package com.cometproject.server.config;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cometproject.api.config.ConfigurationKeys;
import com.cometproject.api.config.ConfigurationSource;

/**
 * Resolves configuration values by consulting a prioritized list of sources.
 */
public final class CompositeConfigurationSource implements ConfigurationSource {
    private final List<ConfigurationSource> sources;

    /**
     * Creates a new composite source.
     *
     * @param sources The ordered configuration sources from highest to lowest priority.
     */
    public CompositeConfigurationSource(final List<ConfigurationSource> sources) {
        this.sources = List.copyOf(sources);
    }

    /**
     * Executes get for this configuration contract.
     *
     * @param key Key supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public String get(final String key) {
        final String propertyKey = ConfigurationKeys.toPropertyKey(key).equals(key) ? key : ConfigurationKeys.toPropertyKey(key);

        for (ConfigurationSource source : this.sources) {
            final String value = source.get(key);

            if (value != null) {
                return value;
            }

            if (!propertyKey.equals(key)) {
                final String propertyValue = source.get(propertyKey);
                if (propertyValue != null) {
                    return propertyValue;
                }
            }
        }

        return null;
    }

    /**
     * Executes has for this configuration contract.
     *
     * @param key Key supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean has(final String key) {
        for (ConfigurationSource source : this.sources) {
            if (source.has(key)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the all for this configuration contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<String, String> getAll() {
        final Map<String, String> merged = new LinkedHashMap<>();

        for (int index = this.sources.size() - 1; index >= 0; index--) {
            merged.putAll(this.sources.get(index).getAll());
        }

        return Map.copyOf(merged);
    }
}