package com.cometproject.server.composers.group.forums;

import com.cometproject.api.game.groups.types.components.forum.IForumThread;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the group forum update thread message for the Pixel Protocol client.
 */
public class GroupForumUpdateThreadMessageComposer extends MessageComposer {
    private int groupId;
    private IForumThread forumThread;

    /**
     * Creates a group forum update thread message composer instance for the protocol composer subsystem.
     *
     * @param groupId Group id value supplied by the caller.
     * @param forumThread Forum thread value supplied by the caller.
     */
    public GroupForumUpdateThreadMessageComposer(int groupId, IForumThread forumThread) {
        this.groupId = groupId;
        this.forumThread = forumThread;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.ThreadUpdatedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.groupId);

        this.forumThread.compose(msg);
    }
}
