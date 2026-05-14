package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive.custom;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired condition custom has dance behavior for the room subsystem.
 */
public class WiredConditionCustomHasDance extends WiredConditionItem {
    public static int PARAM_DANCE_ID = 0;

    /**
     * Creates a wired condition custom has dance instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionCustomHasDance(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 12;
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
        if (entity == null) return false;

        if (this.getWiredData().getParams().size() != 1) {
            return false;
        }

        final int danceId = this.getWiredData().getParams().get(PARAM_DANCE_ID);
        boolean snapDancing = false;

        PlayerEntity playerEntity = (PlayerEntity) entity;

        if (playerEntity.getDanceId() == danceId) {
            snapDancing = true;
        }


        return isNegative != snapDancing;
    }
}
