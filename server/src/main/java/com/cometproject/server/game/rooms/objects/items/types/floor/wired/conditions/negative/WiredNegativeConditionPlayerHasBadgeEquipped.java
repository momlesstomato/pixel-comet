package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionPlayerHasBadgeEquipped;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition player has badge equipped behavior for the room subsystem.
 */
public class WiredNegativeConditionPlayerHasBadgeEquipped extends WiredConditionPlayerHasBadgeEquipped {

    /**
     * Creates a wired negative condition player has badge equipped instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionPlayerHasBadgeEquipped(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
