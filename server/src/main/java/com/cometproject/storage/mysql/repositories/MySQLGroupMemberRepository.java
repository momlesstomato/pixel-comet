package com.cometproject.storage.mysql.repositories;

import com.cometproject.api.game.groups.types.components.membership.GroupAccessLevel;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;
import com.cometproject.storage.api.repositories.IGroupMemberRepository;
import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.cometproject.storage.mysql.data.results.IResultReader;
import com.cometproject.storage.mysql.models.factories.GroupMemberFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Persists and loads my SQL group member data for the MySQL storage subsystem.
 */
public class MySQLGroupMemberRepository extends MySQLRepository implements IGroupMemberRepository {

    private final GroupMemberFactory groupMemberFactory;

    /**
     * Creates a my SQL group member repository instance for the MySQL storage subsystem.
     *
     * @param groupMemberFactory Group member factory supplied by the caller.
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public MySQLGroupMemberRepository(GroupMemberFactory groupMemberFactory, MySQLConnectionProvider connectionProvider) {
        super(connectionProvider);

        this.groupMemberFactory = groupMemberFactory;
    }

    /**
     * Returns the all by group id for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param groupMembers Group members supplied by the caller.
     */
    @Override
    public void getAllByGroupId(int groupId, Consumer<List<IGroupMember>> groupMembers) {
        final List<IGroupMember> members = new ArrayList<>();

        select("SELECT * FROM group_memberships WHERE group_id = ?;", (data) -> {
            members.add(readMember(groupId, data));
        }, groupId);

        groupMembers.accept(members);
    }

    /**
     * Persists member for this MySQL storage contract.
     *
     * @param groupMember Group member supplied by the caller.
     */
    @Override
    public void saveMember(IGroupMember groupMember) {
        update("UPDATE group_memberships SET access_level = ? WHERE id = ?;",
                groupMember.getAccessLevel().toString().toLowerCase(), groupMember.getMembershipId());
    }

    /**
     * Updates role for this MySQL storage contract.
     *
     * @param groupMember Group member supplied by the caller.
     * @param role Role supplied by the caller.
     */
    @Override
    public void updateRole(IGroupMember groupMember, String role) {
        update("UPDATE group_memberships SET role = ? WHERE id = ?;", role, groupMember.getMembershipId());
    }

    /**
     * Executes create for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     * @param accessLevel Access level supplied by the caller.
     * @param member Member supplied by the caller.
     */
    @Override
    public void create(int groupId, int playerId, GroupAccessLevel accessLevel, Consumer<IGroupMember> member) {
        final int dateJoined = (int) (System.currentTimeMillis() / 1000);

        insert("INSERT into group_memberships (`group_id`, `player_id`, `access_level`, `date_joined`) VALUES(?, ?, ?, ?);",
                (data) -> member.accept(this.groupMemberFactory.create(data.readInteger(1), playerId, groupId, accessLevel, dateJoined, "")),
                groupId, playerId, accessLevel.toString(), dateJoined);
    }

    /**
     * Executes delete for this MySQL storage contract.
     *
     * @param groupMembershipId Group membership id supplied by the caller.
     */
    @Override
    public void delete(int groupMembershipId) {
        update("DELETE FROM group_memberships WHERE id = ?", groupMembershipId);
    }

    /**
     * Creates request for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    @Override
    public void createRequest(int groupId, int playerId) {
        insert("INSERT into group_requests(`group_id`, `player_id`) VALUES(?, ?);", (data) -> {
        }, groupId, playerId);
    }

    /**
     * Deletes request for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    @Override
    public void deleteRequest(int groupId, int playerId) {
        update("DELETE FROM group_requests WHERE group_id = ? AND player_id = ?;", groupId, playerId);
    }

    /**
     * Executes clear requests for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     */
    @Override
    public void clearRequests(int groupId) {
        update("DELETE FROM group_requests WHERE group_id = ?", groupId);
    }

    /**
     * Returns the all requests for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param requestsConsumer Requests consumer supplied by the caller.
     */
    @Override
    public void getAllRequests(int groupId, Consumer<List<Integer>> requestsConsumer) {
        final List<Integer> requests = new ArrayList<>();

        select("SELECT * FROM group_requests WHERE group_id = ?;", (data) -> {
            requests.add(data.readInteger("player_id"));
        }, groupId);

        requestsConsumer.accept(requests);
    }

    private IGroupMember readMember(int groupId, IResultReader data) throws Exception {
        final int membershipId = data.readInteger("id");
        final int playerId = data.readInteger("player_id");
        final int dateJoined = data.readInteger("date_joined");
        final String accessLevel = data.readString("access_level");
        final String role = data.readString("role");

        return this.groupMemberFactory.create(membershipId, playerId, groupId, GroupAccessLevel.valueOf(accessLevel.toUpperCase()), dateJoined, role);
    }
}
