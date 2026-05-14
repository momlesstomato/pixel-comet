package com.cometproject.api.game.catalog.types;

/**
 * Defines the i catalog bundled item contract for the catalog subsystem.
 */
public interface ICatalogBundledItem {
    /**
     * Returns the item id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getItemId();

    /**
     * Returns the amount associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAmount();

    /**
     * Returns the preset data associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getPresetData();
}
