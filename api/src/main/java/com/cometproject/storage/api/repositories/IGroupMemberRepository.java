package com.cometproject.storage.api.repositories;

import com.cometproject.api.game.groups.types.components.membership.GroupAccessLevel;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;

import java.util.List;
import java.util.function.Consumer;

/**
 * Defines the i group member repository contract for the storage subsystem.
 */
public interface IGroupMemberRepository {
    /**
     * Returns the all by group id associated with this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param groupMembers Group members value supplied by the caller.
     */
    void getAllByGroupId(int groupId, Consumer<List<IGroupMember>> groupMembers);

    /**
     * Persists member data for this storage contract.
     *
     * @param groupMember Group member value supplied by the caller.
     */
    void saveMember(IGroupMember groupMember);

    /**
     * Executes the create operation for this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     * @param accessLevel Access level value supplied by the caller.
     * @param member Member value supplied by the caller.
     */
    void create(int groupId, int playerId, GroupAccessLevel accessLevel, Consumer<IGroupMember> member);

    /**
     * Executes the delete operation for this storage contract.
     *
     * @param groupMembershipId Group membership id value supplied by the caller.
     */
    void delete(int groupMembershipId);

    /**
     * Creates request data for this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     */
    void createRequest(int groupId, int playerId);

    /**
     * Deletes request data for this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     */
    void deleteRequest(int groupId, int playerId);

    /**
     * Executes the clear requests operation for this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     */
    void clearRequests(int groupId);

    /**
     * Returns the all requests associated with this storage contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @param requestsConsumer Requests consumer value supplied by the caller.
     */
    void getAllRequests(int groupId, Consumer<List<Integer>> requestsConsumer);

    /**
     * Updates role data for this storage contract.
     *
     * @param member Member value supplied by the caller.
     * @param role Role value supplied by the caller.
     */
    void updateRole(IGroupMember member, String role);
}
