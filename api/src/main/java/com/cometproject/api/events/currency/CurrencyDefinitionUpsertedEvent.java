package com.cometproject.api.events.currency;

import java.util.Map;

/**
 * Event fired after a currency definition is created or updated.
 */
public final class CurrencyDefinitionUpsertedEvent extends CurrencyConfigurationEvent {
    /**
     * Creates a definition-upserted event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyDefinitionUpsertedEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }
}
