package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes wired custom furni down behavior for the room subsystem.
 */
public class WiredCustomFurniDown extends WiredCustomFurniUp {

    /**
     * Creates a wired custom furni down instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredCustomFurniDown(RoomItemData itemData, Room room) {
        super(itemData, room);
        this.isUp = false;
    }
}
