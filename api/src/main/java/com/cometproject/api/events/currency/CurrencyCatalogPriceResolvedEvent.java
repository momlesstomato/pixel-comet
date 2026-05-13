package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired when catalog pricing resolves a configured currency boundary.
 */
public final class CurrencyCatalogPriceResolvedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a catalog-price-resolved listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyCatalogPriceResolvedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
