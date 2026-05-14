package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.custom.WiredConditionCustomHasDance;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition custom has dance behavior for the room subsystem.
 */
public class WiredNegativeConditionCustomHasDance extends WiredConditionCustomHasDance {

    /**
     * Creates a wired negative condition custom has dance instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionCustomHasDance(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
