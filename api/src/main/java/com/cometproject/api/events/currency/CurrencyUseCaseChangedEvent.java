package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired after a currency use-case mapping changes.
 */
public final class CurrencyUseCaseChangedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a use-case-changed listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyUseCaseChangedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
