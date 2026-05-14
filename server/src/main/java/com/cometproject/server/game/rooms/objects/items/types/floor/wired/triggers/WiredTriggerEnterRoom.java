package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired trigger enter room behavior for the room subsystem.
 */
public class WiredTriggerEnterRoom extends WiredTriggerItem {

    /**
     * Creates a wired trigger enter room instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerEnterRoom(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes execute triggers for this room contract.
     *
     * @param playerEntity Player entity supplied by the caller.
     */
    public static void executeTriggers(PlayerEntity playerEntity) {
        if (playerEntity == null || playerEntity.getRoom() == null || playerEntity.getRoom().getItems() == null) {
            return;
        }

        for (RoomItemFloor floorItem : getTriggers(playerEntity.getRoom(), WiredTriggerEnterRoom.class)) {
            if (!(floorItem instanceof WiredTriggerEnterRoom)) continue;

            WiredTriggerEnterRoom trigger = ((WiredTriggerEnterRoom) floorItem);

            if (trigger.getWiredData().getText().isEmpty() || trigger.getWiredData().getText().equals(playerEntity.getUsername())) {
                trigger.evaluate(playerEntity, null);
            }
        }
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 7;
    }

    /**
     * Executes supplies player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean suppliesPlayer() {
        return true;
    }
}
