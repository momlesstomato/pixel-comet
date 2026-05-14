package com.cometproject.server.game.catalog.purchase;

import com.cometproject.api.game.catalog.types.ICatalogItem;
import com.cometproject.server.network.sessions.Session;

/**
 * Defines the purchase handler contract for the catalog subsystem.
 */
public interface PurchaseHandler {
    /**
     * Executes handle purchase data for this Comet contract.
     *
     * @param session Session supplied by the caller.
     * @param purchaseData Purchase data supplied by the caller.
     * @param catalogItem Catalog item supplied by the caller.
     * @param amount Amount supplied by the caller.
     * @return Value exposed by the contract.
     */
    PurchaseResult handlePurchaseData(Session session, String purchaseData, ICatalogItem catalogItem, int amount);
}
