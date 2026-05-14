package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes teleport pad floor item behavior for the room subsystem.
 */
public class TeleportPadFloorItem extends TeleporterFloorItem {
    /**
     * Creates a teleport pad floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public TeleportPadFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
