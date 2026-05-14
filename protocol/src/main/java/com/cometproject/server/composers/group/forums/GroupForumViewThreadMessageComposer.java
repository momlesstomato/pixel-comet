package com.cometproject.server.composers.group.forums;

import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.groups.types.components.forum.IForumThreadReply;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the group forum view thread message for the Pixel Protocol client.
 */
public class GroupForumViewThreadMessageComposer extends MessageComposer {

    private IGroupData groupData;
    private final int threadId;
    private List<IForumThreadReply> replies;
    private int start;

    /**
     * Creates a group forum view thread message composer instance for the protocol composer subsystem.
     *
     * @param groupData Group data value supplied by the caller.
     * @param threadId Thread id value supplied by the caller.
     * @param threadReplies Thread replies value supplied by the caller.
     * @param start Start value supplied by the caller.
     */
    public GroupForumViewThreadMessageComposer(IGroupData groupData, int threadId, List<IForumThreadReply> threadReplies, int start) {
        this.groupData = groupData;
        this.threadId = threadId;
        this.replies = threadReplies;
        this.start = start;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.ThreadDataMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.groupData.getId());
        msg.writeInt(this.threadId);
        msg.writeInt(this.start);
        msg.writeInt(this.replies.size());

        for(IForumThreadReply reply : this.replies) {
            reply.compose(msg);
        }
    }
}
