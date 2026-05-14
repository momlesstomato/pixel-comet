package com.cometproject.server.game.rooms.objects.items.types.floor.snowboarding;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.AdjustableHeightFloorItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes snowboard slope floor item behavior for the room subsystem.
 */
public class SnowboardSlopeFloorItem extends AdjustableHeightFloorItem {
    /**
     * Creates a snowboard slope floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public SnowboardSlopeFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }
}
