package com.cometproject.api.events.currency;

import com.cometproject.api.events.Cancellable;

import java.util.Map;

/**
 * Base configuration event that plugins may cancel before persistence.
 */
public class CancellableCurrencyConfigurationEvent extends CurrencyConfigurationEvent implements Cancellable {
    private boolean cancelled;
    private String cancellationCode = "currency_configuration_cancelled";
    private String cancellationMessage = "Currency configuration change was cancelled.";

    /**
     * Creates a cancellable configuration event.
     *
     * @param action       the configuration action name.
     * @param currencyCode the affected currency code.
     * @param metadata     event metadata describing the changed resource.
     */
    public CancellableCurrencyConfigurationEvent(
            final String action,
            final String currencyCode,
            final Map<String, String> metadata) {
        super(action, currencyCode, metadata);
    }

    /**
     * Cancels the configuration event with a public code and message.
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
