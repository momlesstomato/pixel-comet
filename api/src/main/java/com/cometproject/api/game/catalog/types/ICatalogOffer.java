package com.cometproject.api.game.catalog.types;

/**
 * Defines the i catalog offer contract for the catalog subsystem.
 */
public interface ICatalogOffer {
    /**
     * Returns the offer id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getOfferId();

    /**
     * Returns the catalog page id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCatalogPageId();

    /**
     * Returns the catalog item id associated with this catalog contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCatalogItemId();
}
