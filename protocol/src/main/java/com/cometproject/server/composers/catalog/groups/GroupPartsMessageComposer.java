package com.cometproject.server.composers.catalog.groups;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the group parts message for the Pixel Protocol client.
 */
public class GroupPartsMessageComposer extends MessageComposer {

    private final List<IRoomData> availableRooms;

    /**
     * Creates a group parts message composer instance for the group subsystem.
     *
     * @param rooms Rooms value supplied by the caller.
     */
    public GroupPartsMessageComposer(final List<IRoomData> rooms) {
        this.availableRooms = rooms;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.GroupCreationWindowMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(CometSettings.groupCost);
        msg.writeInt(availableRooms.size());

        for (IRoomData roomData : availableRooms) {
            msg.writeInt(roomData.getId());
            msg.writeString(roomData.getName());
            msg.writeBoolean(false);
        }


        // TODO: Stop hardcoding this
        msg.writeInt(5);
        msg.writeInt(10);
        msg.writeInt(3);
        msg.writeInt(4);
        msg.writeInt(0x19);
        msg.writeInt(0x11);
        msg.writeInt(5);
        msg.writeInt(0x19);
        msg.writeInt(0x11);
        msg.writeInt(3);
        msg.writeInt(0x1d);
        msg.writeInt(11);
        msg.writeInt(4);
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(0);
    }

    /**
     * Releases references held by this protocol message.
     */
    @Override
    public void dispose() {
        this.availableRooms.clear();
    }
}
