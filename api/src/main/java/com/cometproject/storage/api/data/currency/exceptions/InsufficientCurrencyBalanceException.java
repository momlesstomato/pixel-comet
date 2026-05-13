package com.cometproject.storage.api.data.currency.exceptions;

/**
 * Indicates that a debit would make a player currency balance negative.
 */
public final class InsufficientCurrencyBalanceException extends CurrencyAdjustmentException {
    /**
     * Creates an exception for a rejected currency debit.
     *
     * @param currencyCode the currency code.
     * @param balance      the current balance.
     * @param amount       the requested debit amount.
     */
    public InsufficientCurrencyBalanceException(
            final String currencyCode,
            final long balance,
            final long amount) {
        super("Insufficient balance for " + currencyCode + ": " + balance + " available, " + amount + " requested");
    }
}
