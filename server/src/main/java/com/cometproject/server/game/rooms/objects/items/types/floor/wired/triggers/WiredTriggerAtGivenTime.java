package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredTriggerItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired trigger at given time behavior for the room subsystem.
 */
public class WiredTriggerAtGivenTime extends WiredTriggerItem {
    private static final int PARAM_TICK_LENGTH = 0;

    private boolean needsReset = false;

    /**
     * The default constructor.
     *
     * @param id       The ID of the item
     * @param itemId   The ID of the item definition
     * @param room     The instance of the room
     * @param owner    The ID of the owner
     * @param x        The position of the item on the X axis
     * @param y        The position of the item on the Y axis
     * @param z        The position of the item on the z axis
     * @param rotation The orientation of the item
     * @param data     The JSON object associated with this item
     */
    public WiredTriggerAtGivenTime(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        this.getWiredData().getParams().putIfAbsent(PARAM_TICK_LENGTH, 2); // 1s
    }

    /**
     * Executes execute triggers for this room contract.
     *
     * @param room Room participating in the operation.
     * @param timer Timer supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean executeTriggers(Room room, int timer) {
        boolean wasExecuted = false;

        for (RoomItemFloor wiredItem : getTriggers(room, WiredTriggerAtGivenTime.class)) {
            WiredTriggerAtGivenTime trigger = ((WiredTriggerAtGivenTime) wiredItem);

            if (timer >= trigger.getTime() && !trigger.needsReset) {
                if (trigger.evaluate(null, null)) {
                    wasExecuted = true;

                    trigger.needsReset = true;
                }
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
        return false;
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 6;
    }

    /**
     * Returns the time for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTime() {
        return this.getWiredData().getParams().get(PARAM_TICK_LENGTH);
    }

    /**
     * Updates the needs reset for this room contract.
     *
     * @param needsReset Needs reset supplied by the caller.
     */
    public void setNeedsReset(boolean needsReset) {
        this.needsReset = needsReset;
    }

    /**
     * Executes needs reset for this room contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean needsReset() {
        return this.needsReset;
    }
}
