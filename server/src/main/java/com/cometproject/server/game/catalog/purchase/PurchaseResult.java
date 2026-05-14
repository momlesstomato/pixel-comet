package com.cometproject.server.game.catalog.purchase;

/**
 * Describes purchase result behavior for the catalog subsystem.
 */
public class PurchaseResult {
    private int amount;
    private String extraData;

    /**
     * Creates a purchase result instance for the catalog subsystem.
     *
     * @param amount Amount supplied by the caller.
     * @param extraData Extra data supplied by the caller.
     */
    public PurchaseResult(int amount, String extraData) {
        this.amount = amount;
        this.extraData = extraData;
    }

    /**
     * Returns the amount for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the extra data for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public String getExtraData() {
        return this.extraData;
    }
}
