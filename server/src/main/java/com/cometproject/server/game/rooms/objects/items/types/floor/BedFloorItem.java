package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.types.DefaultFloorItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes bed floor item behavior for the room subsystem.
 */
public class BedFloorItem extends DefaultFloorItem {
    /**
     * Creates a bed floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public BedFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the entity step on callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOn(RoomEntity entity) {
        entity.setBodyRotation(this.getRotation());
        entity.setHeadRotation(this.getRotation());
        entity.addStatus(RoomEntityStatus.LAY, this.getDefinition().getHeight() + "");

        entity.markNeedsUpdate();
    }

    /**
     * Handles the entity step off callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     */
    @Override
    public void onEntityStepOff(RoomEntity entity) {
        entity.removeStatus(RoomEntityStatus.LAY);
        entity.markNeedsUpdate();
    }
}
