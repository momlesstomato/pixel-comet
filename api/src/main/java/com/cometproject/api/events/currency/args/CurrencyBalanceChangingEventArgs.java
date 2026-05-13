package com.cometproject.api.events.currency.args;

import com.cometproject.storage.api.data.currency.CurrencyAdjustmentRequest;

/**
 * Cancellable arguments fired after current balance resolution and before persistence.
 */
public final class CurrencyBalanceChangingEventArgs extends CurrencyEventArgs {
    private final CurrencyAdjustmentRequest request;
    private final long oldBalance;
    private long newBalance;

    /**
     * Creates balance-changing event arguments.
     *
     * @param request    the validated adjustment request.
     * @param oldBalance the current balance before persistence.
     * @param newBalance the proposed balance after persistence.
     */
    public CurrencyBalanceChangingEventArgs(
            final CurrencyAdjustmentRequest request,
            final long oldBalance,
            final long newBalance) {
        this.request = request;
        this.oldBalance = oldBalance;
        this.newBalance = newBalance;
    }

    /**
     * Returns the validated adjustment request.
     *
     * @return the adjustment request.
     */
    public CurrencyAdjustmentRequest getRequest() {
        return this.request;
    }

    /**
     * Returns the current balance before persistence.
     *
     * @return the old balance.
     */
    public long getOldBalance() {
        return this.oldBalance;
    }

    /**
     * Returns the proposed balance after persistence.
     *
     * @return the proposed new balance.
     */
    public long getNewBalance() {
        return this.newBalance;
    }

    /**
     * Updates the proposed balance after persistence.
     *
     * @param newBalance the replacement new balance.
     */
    public void setNewBalance(final long newBalance) {
        this.newBalance = newBalance;
    }
}
