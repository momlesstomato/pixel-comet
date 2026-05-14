package com.cometproject.server.game.rooms.objects.items.types.floor.football;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.RollableFloorItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes football floor item behavior for the room subsystem.
 */
public class FootballFloorItem extends RollableFloorItem {
    /**
     * Creates a football floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public FootballFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
