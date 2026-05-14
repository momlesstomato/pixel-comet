package com.cometproject.server.game.catalog.types;

import com.cometproject.api.game.catalog.types.ICatalogOffer;

/**
 * Describes catalog offer behavior for the catalog subsystem.
 */
public class CatalogOffer implements ICatalogOffer {
    private int offerId;
    private int catalogPageId;
    private int catalogItemId;

    /**
     * Creates a catalog offer instance for the catalog subsystem.
     *
     * @param offerId Offer id supplied by the caller.
     * @param catalogPageId Catalog page id supplied by the caller.
     * @param catalogItemId Catalog item id supplied by the caller.
     */
    public CatalogOffer(int offerId, int catalogPageId, int catalogItemId) {
        this.offerId = offerId;
        this.catalogPageId = catalogPageId;
        this.catalogItemId = catalogItemId;
    }

    /**
     * Returns the offer id for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getOfferId() {
        return offerId;
    }

    /**
     * Returns the catalog page id for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCatalogPageId() {
        return catalogPageId;
    }

    /**
     * Returns the catalog item id for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCatalogItemId() {
        return catalogItemId;
    }
}
