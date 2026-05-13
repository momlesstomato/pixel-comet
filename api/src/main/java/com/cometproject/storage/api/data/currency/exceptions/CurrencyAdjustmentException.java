package com.cometproject.storage.api.data.currency.exceptions;

/**
 * Base runtime exception for currency inventory adjustment failures.
 */
public class CurrencyAdjustmentException extends RuntimeException {
    /**
     * Creates an adjustment exception with a message.
     *
     * @param message the failure message.
     */
    public CurrencyAdjustmentException(final String message) {
        super(message);
    }

    /**
     * Creates an adjustment exception with a message and cause.
     *
     * @param message the failure message.
     * @param cause   the original failure.
     */
    public CurrencyAdjustmentException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
