package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionTriggererOnFurni;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition triggerer on furni behavior for the room subsystem.
 */
public class WiredNegativeConditionTriggererOnFurni extends WiredConditionTriggererOnFurni {
    /**
     * Creates a wired negative condition triggerer on furni instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionTriggererOnFurni(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
