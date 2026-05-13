package com.cometproject.api.events.currency;

import java.util.Map;

/**
 * Event fired before a currency definition is created or updated.
 */
public final class CurrencyDefinitionUpsertRequestedEvent extends CancellableCurrencyConfigurationEvent {
    /**
     * Creates a definition-upsert-requested event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyDefinitionUpsertRequestedEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }
}
