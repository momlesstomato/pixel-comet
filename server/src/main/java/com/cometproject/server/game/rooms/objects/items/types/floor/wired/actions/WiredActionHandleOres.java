package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.players.components.types.inventory.InventoryItem;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredUtil;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.data.Data;


/**
 * Describes wired action handle ores behavior for the room subsystem.
 */
public class WiredActionHandleOres extends WiredActionItem {

    /**
     * Creates a wired action handle ores instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredActionHandleOres(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the event complete callback for this room contract.
     *
     * @param event Event supplied by the caller.
     */
    @Override
    public void onEventComplete(WiredItemEvent event) {
        if (this.getWiredData() == null || this.getWiredData().getSelectedIds() == null || this.getWiredData().getSelectedIds().isEmpty()) {
            event.entity = null;
            return;
        }

        Long itemId = WiredUtil.getRandomElement(this.getWiredData().getSelectedIds());

        if (itemId == null)
            return;

        RoomItemFloor item = this.getRoom().getItems().getFloorItem(itemId);

        if (item == null || item.isAtDoor() || item.getPosition() == null || item.getTile() == null)
            return;

        int rewardId = CometSettings.oreItemId;

        FurnitureDefinition itemDefinition = ItemManager.getInstance().getDefinition(rewardId);


        if (itemDefinition != null) {
            final Data<Long> newItem = Data.createEmpty();
            StorageContext.getCurrentContext().getRoomItemRepository().createItem(1, rewardId, "0", newItem::set);

            PlayerItem playerItem = new InventoryItem(newItem.get(), rewardId, "0");
            this.getRoom().getItems().placeFloorItem(playerItem, item.getPosition().getX(), item.getPosition().getY(), item.getRotation());

        }
        // Function for spawning ore's.
    }


    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 0;
    }

    /**
     * Executes requires player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean requiresPlayer() {
        return false;
    }
}
