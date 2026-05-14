package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionTriggererOnFurniStaff;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition triggerer on furni staff behavior for the room subsystem.
 */
public class WiredNegativeConditionTriggererOnFurniStaff extends WiredConditionTriggererOnFurniStaff {
    /**
     * Creates a wired negative condition triggerer on furni staff instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionTriggererOnFurniStaff(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
