package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired condition player wearing effect behavior for the room subsystem.
 */
public class WiredConditionPlayerWearingEffect extends WiredConditionItem {
    public static int PARAM_EFFECT_ID = 0;

    /**
     * Creates a wired condition player wearing effect instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionPlayerWearingEffect(RoomItemData itemData, Room room) {
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

        final int effectId = this.getWiredData().getParams().get(PARAM_EFFECT_ID);
        boolean isWearingEffect = false;


        if (entity.getCurrentEffect() != null) {
            if (entity.getCurrentEffect().getEffectId() == effectId) {
                isWearingEffect = true;
            }
        }

        return isNegative != isWearingEffect;
    }
}
