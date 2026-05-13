package com.cometproject.api.events.currency.args;

import com.cometproject.api.events.EventArgs;

/**
 * Base event arguments for cancellable currency events.
 */
public abstract class CurrencyEventArgs extends EventArgs {
    private String cancellationCode = "currency_operation_cancelled";
    private String cancellationMessage = "Currency operation was cancelled.";

    /**
     * Cancels the currency event with a public code and message.
     *
     * @param code    the machine-readable cancellation code.
     * @param message the public cancellation message.
     */
    public void cancel(final String code, final String message) {
        this.cancellationCode = code;
        this.cancellationMessage = message;
        this.setCancelled(true);
    }

    /**
     * Returns the machine-readable cancellation code.
     *
     * @return the cancellation code.
     */
    public String getCancellationCode() {
        return this.cancellationCode;
    }

    /**
     * Returns the public cancellation message.
     *
     * @return the cancellation message.
     */
    public String getCancellationMessage() {
        return this.cancellationMessage;
    }
}
