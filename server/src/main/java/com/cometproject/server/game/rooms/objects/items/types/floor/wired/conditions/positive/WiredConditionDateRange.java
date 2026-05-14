package com.cometproject.server.game.rooms.objects.items.types.floor.wired.conditions.positive;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.rooms.objects.entities.RoomEntity;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredConditionItem;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes wired condition date range behavior for the room subsystem.
 */
public class WiredConditionDateRange extends WiredConditionItem {
    /**
     * Creates a wired condition date range instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public WiredConditionDateRange(RoomItemData itemData, Room room) {
        super(itemData, room);


    }

    /**
     * Returns the interface for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getInterface() {
        return 24;
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
        int DateStart = this.getWiredData().getParams().get(0);
        int DateEnd = this.getWiredData().getParams().get(1);
        long DateCurrent = Comet.getTime();

        if(DateStart > DateCurrent || DateEnd < DateCurrent) {
            return false;
        } else
        return true;
    }
}
