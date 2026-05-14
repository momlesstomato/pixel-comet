package com.cometproject.storage.mysql.models.factories;

import com.cometproject.api.game.groups.types.components.membership.GroupAccessLevel;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;
import com.cometproject.storage.mysql.models.GroupMemberData;

/**
 * Creates group member instances for the MySQL storage subsystem.
 */
public class GroupMemberFactory {
    /**
     * Executes create for this MySQL storage contract.
     *
     * @param membershipId Membership id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     * @param groupId Group id supplied by the caller.
     * @param accessLevel Access level supplied by the caller.
     * @param dateJoined Date joined supplied by the caller.
     * @param role Role supplied by the caller.
     * @return Value exposed by the contract.
     */
    public IGroupMember create(final int membershipId, final int playerId, final int groupId, final GroupAccessLevel accessLevel, final int dateJoined, final String role) {
        return new GroupMemberData(membershipId, playerId, groupId, dateJoined, accessLevel, role);
    }

    /**
     * Executes create for this MySQL storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param groupId Group id supplied by the caller.
     * @param accessLevel Access level supplied by the caller.
     * @return Value exposed by the contract.
     */
    public IGroupMember create(int playerId, int groupId, GroupAccessLevel accessLevel) {
        return new GroupMemberData(0, playerId, groupId, (int) (System.currentTimeMillis() / 1000), accessLevel, "");
    }
}
