package com.cometproject.api.config;

/**
 * Converts between the historic property-key format and the environment-key format.
 */
public final class ConfigurationKeys {
    private ConfigurationKeys() {
    }

    /**
     * Converts a property-style key such as {@code comet.db.host} into an
     * environment-style key such as {@code COMET_DB_HOST}.
     *
     * @param propertyKey The property-style key.
     * @return The normalized environment-style key.
     */
    public static String toEnvKey(final String propertyKey) {
        return propertyKey.toUpperCase().replace('.', '_');
    }

    /**
     * Converts an environment-style key such as {@code COMET_DB_HOST} into a
     * property-style key such as {@code comet.db.host}.
     *
     * @param envKey The environment-style key.
     * @return The normalized property-style key.
     */
    public static String toPropertyKey(final String envKey) {
        return envKey.toLowerCase().replace('_', '.');
    }
}