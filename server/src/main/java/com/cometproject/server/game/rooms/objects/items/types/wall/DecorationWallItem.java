package com.cometproject.server.game.rooms.objects.items.types.wall;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemWall;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes decoration wall item behavior for the room subsystem.
 */
public final class DecorationWallItem extends RoomItemWall {
    /**
     * Creates a decoration wall item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public DecorationWallItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);
    }
}
