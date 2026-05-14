package com.cometproject.api.game.catalog.types;

/**
 * Defines the i clothing item contract for the catalog subsystem.
 */
public interface IClothingItem {
    /**
     * Returns the item name associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getItemName();

    /**
     * Returns the parts associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int[] getParts();
}
