package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired trigger leaves room behavior for the room subsystem.
 */
public class WiredTriggerLeavesRoom extends WiredTriggerItem {

    /**
     * Creates a wired trigger leaves room instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerLeavesRoom(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);
    }

    /**
     * Executes execute triggers for this room contract.
     *
     * @param playerEntity Player entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean executeTriggers(PlayerEntity playerEntity) {
        boolean wasExecuted = false;

        for (RoomItemFloor wiredItem : getTriggers(playerEntity.getRoom(), WiredTriggerLeavesRoom.class)) {
            WiredTriggerLeavesRoom trigger = ((WiredTriggerLeavesRoom) wiredItem);

            wasExecuted = trigger.evaluate(playerEntity, trigger);
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
