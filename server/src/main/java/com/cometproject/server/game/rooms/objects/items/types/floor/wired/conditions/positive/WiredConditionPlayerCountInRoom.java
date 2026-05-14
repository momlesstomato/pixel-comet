package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;


/**
 * Describes wired condition player count in room behavior for the room subsystem.
 */
public class WiredConditionPlayerCountInRoom extends WiredConditionItem {
    private static final int PARAM_AT_LEAST = 0;
    private static final int PARAM_NO_MORE_THAN = 1;

    /**
     * Creates a wired condition player count in room instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionPlayerCountInRoom(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 5;
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
        if (this.getWiredData().getParams().size() != 2) return true;

        final int playerCount = this.getRoom().getEntities().getPlayerEntities().size();

        if (playerCount >= this.getWiredData().getParams().get(PARAM_AT_LEAST) && playerCount <= this.getWiredData().getParams().get(PARAM_NO_MORE_THAN)) {
            // true if is not negative, false if is negative
            return !this.isNegative;
        }

        // false if is not negative, true if is negative.
        return this.isNegative;
    }
}
