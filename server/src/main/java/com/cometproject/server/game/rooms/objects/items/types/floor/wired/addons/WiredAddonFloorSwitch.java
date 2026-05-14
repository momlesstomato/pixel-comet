package com.cometproject.server.game.rooms.objects.items.types.floor.wired.addons;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired addon floor switch behavior for the room subsystem.
 */
public class WiredAddonFloorSwitch extends RoomItemFloor {
    /**
     * Creates a wired addon floor switch instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredAddonFloorSwitch(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Handles the interact callback for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param requestData Request data supplied by the caller.
     * @param isWiredTrigger Is wired trigger supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean onInteract(RoomEntity entity, int requestData, boolean isWiredTrigger) {
        if (entity != null) {
            if (!this.getPosition().touching(entity.getPosition())) {
                entity.moveTo(this.getPosition().squareBehind(this.getRotation()).getX(), this.getPosition().squareBehind(this.getRotation()).getY());
                return false;
            }
        }

        this.toggleInteract(true);

        this.sendUpdate();
        this.saveData();

        return true;
    }
}
