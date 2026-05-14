package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired condition has furni on behavior for the room subsystem.
 */
public class WiredConditionHasFurniOn extends WiredConditionItem {
    private final static int PARAM_MODE = 0;

    /**
     * Creates a wired condition has furni on instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionHasFurniOn(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 7;
    }

    /**
     * Returns the mode for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMode() {
        return this.getWiredData().getParams().get(PARAM_MODE);
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
        int mode;

        try {
            mode = this.getWiredData().getParams().get(PARAM_MODE);
        } catch (Exception e) {
            mode = 0;
        }

        int selectedItemsWithFurni = 0;

        for (long itemId : this.getWiredData().getSelectedIds()) {
            RoomItemFloor floorItem = this.getRoom().getItems().getFloorItem(itemId);

            if (floorItem != null) {
                for (RoomItemFloor itemOnSq : floorItem.getItemsOnStack()) {
                    if (itemOnSq.getPosition().getZ() != 0.0 && itemOnSq.getPosition().getZ() >= floorItem.getPosition().getZ() && itemOnSq.getId() != floorItem.getId())
                        selectedItemsWithFurni++;
                }
            }
        }

        boolean result = false;

        if (mode == 0) {
            if (selectedItemsWithFurni >= 1) result = true;
        } else {
            if (selectedItemsWithFurni == this.getWiredData().getSelectedIds().size()) result = true;
        }

        return this.isNegative != result;
    }
}