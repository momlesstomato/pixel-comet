package com.cometproject.api.events.currency;

import java.util.Map;

/**
 * Event fired when catalog pricing resolves a configured currency boundary.
 */
public final class CurrencyCatalogPriceResolvedEvent extends CurrencyConfigurationEvent {
    /**
     * Creates a catalog-price-resolved event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyCatalogPriceResolvedEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }
}
