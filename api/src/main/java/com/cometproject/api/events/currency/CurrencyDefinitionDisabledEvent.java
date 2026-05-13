package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired after a currency definition is disabled.
 */
public final class CurrencyDefinitionDisabledEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a definition-disabled listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyDefinitionDisabledEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
