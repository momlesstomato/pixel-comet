package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired trigger custom idle behavior for the room subsystem.
 */
public class WiredTriggerCustomIdle extends WiredTriggerItem {

    /**
     * Creates a wired trigger custom idle instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerCustomIdle(RoomItemData roomItemData, Room room) {
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

        for (RoomItemFloor wiredItem : getTriggers(playerEntity.getRoom(), WiredTriggerCustomIdle.class)) {
            WiredTriggerCustomIdle trigger = ((WiredTriggerCustomIdle) wiredItem);

            if (playerEntity.isIdle()) {
                wasExecuted = trigger.evaluate(playerEntity, trigger);
            }
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
        return true;
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
