package com.cometproject.api.game.catalog.types.purchase;

/**
 * Represents a catalog purchase request used for batching item grants.
 */
public class CatalogPurchase {
    /**
     * The ID of the player who purchased the item.
     */
    private int playerId;

    /**
     * The item definition ID of the item.
     */
    private int itemBaseId;

    /**
     * The data generated for item types such as trophies.
     */
    private String data;

    /**
     * Creates a catalog purchase record.
     *
     * @param playerId The ID of the player who purchased the item.
     * @param itemBaseId The item definition ID of the item.
     * @param data The data generated for item types such as trophies.
     */
    public CatalogPurchase(int playerId, int itemBaseId, String data) {
        this.playerId = playerId;
        this.itemBaseId = itemBaseId;
        this.data = data;
    }

    /**
     * Returns the player ID for the buyer.
     *
     * @return The ID of the player who purchased the item.
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * Returns the purchased item definition ID.
     *
     * @return The item definition ID.
     */
    public int getItemBaseId() {
        return itemBaseId;
    }

    /**
     * Returns custom item data generated for the purchase.
     *
     * @return The data generated for item types such as trophies.
     */
    public String getData() {
        return data;
    }
}
