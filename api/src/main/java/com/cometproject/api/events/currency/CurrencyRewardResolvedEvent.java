package com.cometproject.api.events.currency;

import java.util.Map;

/**
 * Event fired when reward logic resolves a configured currency reward.
 */
public final class CurrencyRewardResolvedEvent extends CurrencyConfigurationEvent {
    /**
     * Creates a reward-resolved event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyRewardResolvedEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }
}
