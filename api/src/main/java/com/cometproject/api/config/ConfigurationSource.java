package com.cometproject.api.config;

import java.util.Collections;
import java.util.Map;

/**
 * Describes a configuration backend capable of resolving server settings.
 *
 * <p>Implementations may read from process environment variables, a local
 * development .env file, in-memory overrides, or any future backend.
 */
public interface ConfigurationSource {
    /**
     * Resolves the configured value for the supplied key.
     *
     * @param key The property-style or environment-style key to resolve.
     * @return The configured value, or {@code null} when the key is undefined.
     */
    String get(String key);

    /**
     * Resolves the configured value for the supplied key using a fallback.
     *
     * @param key The property-style or environment-style key to resolve.
     * @param defaultValue The fallback value when the key is undefined.
     * @return The configured or fallback value.
     */
    default String getOrDefault(final String key, final String defaultValue) {
        final String value = this.get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * Resolves the configured value for the supplied key using a fallback.
     *
     * @param key The property-style or environment-style key to resolve.
     * @param fallback The fallback value when the key is undefined.
     * @return The configured or fallback value.
     */
    default String get(final String key, final String fallback) {
        return this.getOrDefault(key, fallback);
    }

    /**
     * Indicates whether the supplied key is defined by this source.
     *
     * @param key The property-style or environment-style key to inspect.
     * @return {@code true} when the source declares a value for the key.
     */
    default boolean has(final String key) {
        return this.get(key) != null;
    }

    /**
     * Returns an immutable snapshot of all values exposed by this source.
     *
     * @return All key-value pairs known by the source.
     */
    default Map<String, String> getAll() {
        return Collections.emptyMap();
    }

    /**
     * Compatibility alias for callers still using the legacy configuration API.
     *
     * @param key The property-style or environment-style key to resolve.
     * @return The configured value, or {@code null} when the key is undefined.
     */
    default String getProperty(final String key) {
        return this.get(key);
    }

    /**
     * Compatibility alias for callers still using the legacy configuration API.
     *
     * @param key The property-style or environment-style key to resolve.
     * @param defaultValue The fallback value when the key is undefined.
     * @return The configured or fallback value.
     */
    default String getProperty(final String key, final String defaultValue) {
        return this.getOrDefault(key, defaultValue);
    }

    /**
     * Compatibility alias for callers still using the legacy configuration API.
     *
     * @param key The property-style or environment-style key to inspect.
     * @return {@code true} when the key is defined.
     */
    default boolean containsKey(final String key) {
        return this.has(key);
    }
}