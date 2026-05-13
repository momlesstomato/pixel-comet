package com.cometproject.storage.api.data.currency.exceptions;

/**
 * Indicates that a write was rejected because the target currency is disabled.
 */
public final class CurrencyDisabledException extends CurrencyAdjustmentException {
    /**
     * Creates an exception for a disabled currency.
     *
     * @param currencyCode the disabled currency code.
     */
    public CurrencyDisabledException(final String currencyCode) {
        super("Currency is disabled: " + currencyCode);
    }
}
