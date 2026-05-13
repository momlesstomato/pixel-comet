package com.cometproject.api.events.currency.args;

import com.cometproject.storage.api.data.currency.CurrencyAdjustmentRequest;
import com.cometproject.storage.api.data.currency.CurrencyOperation;
import com.cometproject.storage.api.data.currency.CurrencySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Cancellable arguments fired before a currency adjustment is validated and persisted.
 */
public final class CurrencyAdjustmentRequestedEventArgs extends CurrencyEventArgs {
    private final int playerId;
    private String currencyCodeOrAlias;
    private CurrencyOperation operation;
    private long amount;
    private CurrencySource source;
    private boolean notifyPlayer;
    private Map<String, String> metadata;

    /**
     * Creates event arguments from a request object.
     *
     * @param request the requested currency adjustment.
     */
    public CurrencyAdjustmentRequestedEventArgs(final CurrencyAdjustmentRequest request) {
        this.playerId = request.getPlayerId();
        this.currencyCodeOrAlias = request.getCurrencyCodeOrAlias();
        this.operation = request.getOperation();
        this.amount = request.getAmount();
        this.source = request.getSource();
        this.notifyPlayer = request.shouldNotifyPlayer();
        this.metadata = new HashMap<>(request.getMetadata());
    }

    /**
     * Returns the immutable target player id.
     *
     * @return the target player id.
     */
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Returns the requested currency code or alias.
     *
     * @return the currency code or alias.
     */
    public String getCurrencyCodeOrAlias() {
        return this.currencyCodeOrAlias;
    }

    /**
     * Updates the requested currency code or alias.
     *
     * @param currencyCodeOrAlias the replacement currency code or alias.
     */
    public void setCurrencyCodeOrAlias(final String currencyCodeOrAlias) {
        this.currencyCodeOrAlias = currencyCodeOrAlias;
    }

    /**
     * Returns the requested operation.
     *
     * @return the requested operation.
     */
    public CurrencyOperation getOperation() {
        return this.operation;
    }

    /**
     * Updates the requested operation.
     *
     * @param operation the replacement operation.
     */
    public void setOperation(final CurrencyOperation operation) {
        this.operation = operation;
    }

    /**
     * Returns the requested amount.
     *
     * @return the amount.
     */
    public long getAmount() {
        return this.amount;
    }

    /**
     * Updates the requested amount.
     *
     * @param amount the replacement amount.
     */
    public void setAmount(final long amount) {
        this.amount = amount;
    }

    /**
     * Returns the audit source metadata.
     *
     * @return the source metadata.
     */
    public CurrencySource getSource() {
        return this.source;
    }

    /**
     * Updates the audit source metadata.
     *
     * @param source the replacement source metadata.
     */
    public void setSource(final CurrencySource source) {
        this.source = source;
    }

    /**
     * Returns whether the player should be notified immediately.
     *
     * @return true when a purse packet should be sent after persistence.
     */
    public boolean shouldNotifyPlayer() {
        return this.notifyPlayer;
    }

    /**
     * Updates whether the player should be notified immediately.
     *
     * @param notifyPlayer true when a purse packet should be sent after persistence.
     */
    public void setNotifyPlayer(final boolean notifyPlayer) {
        this.notifyPlayer = notifyPlayer;
    }

    /**
     * Returns mutable event metadata.
     *
     * @return event metadata.
     */
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    /**
     * Replaces event metadata.
     *
     * @param metadata the replacement metadata.
     */
    public void setMetadata(final Map<String, String> metadata) {
        this.metadata = metadata == null ? new HashMap<>() : new HashMap<>(metadata);
    }

    /**
     * Converts the possibly modified event arguments back to a request object.
     *
     * @return the adjusted request.
     */
    public CurrencyAdjustmentRequest toRequest() {
        return new CurrencyAdjustmentRequest(
                this.playerId,
                this.currencyCodeOrAlias,
                this.operation,
                this.amount,
                this.source,
                this.notifyPlayer,
                this.metadata);
    }
}
