package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionHasFurniOn;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition has furni on behavior for the room subsystem.
 */
public class WiredNegativeConditionHasFurniOn extends WiredConditionHasFurniOn {

    /**
     * Creates a wired negative condition has furni on instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionHasFurniOn(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
