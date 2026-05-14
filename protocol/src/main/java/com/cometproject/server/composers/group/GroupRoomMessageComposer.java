package com.cometproject.server.composers.group;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the group room message for the Pixel Protocol client.
 */
public class GroupRoomMessageComposer extends MessageComposer {
    private final int roomId;
    private final int groupId;

    /**
     * Creates a group room message composer instance for the protocol composer subsystem.
     *
     * @param roomId Room id value supplied by the caller.
     * @param groupId Group id value supplied by the caller.
     */
    public GroupRoomMessageComposer(final int roomId, final int groupId) {
        this.roomId = roomId;
        this.groupId = groupId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.NewGroupInfoMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
        msg.writeInt(groupId);
    }
}
