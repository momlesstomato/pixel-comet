package com.cometproject.api.events.currency;

import java.util.Map;

/**
 * Event fired after a currency alias is created, updated, or deleted.
 */
public final class CurrencyAliasChangedEvent extends CurrencyConfigurationEvent {
    /**
     * Creates an alias-changed event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyAliasChangedEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }
}
