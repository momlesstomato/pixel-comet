package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.custom.WiredConditionCustomHasRank;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition custom has rank behavior for the room subsystem.
 */
public class WiredNegativeConditionCustomHasRank extends WiredConditionCustomHasRank {

    /**
     * Creates a wired negative condition custom has rank instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionCustomHasRank(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
