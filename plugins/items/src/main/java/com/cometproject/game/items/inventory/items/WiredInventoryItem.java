package com.cometproject.game.items.inventory.items;

import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.players.data.components.inventory.InventoryItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.game.items.inventory.InventoryItem;

/**
 * Describes wired inventory item behavior for the item subsystem.
 */
public class WiredInventoryItem extends InventoryItem {
    /**
     * Creates a wired inventory item instance for the item subsystem.
     *
     * @param inventoryItemData Inventory item data supplied by the caller.
     * @param furnitureDefinition Furniture definition supplied by the caller.
     */
    public WiredInventoryItem(InventoryItemData inventoryItemData, FurnitureDefinition furnitureDefinition) {
        super(inventoryItemData, furnitureDefinition);
    }

    /**
     * Executes compose data for this item contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean composeData(IComposer msg) {
        super.composeData(msg);

        msg.writeString("");
        return true;
    }

}
