package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired when reward logic resolves a configured currency reward.
 */
public final class CurrencyRewardResolvedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a reward-resolved listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyRewardResolvedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
