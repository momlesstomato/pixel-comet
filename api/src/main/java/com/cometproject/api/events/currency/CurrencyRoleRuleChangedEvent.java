package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.currency.args.CurrencyConfigurationEventArgs;

import java.util.function.Consumer;

/**
 * Event fired after a currency role rule is created, updated, or deleted.
 */
public final class CurrencyRoleRuleChangedEvent extends Event<CurrencyConfigurationEventArgs> {
    /**
     * Creates a role-rule-changed listener.
     *
     * @param eventConsumer the listener callback.
     */
    public CurrencyRoleRuleChangedEvent(final Consumer<CurrencyConfigurationEventArgs> eventConsumer) {
        super(eventConsumer);
    }
}
