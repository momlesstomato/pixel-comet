package com.cometproject.server.composers.group.forums;

import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the group forum list message for the Pixel Protocol client.
 */
public class GroupForumListMessageComposer extends MessageComposer {

    private final int code;
    private final List<IGroup> groups;
    private final int playerId;

    /**
     * Creates a group forum list message composer instance for the protocol composer subsystem.
     *
     * @param code Code value supplied by the caller.
     * @param groups Groups value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     */
    public GroupForumListMessageComposer(final int code, final List<IGroup> groups, final int playerId) {
        this.code = code;
        this.groups = groups;
        this.playerId = playerId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.ForumsListDataMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.code);
        msg.writeInt(this.groups.size());
        msg.writeInt(0);
        msg.writeInt(this.groups.size()); //???

        for(IGroup group : this.groups) {
            group.getForum().composeData(msg, group.getData());
        }
    }
}
