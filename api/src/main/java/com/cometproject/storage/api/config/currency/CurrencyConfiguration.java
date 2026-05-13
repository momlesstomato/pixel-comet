package com.cometproject.storage.api.config.currency;

import java.util.Map;

/**
 * Declares currency inventory configuration defaults.
 */
public final class CurrencyConfiguration {
    private CurrencyConfiguration() {
    }

    /**
     * Returns the default values for currency inventory configuration.
     *
     * @return the currency inventory defaults.
     */
    public static Map<String, String> defaults() {
        return Map.of();
    }
}
