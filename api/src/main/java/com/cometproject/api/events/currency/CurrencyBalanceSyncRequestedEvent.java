package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyBalanceSyncEventArgs;

import java.util.function.Consumer;

/**
 * Event fired before the server attempts an immediate purse synchronization.
 */
public final class CurrencyBalanceSyncRequestedEvent extends Event<CurrencyBalanceSyncEventArgs> {
    /**
     * Creates a balance-sync-requested listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyBalanceSyncRequestedEvent(final Consumer<CurrencyBalanceSyncEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
