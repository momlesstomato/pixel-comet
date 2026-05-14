package com.cometproject.server.game.rooms.objects.items.types.floor.totem;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes totem head floor item behavior for the room subsystem.
 */
public class TotemHeadFloorItem extends TotemPartFloorItem {
    /**
     * Creates a totem head floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public TotemHeadFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
