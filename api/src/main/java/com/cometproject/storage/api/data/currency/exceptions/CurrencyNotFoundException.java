package com.cometproject.storage.api.data.currency.exceptions;

/**
 * Indicates that a requested currency code does not exist.
 */
public final class CurrencyNotFoundException extends CurrencyAdjustmentException {
    /**
     * Creates an exception for an unknown currency code.
     *
     * @param currencyCode the missing currency code.
     */
    public CurrencyNotFoundException(final String currencyCode) {
        super("Currency does not exist: " + currencyCode);
    }
}
