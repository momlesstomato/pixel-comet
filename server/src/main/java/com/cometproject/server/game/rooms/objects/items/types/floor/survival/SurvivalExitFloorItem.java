package com.cometproject.server.game.rooms.objects.items.types.floor.survival;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes survival exit floor item behavior for the room subsystem.
 */
public class SurvivalExitFloorItem extends RoomItemFloor {
    /**
     * Creates a survival exit floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public SurvivalExitFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);

        this.getItemData().setData("0");
    }
}
