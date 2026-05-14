package com.cometproject.server.game.rooms.objects.items.types.floor.totem;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes totem body floor item behavior for the room subsystem.
 */
public class TotemBodyFloorItem extends TotemPartFloorItem {
    /**
     * Creates a totem body floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public TotemBodyFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
