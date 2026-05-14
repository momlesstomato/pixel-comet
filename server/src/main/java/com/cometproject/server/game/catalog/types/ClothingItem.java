package com.cometproject.server.game.catalog.types;

import com.cometproject.api.game.catalog.types.IClothingItem;

/**
 * Describes clothing item behavior for the catalog subsystem.
 */
public class ClothingItem implements IClothingItem {
    private final String itemName;

    private final int[] parts;

    /**
     * Creates a clothing item instance for the catalog subsystem.
     *
     * @param itemName Item name supplied by the caller.
     * @param parts Parts supplied by the caller.
     */
    public ClothingItem(final String itemName, final int[] parts) {
        this.itemName = itemName;
        this.parts = parts;
    }

    /**
     * Returns the item name for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Returns the parts for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int[] getParts() {
        return parts;
    }
}
