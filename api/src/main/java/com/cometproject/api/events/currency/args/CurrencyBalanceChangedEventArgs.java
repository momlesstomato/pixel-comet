package com.cometproject.api.events.currency.args;

import com.cometproject.api.events.EventArgs;
import com.cometproject.storage.api.data.currency.CurrencyAdjustmentRequest;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;

/**
 * Arguments fired after a currency movement has been persisted.
 */
public final class CurrencyBalanceChangedEventArgs extends EventArgs {
    private final CurrencyAdjustmentRequest request;
    private final CurrencyMovementResult movement;

    /**
     * Creates balance-changed event arguments.
     *
     * @param request  the request that produced the movement.
     * @param movement the persisted movement.
     */
    public CurrencyBalanceChangedEventArgs(
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
