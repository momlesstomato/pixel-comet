package com.cometproject.server.game.catalog.types;

import com.cometproject.api.game.catalog.types.ICatalogBundledItem;

/**
 * Describes catalog bundled item behavior for the catalog subsystem.
 */
public class CatalogBundledItem implements ICatalogBundledItem {

    private final int itemId;
    private final int amount;
    private final String presetData;

    /**
     * Creates a catalog bundled item instance for the catalog subsystem.
     *
     * @param presetData Preset data supplied by the caller.
     * @param amount Amount supplied by the caller.
     * @param itemId Item id supplied by the caller.
     */
    public CatalogBundledItem(String presetData, int amount, int itemId) {
        this.presetData = presetData;
        this.amount = amount;
        this.itemId = itemId;
    }

    /**
     * Returns the item id for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getItemId() {
        return itemId;
    }

    /**
     * Returns the amount for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the preset data for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPresetData() {
        return presetData;
    }

}