package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes wired condition stuff is behavior for the room subsystem.
 */
public class WiredConditionStuffIs extends WiredConditionItem {

    /**
     * Creates a wired condition stuff is instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionStuffIs(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 8;
    }

    /**
     * Executes evaluate for this room contract.
     *
     * @param entity Entity supplied by the caller.
     * @param data Data supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean evaluate(RoomEntity entity, Object data) {
        if (!(data instanceof RoomItemFloor)) {
            return false;
        }

        final RoomItemFloor floor = ((RoomItemFloor) data);
        boolean result = false;

        if (this.getWiredData().getSelectedIds().contains(floor.getId())) {
            result = true;
        }

        return !this.isNegative == result;
    }
}
