package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.items.types.DefaultFloorItem;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes badge display floor item behavior for the room subsystem.
 */
public class BadgeDisplayFloorItem extends DefaultFloorItem {

    /**
     * Creates a badge display floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public BadgeDisplayFloorItem(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    /**
     * Executes compose item data for this room contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void composeItemData(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(2);
        msg.writeInt(4);

        String badge;
        String name = "";
        String date = "";

        if (this.getItemData().getData().contains("~")) {
            String[] data = this.getItemData().getData().split("~");

            badge = data[0];
            name = data[1];
            date = data[2];
        } else {
            badge = this.getItemData().getData();
        }

        msg.writeString("0");
        msg.writeString(badge);
        msg.writeString(name); // creator
        msg.writeString(date); // date
    }
}
