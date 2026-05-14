package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionStuffIs;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes wired negative condition stuff is behavior for the room subsystem.
 */
public class WiredNegativeConditionStuffIs extends WiredConditionStuffIs {
    /**
     * Creates a wired negative condition stuff is instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionStuffIs(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
