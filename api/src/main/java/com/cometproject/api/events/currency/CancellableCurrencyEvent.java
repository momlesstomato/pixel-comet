package com.cometproject.api.events.currency;

import com.cometproject.api.events.Cancellable;
import com.cometproject.api.events.Event;

/**
 * Base class for currency events that may be cancelled before persistence.
 */
public abstract class CancellableCurrencyEvent extends Event implements Cancellable {
    private boolean cancelled;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
}
