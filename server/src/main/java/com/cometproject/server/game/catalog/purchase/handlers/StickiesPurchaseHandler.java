package com.cometproject.server.game.catalog.purchase.handlers;

import com.cometproject.api.game.catalog.types.ICatalogItem;
import com.cometproject.server.game.catalog.purchase.PurchaseHandler;
import com.cometproject.server.game.catalog.purchase.PurchaseResult;
import com.cometproject.server.network.sessions.Session;

/**
 * Describes stickies purchase handler behavior for the catalog subsystem.
 */
public class StickiesPurchaseHandler implements PurchaseHandler {
    /**
     * Handles purchase data for this catalog contract.
     *
     * @param session Session participating in the operation.
     * @param purchaseData Purchase data supplied by the caller.
     * @param catalogItem Catalog item supplied by the caller.
     * @param amount Amount supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public PurchaseResult handlePurchaseData(Session session, String purchaseData, ICatalogItem catalogItem, int amount) {
        return new PurchaseResult(amount * 20, "");
    }
}
