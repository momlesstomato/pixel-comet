package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.server.game.rooms.types.Room;
import org.apache.commons.lang3.StringUtils;


/**
 * Describes adjustable height seat floor item behavior for the room subsystem.
 */
public class AdjustableHeightSeatFloorItem extends SeatFloorItem {
    /**
     * Creates a adjustable height seat floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public AdjustableHeightSeatFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);

        if (this.getItemData().getData().isEmpty()) {
            this.getItemData().setData("0");
        }
    }

    /**
     * Returns the sit height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public double getSitHeight() {
        double height;

        if (!StringUtils.isNumeric(this.getItemData().getData())) {
            height = 1.0;
        } else {
            height = Double.parseDouble(this.getItemData().getData());

            if (height <= 1) {
                height += 1.0;
            } else {
                height += 0.5;
            }
        }

        return height;
    }
}
