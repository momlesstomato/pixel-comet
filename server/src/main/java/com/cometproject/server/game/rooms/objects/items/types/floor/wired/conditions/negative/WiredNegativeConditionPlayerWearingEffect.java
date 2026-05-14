package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionPlayerWearingEffect;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition player wearing effect behavior for the room subsystem.
 */
public class WiredNegativeConditionPlayerWearingEffect extends WiredConditionPlayerWearingEffect {

    /**
     * Creates a wired negative condition player wearing effect instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionPlayerWearingEffect(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
