package com.cometproject.storage.api.data.currency;

/**
 * Represents a player's balance for a single configured currency.
 */
public interface IPlayerCurrencyBalance {
    /**
     * Returns the player that owns this balance.
     *
     * @return the player id.
     */
    int getPlayerId();

    /**
     * Returns the stable currency id for this balance.
     *
     * @return the currency id.
     */
    long getCurrencyId();

    /**
     * Returns the currency code for this balance.
     *
     * @return the currency code.
     */
    String getCurrencyCode();

    /**
     * Returns the current persisted balance.
     *
     * @return the balance amount.
     */
    long getBalance();
}
