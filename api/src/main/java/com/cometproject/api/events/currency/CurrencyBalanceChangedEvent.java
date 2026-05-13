package com.cometproject.api.events.currency;

import com.cometproject.api.events.Event;
import com.cometproject.storage.api.data.currency.CurrencyAdjustmentRequest;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;

/**
 * Event fired after a currency movement has been persisted.
 */
public final class CurrencyBalanceChangedEvent extends Event {
    private final CurrencyAdjustmentRequest request;
    private final CurrencyMovementResult movement;

    /**
     * Creates a balance-changed event.
     *
     * @param request  the request that produced the movement.
     * @param movement the persisted movement.
     */
    public CurrencyBalanceChangedEvent(
            final CurrencyAdjustmentRequest request,
            final CurrencyMovementResult movement) {
        this.request = request;
        this.movement = movement;
    }

    /**
     * Returns the request that produced the movement.
     *
     * @return the adjustment request.
     */
    public CurrencyAdjustmentRequest getRequest() {
        return this.request;
    }

    /**
     * Returns the persisted movement.
     *
     * @return the movement.
     */
    public CurrencyMovementResult getMovement() {
        return this.movement;
    }
}
