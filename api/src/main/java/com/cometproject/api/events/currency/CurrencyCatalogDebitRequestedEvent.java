package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired before catalog pricing requests a currency debit.
 */
public final class CurrencyCatalogDebitRequestedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a catalog-debit-requested listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyCatalogDebitRequestedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
