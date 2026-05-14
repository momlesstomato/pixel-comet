package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.games.GameTeam;

/**
 * Describes wired condition triggerer on furni blue behavior for the room subsystem.
 */
public class WiredConditionTriggererOnFurniBlue extends WiredConditionItem {
    /**
     * Creates a wired condition triggerer on furni blue instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionTriggererOnFurniBlue(RoomItemData itemData, Room room) {
        super(itemData, room);
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
        PlayerEntity playerEntity = (PlayerEntity)entity;

        if (entity == null) {
            return false;
        }

        boolean isOnFurni = false;

        block0: for (long itemId : this.getWiredData().getSelectedIds()) {
            for (RoomItemFloor itemOnEntity : entity.getRoom().getItems().getItemsOnSquare(entity.getPosition().getX(), entity.getPosition().getY())) {
                if (itemOnEntity.getId() != itemId || !playerEntity.getGameTeam().equals(GameTeam.BLUE)) continue;
                isOnFurni = true;
                continue block0;
            }
        }

        return this.isNegative != isOnFurni;
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
}

