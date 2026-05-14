package com.cometproject.server.composers.group.forums;

import com.cometproject.api.game.groups.types.components.forum.IForumThread;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the group forum threads message for the Pixel Protocol client.
 */
public class GroupForumThreadsMessageComposer extends MessageComposer {

    private final int groupId;
    private final List<IForumThread> threads;
    private final int start;

    /**
     * Creates a group forum threads message composer instance for the protocol composer subsystem.
     *
     * @param groupId Group id value supplied by the caller.
     * @param threads Threads value supplied by the caller.
     * @param start Start value supplied by the caller.
     */
    public GroupForumThreadsMessageComposer(int groupId, List<IForumThread> threads, int start) {
        this.groupId = groupId;
        this.threads = threads;

        this.start = start;
}

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.ThreadsListDataMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.groupId);
        msg.writeInt(this.start); // start index.

        msg.writeInt(this.threads.size());

        for(IForumThread forumThread : this.threads) {
            forumThread.compose(msg);
        }
    }

    /**
     * Releases references held by this protocol message.
     */
    @Override
    public void dispose() {
        this.threads.clear();
    }
}