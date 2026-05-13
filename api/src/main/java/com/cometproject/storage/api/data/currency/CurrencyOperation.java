package com.cometproject.storage.api.data.currency;

/**
 * Describes the supported inventory mutation operations.
 */
public enum CurrencyOperation {
    /**
     * Adds a positive amount to the current balance.
     */
    ADD,

    /**
     * Removes a positive amount from the current balance.
     */
    REMOVE,

    /**
     * Replaces the current balance with an exact amount.
     */
    SET
}
