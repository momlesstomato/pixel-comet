package com.cometproject.server.config;

import java.util.LinkedHashMap;
import java.util.Map;

import com.cometproject.api.config.ConfigurationKeys;
import com.cometproject.api.config.ConfigurationSource;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Reads configuration values from a local .env file for development environments.
 */
public final class DotEnvConfigurationSource implements ConfigurationSource {
    private final Dotenv dotenv;
    private final Map<String, String> values;

    /**
     * Creates a new .env-backed configuration source.
     *
     * @param directory The directory that may contain the .env file.
     */
    public DotEnvConfigurationSource(final String directory) {
        this.dotenv = Dotenv.configure()
                .directory(directory)
                .ignoreIfMissing()
                .load();

        final Map<String, String> resolvedValues = new LinkedHashMap<>();
        for (var entry : this.dotenv.entries()) {
            resolvedValues.put(entry.getKey(), entry.getValue());
            resolvedValues.put(ConfigurationKeys.toPropertyKey(entry.getKey()), entry.getValue());
        }

        this.values = Map.copyOf(resolvedValues);
    }

    /**
     * Executes get for this configuration contract.
     *
     * @param key Key supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public String get(final String key) {
        final String directValue = this.values.get(key);
        if (directValue != null) {
            return directValue;
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