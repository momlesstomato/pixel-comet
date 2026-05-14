package com.cometproject.storage.api.repositories;

import com.cometproject.api.game.groups.types.components.forum.IForumSettings;
import com.cometproject.api.game.groups.types.components.forum.IForumThread;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Defines the i group forum repository contract for the storage subsystem.
 */
public interface IGroupForumRepository {
    /**
     * Returns the settings by group id associated with this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param forumSettingsConsumer Forum settings consumer value supplied by the caller.
     */
    void getSettingsByGroupId(final int groupId, Consumer<IForumSettings> forumSettingsConsumer);

    /**
     * Persists settings data for this storage contract.
     *
     * @param forumSettings Forum settings value supplied by the caller.
     */
    void saveSettings(final IForumSettings forumSettings);

    /**
     * Returns the all messages associated with this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param threadConsumer Thread consumer value supplied by the caller.
     */
    void getAllMessages(Integer groupId, BiConsumer<Map<Integer, IForumThread>, List<Integer>> threadConsumer);

    /**
     * Creates thread data for this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param title Title value supplied by the caller.
     * @param message Message value supplied by the caller.
     * @param authorId Author id value supplied by the caller.
     * @param threadId Thread id value supplied by the caller.
     */
    void createThread(int groupId, String title, String message, int authorId, Consumer<Integer> threadId);

    /**
     * Creates reply data for this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param threadId Thread id value supplied by the caller.
     * @param message Message value supplied by the caller.
     * @param authorId Author id value supplied by the caller.
     * @param messageId Message id value supplied by the caller.
     */
    void createReply(int groupId, int threadId, String message, int authorId, Consumer<Integer> messageId);

    /**
     * Persists message state data for this storage contract.
     *
     * @param messageId Message id value supplied by the caller.
     * @param state State value supplied by the caller.
     * @param modId Mod id value supplied by the caller.
     * @param modUsername Mod username value supplied by the caller.
     */
    void saveMessageState(int messageId, int state, int modId, String modUsername);

    /**
     * Persists message lock data for this storage contract.
     *
     * @param messageId Message id value supplied by the caller.
     * @param locked Locked value supplied by the caller.
     * @param modId Mod id value supplied by the caller.
     * @param modUsername Mod username value supplied by the caller.
     */
    void saveMessageLock(int messageId, boolean locked, int modId, String modUsername);

    /**
     * Persists message pin state data for this storage contract.
     *
     * @param messageId Message id value supplied by the caller.
     * @param pinned Pinned value supplied by the caller.
     */
    void saveMessagePinState(int messageId, boolean pinned);
}
