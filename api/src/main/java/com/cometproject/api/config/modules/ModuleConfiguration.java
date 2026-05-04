package com.cometproject.api.config.modules;

import java.util.Locale;
import java.util.Map;

/**
 * Declares plugin-module configuration keys and module env-key helpers.
 */
public final class ModuleConfiguration {
    public static final String MODULES_DIR = "comet.modules.dir";
    private static final String MODULE_PREFIX = "MODULE_";
    private static final String COMET_PREFIX = "COMET_";

    private ModuleConfiguration() {
    }

    /**
     * Returns the default values for the plugin module configuration group.
     *
     * @return The default plugin module values.
     */
    public static Map<String, String> defaults() {
        return Map.of(MODULES_DIR, "./modules");
    }

    /**
     * Builds the module-scoped environment variable key for the supplied module name and key.
     *
     * @param moduleName The module name declared in {@code module.json}.
     * @param key The module-local configuration key.
     * @return The module-scoped environment variable key.
     */
    public static String toModuleEnvironmentKey(final String moduleName, final String key) {
        return MODULE_PREFIX + normalizeModuleNamespace(moduleName) + "_" + normalizeSegment(key);
    }

    /**
     * Builds the fallback module-scoped environment variable key with a trimmed leading
     * {@code COMET_} namespace when present.
     *
     * @param moduleName The module name declared in {@code module.json}.
     * @param key The module-local configuration key.
     * @return The shortened module-scoped environment variable key.
     */
    public static String toShortModuleEnvironmentKey(final String moduleName, final String key) {
        final String namespace = normalizeModuleNamespace(moduleName);
        final String shortenedNamespace = namespace.startsWith(COMET_PREFIX)
                ? namespace.substring(COMET_PREFIX.length())
                : namespace;
        return MODULE_PREFIX + shortenedNamespace + "_" + normalizeSegment(key);
    }

    private static String normalizeModuleNamespace(final String moduleName) {
        return normalizeSegment(moduleName).replaceAll("_+", "_");
    }

    private static String normalizeSegment(final String value) {
        return value.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]+", "_");
    }
}