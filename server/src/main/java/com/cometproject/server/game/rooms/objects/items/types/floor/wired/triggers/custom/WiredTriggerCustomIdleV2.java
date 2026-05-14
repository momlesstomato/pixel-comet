package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired trigger custom idle v2 behavior for the room subsystem.
 */
public class WiredTriggerCustomIdleV2 extends WiredTriggerItem {

    private static final int PARAM_TICK_LENGTH = 0;

    /**
     * Creates a wired trigger custom idle v2 instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerCustomIdleV2(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);
    }

    /**
     * Executes execute triggers for this room contract.
     *
     * @param playerEntity Player entity supplied by the caller.
     * @param time Time supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean executeTriggers(PlayerEntity playerEntity, int time) {
        boolean wasExecuted = false;

        for (WiredTriggerCustomIdleV2 wiredItem : getTriggers(playerEntity.getRoom(), WiredTriggerCustomIdleV2.class)) {
            WiredTriggerCustomIdleV2 trigger = wiredItem;

            if (time > trigger.getTime() || playerEntity.isIdle()) {
                if (trigger.evaluate(playerEntity, trigger)) {
                    wasExecuted = true;
                }
            }
        }

        return wasExecuted;
    }
    /**
     * Returns the time for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTime() {
        return (this.getWiredData().getParams().get(PARAM_TICK_LENGTH)) * 10;
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
        return 12;
    }
}
