package com.cometproject.server.composers.group.forums;

import com.cometproject.api.game.groups.types.components.forum.IForumThreadReply;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the group forum post reply message for the Pixel Protocol client.
 */
public class GroupForumPostReplyMessageComposer extends MessageComposer {
    private int groupId;
    private int threadId;
    private IForumThreadReply reply;

    /**
     * Creates a group forum post reply message composer instance for the protocol composer subsystem.
     *
     * @param groupId Group id value supplied by the caller.
     * @param threadId Thread id value supplied by the caller.
     * @param reply Reply value supplied by the caller.
     */
    public GroupForumPostReplyMessageComposer(int groupId, int threadId, IForumThreadReply reply) {
        this.groupId = groupId;
        this.threadId = threadId;
        this.reply = reply;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.ThreadReplyMessageComposer;
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
