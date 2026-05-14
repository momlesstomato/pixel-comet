package com.cometproject.server.game.rooms.objects.items.types.floor.games.freeze;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes freeze exit floor item behavior for the room subsystem.
 */
public class FreezeExitFloorItem extends RoomItemFloor {
    /**
     * Creates a freeze exit floor item instance for the room subsystem.
     *
     * @param roomItemData Room item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public FreezeExitFloorItem(RoomItemData roomItemData, Room room) {
        super(roomItemData, room);

        this.getItemData().setData("0");
    }
}
