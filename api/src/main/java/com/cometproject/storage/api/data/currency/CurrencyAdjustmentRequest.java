package com.cometproject.storage.api.data.currency;

import java.util.Map;

/**
 * Describes a currency balance operation requested by gameplay, HTTP, or a plugin.
 *
 * <p>The request is immutable so callers can safely pass it through plugin events.
 * Event argument objects may create adjusted copies with the {@code with*} methods.
 */
public final class CurrencyAdjustmentRequest {
    private final int playerId;
    private final String currencyCodeOrAlias;
    private final CurrencyOperation operation;
    private final long amount;
    private final CurrencySource source;
    private final boolean notifyPlayer;
    private final Map<String, String> metadata;

    /**
     * Creates a currency adjustment request.
     *
     * @param playerId            the target player id.
     * @param currencyCodeOrAlias the requested currency code or alias.
     * @param operation           the requested operation.
     * @param amount              the positive delta, or exact target balance for set operations.
     * @param source              the audit source metadata.
     * @param notifyPlayer        whether an online player should receive an immediate purse update.
     * @param metadata            optional correlation metadata.
     */
    public CurrencyAdjustmentRequest(
            final int playerId,
            final String currencyCodeOrAlias,
            final CurrencyOperation operation,
            final long amount,
            final CurrencySource source,
            final boolean notifyPlayer,
            final Map<String, String> metadata) {
        this.playerId = playerId;
        this.currencyCodeOrAlias = currencyCodeOrAlias;
        this.operation = operation;
        this.amount = amount;
        this.source = source;
        this.notifyPlayer = notifyPlayer;
        this.metadata = metadata == null ? Map.of() : Map.copyOf(metadata);
    }

    /**
     * Returns the target player id.
     *
     * @return the player id.
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
     * Returns the requested operation.
     *
     * @return the requested operation.
     */
    public CurrencyOperation getOperation() {
        return this.operation;
    }

    /**
     * Returns the operation amount.
     *
     * @return the positive delta, or exact target balance for set operations.
     */
    public long getAmount() {
        return this.amount;
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
     * Returns whether the service should send an immediate purse update to an online player.
     *
     * @return true when the player should be notified.
     */
    public boolean shouldNotifyPlayer() {
        return this.notifyPlayer;
    }

    /**
     * Returns optional correlation metadata for plugins and management callers.
     *
     * @return immutable metadata keyed by snake_case names.
     */
    public Map<String, String> getMetadata() {
        return this.metadata;
    }

    /**
     * Returns a copy that targets a canonical or replacement currency code.
     *
     * @param currencyCodeOrAlias the replacement currency code or alias.
     * @return the adjusted request.
     */
    public CurrencyAdjustmentRequest withCurrencyCodeOrAlias(final String currencyCodeOrAlias) {
        return new CurrencyAdjustmentRequest(
                this.playerId,
                currencyCodeOrAlias,
                this.operation,
                this.amount,
                this.source,
                this.notifyPlayer,
                this.metadata);
    }

    /**
     * Returns a copy with a replacement operation.
     *
     * @param operation the replacement operation.
     * @return the adjusted request.
     */
    public CurrencyAdjustmentRequest withOperation(final CurrencyOperation operation) {
        return new CurrencyAdjustmentRequest(
                this.playerId,
                this.currencyCodeOrAlias,
                operation,
                this.amount,
                this.source,
                this.notifyPlayer,
                this.metadata);
    }

    /**
     * Returns a copy with a replacement amount.
     *
     * @param amount the replacement amount.
     * @return the adjusted request.
     */
    public CurrencyAdjustmentRequest withAmount(final long amount) {
        return new CurrencyAdjustmentRequest(
                this.playerId,
                this.currencyCodeOrAlias,
                this.operation,
                amount,
                this.source,
                this.notifyPlayer,
                this.metadata);
    }

    /**
     * Returns a copy with replacement source metadata.
     *
     * @param source the replacement source metadata.
     * @return the adjusted request.
     */
    public CurrencyAdjustmentRequest withSource(final CurrencySource source) {
        return new CurrencyAdjustmentRequest(
                this.playerId,
                this.currencyCodeOrAlias,
                this.operation,
                this.amount,
                source,
                this.notifyPlayer,
                this.metadata);
    }

    /**
     * Returns a copy with a replacement notification preference.
     *
     * @param notifyPlayer whether the player should be notified.
     * @return the adjusted request.
     */
    public CurrencyAdjustmentRequest withNotifyPlayer(final boolean notifyPlayer) {
        return new CurrencyAdjustmentRequest(
                this.playerId,
                this.currencyCodeOrAlias,
                this.operation,
                this.amount,
                this.source,
                notifyPlayer,
                this.metadata);
    }

    /**
     * Returns a copy with replacement metadata.
     *
     * @param metadata the replacement metadata.
     * @return the adjusted request.
     */
    public CurrencyAdjustmentRequest withMetadata(final Map<String, String> metadata) {
        return new CurrencyAdjustmentRequest(
                this.playerId,
                this.currencyCodeOrAlias,
                this.operation,
                this.amount,
                this.source,
                this.notifyPlayer,
                metadata);
    }
}
