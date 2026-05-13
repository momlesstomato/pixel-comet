package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired after a currency definition is created or updated.
 */
public final class CurrencyDefinitionUpsertedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a definition-upserted listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyDefinitionUpsertedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
