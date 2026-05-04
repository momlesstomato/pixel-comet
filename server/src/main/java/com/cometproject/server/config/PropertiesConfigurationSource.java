package com.cometproject.server.config;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.ConfigurationSource;

/**
 * Wraps the legacy properties-file configuration format used before env-based configuration.
 */
public final class PropertiesConfigurationSource implements ConfigurationSource {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesConfigurationSource.class);
    private final Map<String, String> values;

    /**
     * Creates a new properties-file-backed configuration source.
     *
     * @param filePath The path to the properties file.
     */
    public PropertiesConfigurationSource(final String filePath) {
        final Properties properties = new Properties();

        try (Reader stream = new InputStreamReader(new FileInputStream(filePath), "UTF-8")) {
            properties.load(stream);
        } catch (Exception e) {
            LOGGER.error("Failed to fetch the server configuration ({})", filePath, e);
            throw new IllegalStateException("Failed to load properties configuration", e);
        }

        final Map<String, String> resolvedValues = new LinkedHashMap<>();
        for (String propertyName : properties.stringPropertyNames()) {
            resolvedValues.put(propertyName, properties.getProperty(propertyName));
        }

        this.values = Map.copyOf(resolvedValues);
    }

    @Override
    public String get(final String key) {
        return this.values.get(key);
    }

    @Override
    public Map<String, String> getAll() {
        return this.values;
    }
}