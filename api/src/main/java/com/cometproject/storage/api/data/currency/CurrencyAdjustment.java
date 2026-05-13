package com.cometproject.storage.api.data.currency;

/**
 * Request object for a single atomic currency balance adjustment.
 */
public final class CurrencyAdjustment {
    private final int playerId;
    private final long currencyId;
    private final String currencyCode;
    private final CurrencyOperation operation;
    private final long amount;
    private final CurrencySource source;

    /**
     * Creates a currency adjustment request.
     *
     * @param playerId     the target player id.
     * @param currencyId   the target currency id.
     * @param currencyCode the target currency code.
     * @param operation    the requested operation.
     * @param amount       the positive operation amount, or exact balance for {@link CurrencyOperation#SET}.
     * @param source       movement source metadata.
     */
    public CurrencyAdjustment(
            final int playerId,
            final long currencyId,
            final String currencyCode,
            final CurrencyOperation operation,
            final long amount,
            final CurrencySource source) {
        this.playerId = playerId;
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.operation = operation;
        this.amount = amount;
        this.source = source;
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
     * Returns the target currency id.
     *
     * @return the currency id.
     */
    public long getCurrencyId() {
        return this.currencyId;
    }

    /**
     * Returns the target currency code.
     *
     * @return the currency code.
     */
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * Returns the requested operation.
     *
     * @return the operation.
     */
    public CurrencyOperation getOperation() {
        return this.operation;
    }

    /**
     * Returns the operation amount.
     *
     * @return the amount.
     */
    public long getAmount() {
        return this.amount;
    }

    /**
     * Returns movement source metadata.
     *
     * @return the source metadata.
     */
    public CurrencySource getSource() {
        return this.source;
    }
}
