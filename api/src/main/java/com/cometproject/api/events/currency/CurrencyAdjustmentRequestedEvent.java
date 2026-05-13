package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyAdjustmentRequestedEventArgs;

import java.util.function.Consumer;

/**
 * Event fired before a currency adjustment is validated and persisted.
 */
public final class CurrencyAdjustmentRequestedEvent extends Event<CurrencyAdjustmentRequestedEventArgs> {
    /**
     * Creates a currency-adjustment listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyAdjustmentRequestedEvent(final Consumer<CurrencyAdjustmentRequestedEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
