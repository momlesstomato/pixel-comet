package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionPlayerCountInRoom;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition player count in room behavior for the room subsystem.
 */
public class WiredNegativeConditionPlayerCountInRoom extends WiredConditionPlayerCountInRoom {

    /**
     * Creates a wired negative condition player count in room instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionPlayerCountInRoom(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
