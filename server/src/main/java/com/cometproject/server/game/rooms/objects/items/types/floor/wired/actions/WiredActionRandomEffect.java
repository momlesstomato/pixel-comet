package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes wired action random effect behavior for the room subsystem.
 */
public class WiredActionRandomEffect extends RoomItemFloor {
    /**
     * Creates a wired action random effect instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredActionRandomEffect(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
