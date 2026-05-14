package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.custom.WiredConditionCustomHasDuckets;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition custom has duckets behavior for the room subsystem.
 */
public class WiredNegativeConditionCustomHasDuckets extends WiredConditionCustomHasDuckets {

    /**
     * Creates a wired negative condition custom has duckets instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionCustomHasDuckets(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
