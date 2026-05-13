package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired after a currency alias is created, updated, or deleted.
 */
public final class CurrencyAliasChangedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates an alias-changed listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyAliasChangedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
