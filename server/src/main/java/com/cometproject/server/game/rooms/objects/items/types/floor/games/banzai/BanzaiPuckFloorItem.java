package com.cometproject.server.game.rooms.objects.items.types.floor.games.banzai;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.RollableFloorItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes banzai puck floor item behavior for the room subsystem.
 */
public class BanzaiPuckFloorItem extends RollableFloorItem {
    /**
     * Creates a banzai puck floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public BanzaiPuckFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
