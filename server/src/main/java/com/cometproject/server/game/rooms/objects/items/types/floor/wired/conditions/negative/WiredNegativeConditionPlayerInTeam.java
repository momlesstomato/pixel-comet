package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionPlayerInTeam;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes wired negative condition player in team behavior for the room subsystem.
 */
public class WiredNegativeConditionPlayerInTeam extends WiredConditionPlayerInTeam {
    /**
     * Creates a wired negative condition player in team instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionPlayerInTeam(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
