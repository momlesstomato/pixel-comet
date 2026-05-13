package com.cometproject.api.events.currency;

import java.util.Map;

/**
 * Event fired before reward logic requests a currency grant.
 */
public final class CurrencyRewardGrantRequestedEvent extends CancellableCurrencyConfigurationEvent {
    /**
     * Creates a reward-grant-requested event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CurrencyRewardGrantRequestedEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }
}
