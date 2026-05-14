package com.cometproject.api.game.catalog.types.purchase;

import com.cometproject.api.game.catalog.types.ICatalogPage;
import com.cometproject.api.game.catalog.types.bundles.IRoomBundle;
import com.cometproject.api.game.furniture.types.IGiftData;
import com.cometproject.api.networking.sessions.ISession;

import java.util.List;

/**
 * Defines the i catalog purchase handler contract for the catalog subsystem.
 */
public interface ICatalogPurchaseHandler {
    /**
     * Executes the purchase item operation for this catalog contract.
     *
     * @param client Client value supplied by the caller.
     * @param pageId Page id value supplied by the caller.
     * @param itemId Item id value supplied by the caller.
     * @param data Data value supplied by the caller.
     * @param amount Amount value supplied by the caller.
     * @param giftData Gift data value supplied by the caller.
     */
    void purchaseItem(ISession client, int pageId, int itemId, String data, int amount, IGiftData giftData);

    /**
     * Executes the handle operation for this catalog contract.
     *
     * @param client Client value supplied by the caller.
     * @param pageId Page id value supplied by the caller.
     * @param itemId Item id value supplied by the caller.
     * @param data Data value supplied by the caller.
     * @param amount Amount value supplied by the caller.
     * @param giftData Gift data value supplied by the caller.
     */
    void handle(ISession client, int pageId, int itemId, String data, int amount, IGiftData giftData);

    /**
     * Executes the deliver gift operation for this catalog contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param giftData Gift data value supplied by the caller.
     * @param newItems New items value supplied by the caller.
     * @param senderUsername Sender username value supplied by the caller.
     */
    void deliverGift(int playerId, IGiftData giftData, List<Long> newItems, String senderUsername);

    /**
     * Executes the purchase bundle operation for this catalog contract.
     *
     * @param roomBundle Room bundle value supplied by the caller.
     * @param client Client value supplied by the caller.
     */
    void purchaseBundle(IRoomBundle roomBundle, ISession client);

    /**
     * Executes the purchase bundle operation for this catalog contract.
     *
     * @param page Page value supplied by the caller.
     * @param client Client value supplied by the caller.
     */
    void purchaseBundle(ICatalogPage page, ISession client);

    /**
     * Executes the apply discount operation for this catalog contract.
     *
     * @param cost Cost value supplied by the caller.
     * @param quantity Quantity value supplied by the caller.
     * @return Result produced by the operation.
     */
    int applyDiscount(int cost, int quantity);
}
