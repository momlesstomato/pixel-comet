package com.cometproject.server.config;

import com.cometproject.server.storage.queries.config.LocaleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * Describes locale behavior for the configuration subsystem.
 */
public class Locale {
    /**
     * Logging for locale object.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Locale.class);

    /**
     * Store locale in memory.
     */
    private static Map<String, String> locale;

    /**
     * Initialize the locale.
     */
    public static void initialize() {
        reload();
    }

    /**
     * Load locale from the database.
     */
    public static void reload() {
        if (locale != null)
            locale.clear();

        locale = LocaleDao.getAll();
        LOGGER.info("Loaded " + locale.size() + " locale strings");
    }

    /**
     * Get a locale string by the key.
     *
     * @param key Retrieve from the locale by the key
     * @return String from the locale
     */
    public static String get(String key) {
        if (locale.containsKey(key))
            return locale.get(key);
        else
            return key;
    }

    /**
     * Returns the or default for this configuration contract.
     *
     * @param key Key supplied by the caller.
     * @param defaultValue Default value supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static String getOrDefault(String key, String defaultValue) {
        if (!locale.containsKey(key)) {
            return defaultValue;
        }

        return locale.get(key);
    }

    /**
     * Returns the all for this configuration contract.
     *
     * @return Value exposed by the contract.
     */
    public static Map<String, String> getAll() {
        return locale;
    }
}
