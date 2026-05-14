package com.cometproject.server.composers.group;

import com.cometproject.api.game.groups.IGroupItemService;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the group data message for the Pixel Protocol client.
 */
public class GroupDataMessageComposer extends MessageComposer {
    private final List<IGroupData> groups;
    private final IGroupItemService groupItemService;
    private final int playerId;

    /**
     * Creates a group data message composer instance for the protocol composer subsystem.
     *
     * @param groups Groups value supplied by the caller.
     * @param groupItemService Group item service value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     */
    public GroupDataMessageComposer(final List<IGroupData> groups, IGroupItemService groupItemService, final int playerId) {
        this.groups = groups;
        this.groupItemService = groupItemService;
        this.playerId = playerId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.GroupFurniConfigMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.groups.size());

        for (IGroupData group : groups) {
            msg.writeInt(group.getId());
            msg.writeString(group.getTitle());
            msg.writeString(group.getBadge());

            String colourA = this.groupItemService.getSymbolColours().get(group.getColourA()) != null ?
                    this.groupItemService.getSymbolColours().get(group.getColourA()).getFirstValue() : "ffffff";
            String colourB = this.groupItemService.getBackgroundColours().get(group.getColourB()) != null ?
                    this.groupItemService.getBackgroundColours().get(group.getColourB()).getFirstValue() : "ffffff";

            msg.writeString(colourA);
            msg.writeString(colourB);

            msg.writeBoolean(group.getOwnerId() == this.playerId);
            msg.writeInt(group.getOwnerId());
            msg.writeBoolean(group.hasForum());
        }
    }
}