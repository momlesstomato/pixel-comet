package com.cometproject.storage.api.data.currency;

/**
 * Immutable player currency balance.
 */
public final class PlayerCurrencyBalance implements IPlayerCurrencyBalance {
    private final int playerId;
    private final long currencyId;
    private final String currencyCode;
    private final long balance;

    /**
     * Creates a player currency balance snapshot.
     *
     * @param playerId     the player id.
     * @param currencyId   the currency id.
     * @param currencyCode the currency code.
     * @param balance      the current balance.
     */
    public PlayerCurrencyBalance(final int playerId, final long currencyId, final String currencyCode, final long balance) {
        this.playerId = playerId;
        this.currencyId = currencyId;
        this.currencyCode = currencyCode;
        this.balance = balance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getCurrencyId() {
        return this.currencyId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getBalance() {
        return this.balance;
    }
}
