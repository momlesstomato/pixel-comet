package com.cometproject.server.composers.group;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the group furniture widget message for the Pixel Protocol client.
 */
public class GroupFurnitureWidgetMessageComposer extends MessageComposer {
    private final int furnitureId;
    private final int groupId;
    private final String groupTitle;
    private final int homeRoom;
    private final boolean isMember;
    private final boolean hasForum;

    /**
     * Creates a group furniture widget message composer instance for the protocol composer subsystem.
     *
     * @param furnitureId Furniture id value supplied by the caller.
     * @param groupId Group id value supplied by the caller.
     * @param groupTitle Group title value supplied by the caller.
     * @param homeRoom Home room value supplied by the caller.
     * @param isMember Is member value supplied by the caller.
     * @param hasForum Has forum value supplied by the caller.
     */
    public GroupFurnitureWidgetMessageComposer(final int furnitureId, final int groupId, final String groupTitle, final int homeRoom, final boolean isMember, final boolean hasForum) {
        this.furnitureId = furnitureId;
        this.groupId = groupId;
        this.groupTitle = groupTitle;
        this.homeRoom = homeRoom;
        this.isMember = isMember;
        this.hasForum = hasForum;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.GroupFurniSettingsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(furnitureId);
        msg.writeInt(groupId);
        msg.writeString(groupTitle);
        msg.writeInt(homeRoom);
        msg.writeBoolean(isMember);
        msg.writeBoolean(hasForum);
    }
}
