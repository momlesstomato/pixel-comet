package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionMatchSnapshot;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Carries wired negative condition match snapshot data for the room subsystem.
 */
public class WiredNegativeConditionMatchSnapshot extends WiredConditionMatchSnapshot {
    /**
     * Creates a wired negative condition match snapshot instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionMatchSnapshot(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
