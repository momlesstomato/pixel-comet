package com.cometproject.storage.mysql.repositories;

import com.cometproject.api.game.groups.types.components.forum.*;
import com.cometproject.storage.api.repositories.IGroupForumRepository;
import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.cometproject.storage.mysql.data.results.IResultReader;
import com.cometproject.storage.mysql.models.factories.GroupForumMessageFactory;
import com.cometproject.storage.mysql.models.factories.GroupForumSettingsFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Persists and loads my SQL group forum data for the MySQL storage subsystem.
 */
public class MySQLGroupForumRepository extends MySQLRepository implements IGroupForumRepository {

    private final GroupForumSettingsFactory forumSettingsFactory;
    private final GroupForumMessageFactory forumMessageFactory;

    /**
     * Creates a my SQL group forum repository instance for the MySQL storage subsystem.
     *
     * @param forumSettingsFactory Forum settings factory supplied by the caller.
     * @param forumMessageFactory Forum message factory supplied by the caller.
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public MySQLGroupForumRepository(GroupForumSettingsFactory forumSettingsFactory, GroupForumMessageFactory forumMessageFactory, MySQLConnectionProvider connectionProvider) {
        super(connectionProvider);

        this.forumSettingsFactory = forumSettingsFactory;
        this.forumMessageFactory = forumMessageFactory;
    }

    /**
     * Returns the settings by group id for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param forumSettingsConsumer Forum settings consumer supplied by the caller.
     */
    @Override
    public void getSettingsByGroupId(int groupId, Consumer<IForumSettings> forumSettingsConsumer) {
        select("SELECT * FROM group_forum_settings WHERE group_id = ?", (data) -> {
            forumSettingsConsumer.accept(this.buildSettings(groupId, data));
        }, groupId);
    }

    /**
     * Persists settings for this MySQL storage contract.
     *
     * @param forumSettings Forum settings supplied by the caller.
     */
    @Override
    public void saveSettings(IForumSettings forumSettings) {
        update("UPDATE group_forum_settings SET read_permission = ?, post_permission = ?, thread_permission = ?, " +
                        "moderate_permission = ? WHERE group_id = ?",
                forumSettings.getReadPermission().toString(),
                forumSettings.getPostPermission().toString(),
                forumSettings.getStartThreadsPermission().toString(),
                forumSettings.getModeratePermission().toString(),
                forumSettings.getGroupId());
    }

    /**
     * Returns the all messages for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param threadConsumer Thread consumer supplied by the caller.
     */
    @Override
    public void getAllMessages(Integer groupId, BiConsumer<Map<Integer, IForumThread>, List<Integer>> threadConsumer) {
        final Map<Integer, IForumThread> forumThreads = Maps.newConcurrentMap();
        final List<Integer> pinnedThreads = Lists.newCopyOnWriteArrayList();

        select("SELECT * FROM group_forum_messages WHERE group_id = ? ORDER BY FIELD(type, 'THREAD', 'REPLY'), pinned DESC, author_timestamp DESC;", (data) -> {
            final ForumMessageType messageType = ForumMessageType.valueOf(data.readString("type"));

            switch (messageType) {
                case THREAD:
                    final IForumThread forumThread = this.buildThread(data);

                    if (forumThread.isPinned()) {
                        pinnedThreads.add(forumThread.getId());
                    }

                    forumThreads.put(forumThread.getId(), forumThread);
                    break;

                case REPLY:
                    final int threadId = data.readInteger("thread_id");

                    if (!forumThreads.containsKey(threadId)) {
                        break;
                    }

                    final IForumThreadReply threadReply = this.buildThreadReply(data);

                    forumThreads.get(threadId).addReply(threadReply);
                    threadReply.setIndex(forumThreads.get(threadReply.getThreadId()).getReplies().indexOf(threadReply));
                    break;
            }
        }, groupId);

        threadConsumer.accept(forumThreads, pinnedThreads);
    }

    /**
     * Creates thread for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param title Title supplied by the caller.
     * @param message Message supplied by the caller.
     * @param authorId Author id supplied by the caller.
     * @param threadId Thread id supplied by the caller.
     */
    @Override
    public void createThread(int groupId, String title, String message, int authorId, Consumer<Integer> threadId) {
        insert("INSERT into group_forum_messages (type, group_id, title, message, author_id, author_timestamp) " +
                "VALUES('THREAD', ?, ?, ?, ?, UNIX_TIMESTAMP());", (data) -> {
            threadId.accept(data.readInteger(1));
        }, groupId, title, message, authorId);
    }

    /**
     * Creates reply for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param threadId Thread id supplied by the caller.
     * @param message Message supplied by the caller.
     * @param authorId Author id supplied by the caller.
     * @param messageId Message id supplied by the caller.
     */
    @Override
    public void createReply(int groupId, int threadId, String message, int authorId, Consumer<Integer> messageId) {
        insert("INSERT into group_forum_messages (type, group_id, thread_id, message, " +
                "author_id, author_timestamp) VALUES('REPLY', ?, ?, ?, ?, UNIX_TIMESTAMP());", (data) -> {
            messageId.accept(data.readInteger(1));
        }, groupId, threadId, message, authorId);
    }

    /**
     * Persists message state for this MySQL storage contract.
     *
     * @param messageId Message id supplied by the caller.
     * @param state State supplied by the caller.
     * @param modId Mod id supplied by the caller.
     * @param modUsername Mod username supplied by the caller.
     */
    @Override
    public void saveMessageState(int messageId, int state, int modId, String modUsername) {
        update("UPDATE group_forum_messages SET state = ?, moderator_id = ?, moderator_username = ? WHERE id = ?",
                state, modId, modUsername, messageId);

    }

    /**
     * Persists message lock for this MySQL storage contract.
     *
     * @param messageId Message id supplied by the caller.
     * @param locked Locked supplied by the caller.
     * @param modId Mod id supplied by the caller.
     * @param modUsername Mod username supplied by the caller.
     */
    @Override
    public void saveMessageLock(int messageId, boolean locked, int modId, String modUsername) {
        update("UPDATE group_forum_messages SET locked = ?, moderator_id = ?, moderator_username = ? WHERE id = ?",
                locked ? "1" : "0", modId, modUsername, messageId);
    }

    /**
     * Persists message pin state for this MySQL storage contract.
     *
     * @param messageId Message id supplied by the caller.
     * @param pinned Pinned supplied by the caller.
     */
    @Override
    public void saveMessagePinState(int messageId, boolean pinned) {
        update("UPDATE group_forum_messages SET pinned = ? WHERE id = ?", pinned ? "1" : "0", messageId);
    }

    private IForumSettings buildSettings(final int groupId, IResultReader resultReader) throws Exception {
        return this.forumSettingsFactory.createSettings(groupId,
                ForumPermission.valueOf(resultReader.readString("read_permission")),
                ForumPermission.valueOf(resultReader.readString("post_permission")),
                ForumPermission.valueOf(resultReader.readString("thread_permission")),
                ForumPermission.valueOf(resultReader.readString("moderate_permission")));
    }

    private IForumThread buildThread(IResultReader resultReader) throws Exception {
        final int id = resultReader.readInteger("id");
        final String title = resultReader.readString("title");
        final String message = resultReader.readString("message");
        final int authorId = resultReader.readInteger("author_id");
        final int authorTimestamp = resultReader.readInteger("author_timestamp");
        final int state = resultReader.readInteger("state");
        final boolean locked = resultReader.readBoolean("locked");
        final boolean pinned = resultReader.readBoolean("pinned");
        final int moderatorId = resultReader.readInteger("moderator_id");
        final String moderatorUsername = resultReader.readString("moderator_username");

        return this.forumMessageFactory.createThread(id, title, message, authorId, authorTimestamp, state, locked,
                pinned, moderatorId, moderatorUsername);
    }

    /**
     * Executes build thread reply for this MySQL storage contract.
     *
     * @param resultReader Result reader supplied by the caller.
     * @return Result produced by the operation.
     * @throws Exception When the operation cannot complete.
     */
    public IForumThreadReply buildThreadReply(IResultReader resultReader) throws Exception {
        final int id = resultReader.readInteger("id");
        final int threadId = resultReader.readInteger("thread_id");
        final String message = resultReader.readString("message");
        final int authorId = resultReader.readInteger("author_id");
        final int authorTimestamp = resultReader.readInteger("author_timestamp");
        final int state = resultReader.readInteger("state");
        final int moderatorId = resultReader.readInteger("moderator_id");
        final String moderatorUsername = resultReader.readString("moderator_username");

        return this.forumMessageFactory.createThreadReply(id, -1, message, threadId, authorId, authorTimestamp, state, moderatorId, moderatorUsername);
    }
}
