package com.cometproject.api.events.currency;

import java.util.Map;

/**
 * Event fired before catalog pricing requests a currency debit.
 */
public final class CurrencyCatalogDebitRequestedEvent extends CancellableCurrencyConfigurationEvent {
    /**
     * Creates a catalog-debit-requested event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyCatalogDebitRequestedEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }
}
