package com.cometproject.server.composers.group.forums;

import com.cometproject.api.game.groups.types.components.forum.IForumThreadReply;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the group forum update reply message for the Pixel Protocol client.
 */
public class GroupForumUpdateReplyMessageComposer extends MessageComposer {

    private final int groupId;
    private final int threadId;
    private final IForumThreadReply reply;

    /**
     * Creates a group forum update reply message composer instance for the protocol composer subsystem.
     *
     * @param reply Reply value supplied by the caller.
     * @param threadId Thread id value supplied by the caller.
     * @param groupId Group id value supplied by the caller.
     */
    public GroupForumUpdateReplyMessageComposer(IForumThreadReply reply, int threadId, int groupId) {
        this.reply = reply;
        this.threadId = threadId;
        this.groupId = groupId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.ThreadUpdateReplyMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.groupId);
        msg.writeInt(this.threadId);

        this.reply.compose(msg);
    }
}
