package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired before reward logic requests a currency grant.
 */
public final class CurrencyRewardGrantRequestedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a reward-grant-requested listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyRewardGrantRequestedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
