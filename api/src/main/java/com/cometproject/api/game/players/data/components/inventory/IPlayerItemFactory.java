package com.cometproject.api.game.players.data.components.inventory;

/**
 * Defines the i player item factory contract for the player subsystem.
 */
public interface IPlayerItemFactory {

    /**
     * Creates item data for this player contract.
     *
     * @param itemData Item data value supplied by the caller.
     * @return Result produced by the mutation.
     */
    PlayerItem createItem(InventoryItemData itemData);

}
