package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired before a currency definition is created or updated.
 */
public final class CurrencyDefinitionUpsertRequestedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a definition-upsert-requested listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyDefinitionUpsertRequestedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
