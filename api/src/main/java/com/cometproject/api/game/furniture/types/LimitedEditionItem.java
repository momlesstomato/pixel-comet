package com.cometproject.api.game.furniture.types;

/**
 * Defines the limited edition item contract for the furniture subsystem.
 */
public interface LimitedEditionItem {
    /**
     * Returns the item id associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getItemId();

    /**
     * Returns the limited rare associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLimitedRare();

    /**
     * Returns the limited rare total associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLimitedRareTotal();
}
