package com.cometproject.server.game.rooms.objects.items.types.floor;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.items.types.DefaultFloorItem;
import com.cometproject.server.game.rooms.types.Room;

/**
 * Describes video player floor item behavior for the room subsystem.
 */
public class VideoPlayerFloorItem extends DefaultFloorItem {
    /**
     * Creates a video player floor item instance for the room subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param room Room participating in the operation.
     */
    public VideoPlayerFloorItem(RoomItemData itemData, Room room) {
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
        msg.writeInt(1);
        msg.writeInt(1);
        msg.writeString("THUMBNAIL_URL");
        msg.writeString("/deliver/" + this.getAttribute("video"));
    }
}
