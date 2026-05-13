package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyBalanceChangedEventArgs;

import java.util.function.Consumer;

/**
 * Event fired after a currency movement has been persisted.
 */
public final class CurrencyBalanceChangedEvent extends Event<CurrencyBalanceChangedEventArgs> {
    /**
     * Creates a balance-changed listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyBalanceChangedEvent(final Consumer<CurrencyBalanceChangedEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
