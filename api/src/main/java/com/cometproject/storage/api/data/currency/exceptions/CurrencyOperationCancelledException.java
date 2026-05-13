package com.cometproject.storage.api.data.currency.exceptions;

/**
 * Raised when a cancellable currency event vetoes an operation before persistence.
 */
public final class CurrencyOperationCancelledException extends CurrencyAdjustmentException {
    private final String cancellationCode;

    /**
     * Creates a currency operation cancellation exception.
     *
     * @param cancellationCode the machine-readable cancellation code.
     * @param message          the public cancellation message.
     */
    public CurrencyOperationCancelledException(final String cancellationCode, final String message) {
        super(message);
        this.cancellationCode = cancellationCode;
    }

    /**
     * Returns the machine-readable cancellation code.
     *
     * @return the cancellation code.
     */
    public String getCancellationCode() {
        return this.cancellationCode;
    }
}
