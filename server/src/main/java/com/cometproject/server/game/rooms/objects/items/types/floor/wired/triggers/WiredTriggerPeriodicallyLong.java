package com.cometproject.server.game.rooms.objects.items.types.floor.wired.triggers;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired trigger periodically long behavior for the room subsystem.
 */
public class WiredTriggerPeriodicallyLong extends WiredTriggerPeriodically {
    private static final int PARAM_TICK_LENGTH = 0;

    /**
     * Creates a wired trigger periodically long instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredTriggerPeriodicallyLong(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the tick count for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getTickCount() {
        return (this.getWiredData().getParams().get(PARAM_TICK_LENGTH)) * 10;
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
