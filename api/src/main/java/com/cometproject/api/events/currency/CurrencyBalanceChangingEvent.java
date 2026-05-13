package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyBalanceChangingEventArgs;

import java.util.function.Consumer;

/**
 * Event fired after current balance resolution and before a currency movement is persisted.
 */
public final class CurrencyBalanceChangingEvent extends Event<CurrencyBalanceChangingEventArgs> {
    /**
     * Creates a balance-changing listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyBalanceChangingEvent(final Consumer<CurrencyBalanceChangingEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
