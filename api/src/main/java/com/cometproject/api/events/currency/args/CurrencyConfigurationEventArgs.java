package com.cometproject.api.events.currency.args;

import java.util.HashMap;
import java.util.Map;

/**
 * Arguments for currency definition, alias, role-rule, and use-case configuration events.
 */
public final class CurrencyConfigurationEventArgs extends CurrencyEventArgs {
    private final String action;
    private String currencyCode;
    private Map<String, String> metadata;

    /**
     * Creates configuration event arguments.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyConfigurationEventArgs(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        this.action = action;
        this.currencyCode = currencyCode;
        this.metadata = metadata == null ? new HashMap<>() : new HashMap<>(metadata);
    }

    /**
     * Returns the configuration action name.
     *
     * @return the action name.
     */
    public String getAction() {
        return this.action;
    }

    /**
     * Returns the affected currency code.
     *
     * @return the currency code.
     */
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * Updates the affected currency code for requested configuration changes.
     *
     * @param currencyCode the replacement currency code.
     */
    public void setCurrencyCode(final String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * Returns mutable configuration metadata.
     *
     * @return configuration metadata.
     */
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    /**
     * Replaces configuration metadata.
     *
     * @param metadata the replacement metadata.
     */
    public void setMetadata(final Map<String, String> metadata) {
        this.metadata = metadata == null ? new HashMap<>() : new HashMap<>(metadata);
    }
}
