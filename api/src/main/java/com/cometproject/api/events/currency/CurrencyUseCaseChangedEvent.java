package com.cometproject.api.events.currency;

import java.util.Map;

/**
 * Event fired after a currency use-case mapping changes.
 */
public final class CurrencyUseCaseChangedEvent extends CurrencyConfigurationEvent {
    /**
     * Creates a use-case-changed event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyUseCaseChangedEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }
}
