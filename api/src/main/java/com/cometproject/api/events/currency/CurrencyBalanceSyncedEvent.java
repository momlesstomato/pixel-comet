package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyBalanceSyncEventArgs;

import java.util.function.Consumer;

/**
 * Event fired after an immediate purse synchronization was evaluated.
 */
public final class CurrencyBalanceSyncedEvent extends Event<CurrencyBalanceSyncEventArgs> {
    /**
     * Creates a balance-synced listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyBalanceSyncedEvent(final Consumer<CurrencyBalanceSyncEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
