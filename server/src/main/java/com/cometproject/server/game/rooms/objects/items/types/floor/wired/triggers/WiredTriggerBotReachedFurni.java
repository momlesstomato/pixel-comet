package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes wired trigger bot reached furni behavior for the room subsystem.
 */
public class WiredTriggerBotReachedFurni extends WiredTriggerItem {

    /**
     * Creates a wired trigger bot reached furni instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerBotReachedFurni(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes execute triggers for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param floorItem Floor item supplied by the caller.
     * @param username Username supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean executeTriggers(RoomEntity entity, RoomItemFloor floorItem, String username) {
        boolean wasExecuted = false;

        for (RoomItemFloor floorItemm : getTriggers(entity.getRoom(), WiredTriggerBotReachedFurni.class)) {
            WiredTriggerBotReachedFurni trigger = ((WiredTriggerBotReachedFurni) floorItemm);

            if (trigger.getWiredData().getText().isEmpty() || trigger.getWiredData().getText().equals(username)) {
                if (trigger.getWiredData().getSelectedIds().contains(floorItem.getId())) {
                    trigger.evaluate(entity, null);
                }
            }

        }

        return wasExecuted;
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 13;
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
