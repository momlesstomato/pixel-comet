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

    @Override
    public boolean has(final String key) {
        for (ConfigurationSource source : this.sources) {
            if (source.has(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Map<String, String> getAll() {
        final Map<String, String> merged = new LinkedHashMap<>();

        for (int index = this.sources.size() - 1; index >= 0; index--) {
            merged.putAll(this.sources.get(index).getAll());
        }

        return Map.copyOf(merged);
    }
}