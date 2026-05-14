package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired condition furni has players behavior for the room subsystem.
 */
public class WiredConditionFurniHasPlayers extends WiredConditionItem {

    /**
     * Creates a wired condition furni has players instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionFurniHasPlayers(RoomItemData itemData, Room room) {
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
        int itemsWithUserCount = 0;
        int itemsWithoutUsersCount = 0;

        for (long itemId : this.getWiredData().getSelectedIds()) {
            RoomItemFloor floorItem = this.getRoom().getItems().getFloorItem(itemId);

            if (floorItem != null) {
                if (floorItem.getEntitiesOnItem().size() != 0) {
                    itemsWithUserCount++;
                } else {
                    itemsWithoutUsersCount++;
                }
            }
        }

        if (isNegative) {
            if (itemsWithoutUsersCount == this.getWiredData().getSelectedIds().size()) {
                return true;
            }

            return false;
        } else {
            return itemsWithUserCount == this.getWiredData().getSelectedIds().size();
        }
    }
        /*int itemsWithPlayers = 0;


        for (long itemId : this.getWiredData().getSelectedIds()) {
            RoomItemFloor floorItem = this.getRoom().getItems().getFloorItem(itemId);

            if (floorItem != null) {
                if (floorItem.getEntitiesOnItem().size() != 0) {
                    System.out.format("%s, %s, %s\n", this.getId(), floorItem.getId(), floorItem.getTile().getEntity().getUsername());
                    itemsWithPlayers++;
                }
            }
        }

        if (isNegative) {
            return itemsWithPlayers == 0;
        } else {
            return itemsWithPlayers == this.getWiredData().getSelectedIds().size();
        }
    }*/
}
