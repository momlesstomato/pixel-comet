package com.cometproject.storage.mysql.models.factories;

import com.cometproject.api.game.groups.types.components.forum.IForumThread;
import com.cometproject.api.game.groups.types.components.forum.IForumThreadReply;
import com.cometproject.storage.mysql.models.GroupForumThreadData;
import com.cometproject.storage.mysql.models.GroupForumThreadMessageData;

/**
 * Creates group forum message instances for the MySQL storage subsystem.
 */
public class GroupForumMessageFactory {

    /**
     * Creates thread for this MySQL storage contract.
     *
     * @param id Id supplied by the caller.
     * @param title Title supplied by the caller.
     * @param message Message supplied by the caller.
     * @param authorId Author id supplied by the caller.
     * @param authorTimestamp Author timestamp supplied by the caller.
     * @param state State supplied by the caller.
     * @param locked Locked supplied by the caller.
     * @param pinned Pinned supplied by the caller.
     * @param moderatorId Moderator id supplied by the caller.
     * @param moderatorUsername Moderator username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public IForumThread createThread(int id, String title, String message, int authorId, int authorTimestamp, int state, boolean locked, boolean pinned, int moderatorId, String moderatorUsername) {
        return new GroupForumThreadData(id, title, message, authorId, authorTimestamp, state, locked, pinned, moderatorId, moderatorUsername);
    }

    /**
     * Creates thread reply for this MySQL storage contract.
     *
     * @param id Id supplied by the caller.
     * @param index Index supplied by the caller.
     * @param message Message supplied by the caller.
     * @param threadId Thread id supplied by the caller.
     * @param authorId Author id supplied by the caller.
     * @param authorTimestamp Author timestamp supplied by the caller.
     * @param state State supplied by the caller.
     * @param adminId Admin id supplied by the caller.
     * @param adminUsername Admin username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public IForumThreadReply createThreadReply(int id, int index, String message, int threadId, int authorId, int authorTimestamp, int state, int adminId, String adminUsername) {
        return new GroupForumThreadMessageData(id, index, message, threadId, authorId, authorTimestamp, state, adminId, adminUsername);
    }
}