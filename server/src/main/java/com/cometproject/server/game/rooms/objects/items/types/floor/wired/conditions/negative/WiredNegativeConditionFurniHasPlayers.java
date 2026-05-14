package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.negative;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.WiredConditionFurniHasPlayers;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired negative condition furni has players behavior for the room subsystem.
 */
public class WiredNegativeConditionFurniHasPlayers extends WiredConditionFurniHasPlayers {

    /**
     * Creates a wired negative condition furni has players instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredNegativeConditionFurniHasPlayers(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
