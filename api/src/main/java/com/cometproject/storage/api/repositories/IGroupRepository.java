package com.cometproject.storage.api.repositories;

import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.groups.types.components.IForumComponent;

import java.util.List;
import java.util.function.Consumer;

/**
 * Defines the i group repository contract for the storage subsystem.
 */
public interface IGroupRepository {
    /**
     * Returns the data by id associated with this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param consumer Consumer value supplied by the caller.
     */
    void getDataById(final int groupId, Consumer<IGroupData> consumer);

    /**
     * Persists group data data for this storage contract.
     *
     * @param groupData Group data value supplied by the caller.
     */
    void saveGroupData(IGroupData groupData);

    /**
     * Executes the create operation for this storage contract.
     *
     * @param groupData Group data value supplied by the caller.
     */
    void create(IGroupData groupData);

    /**
     * Returns the group id by room id associated with this storage contract.
     *
     * @param roomId Room id value supplied by the caller.
     * @param consumer Consumer value supplied by the caller.
     */
    void getGroupIdByRoomId(int roomId, Consumer<Integer> consumer);

    /**
     * Deletes group data for this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     */
    void deleteGroup(int groupId);

    /**
     * Returns the group ids by player id associated with this storage contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param consumer Consumer value supplied by the caller.
     */
    void getGroupIdsByPlayerId(int playerId, Consumer<List<Integer>> consumer);

    /**
     * Creates forum settings data for this storage contract.
     *
     * @param forumComponent Forum component value supplied by the caller.
     */
    void createForumSettings(IForumComponent forumComponent);
}
