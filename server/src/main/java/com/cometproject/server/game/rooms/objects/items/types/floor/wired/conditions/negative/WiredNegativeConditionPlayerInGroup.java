package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionPlayerInGroup;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition player in group behavior for the room subsystem.
 */
public class WiredNegativeConditionPlayerInGroup extends WiredConditionPlayerInGroup {

    /**
     * Creates a wired negative condition player in group instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionPlayerInGroup(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
