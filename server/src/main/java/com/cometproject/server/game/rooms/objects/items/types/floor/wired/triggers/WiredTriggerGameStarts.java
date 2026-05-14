package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired trigger game starts behavior for the room subsystem.
 */
public class WiredTriggerGameStarts extends WiredTriggerItem {

    /**
     * Creates a wired trigger game starts instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerGameStarts(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes execute triggers for this room contract.
     *
     * @param room Room participating in the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean executeTriggers(Room room) {
        boolean wasExecuted = false;

        for (RoomItemFloor floorItem : getTriggers(room, WiredTriggerGameStarts.class)) {
            WiredTriggerGameStarts trigger = ((WiredTriggerGameStarts) floorItem);

            wasExecuted = trigger.evaluate(null, null);
        }

        return wasExecuted;
    }

    /**
     * Executes supplies player for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean suppliesPlayer() {
        return false;
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 9;
    }
}
