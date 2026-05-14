package com.cometproject.storage.mysql.models;

import com.cometproject.api.game.groups.types.components.membership.GroupAccessLevel;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;

/**
 * Carries group member data data for the MySQL storage subsystem.
 */
public class GroupMemberData implements IGroupMember {

    private final int playerId;
    private final int groupId;
    private final int dateJoined;
    private String role;
    private int membershipId;
    private GroupAccessLevel groupAccessLevel;

    /**
     * Creates a group member data instance for the MySQL storage subsystem.
     *
     * @param membershipId Membership id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     * @param groupId Group id supplied by the caller.
     * @param dateJoined Date joined supplied by the caller.
     * @param groupAccessLevel Group access level supplied by the caller.
     * @param role Role supplied by the caller.
     */
    public GroupMemberData(int membershipId, int playerId, int groupId, int dateJoined, GroupAccessLevel groupAccessLevel, String role) {
        this.membershipId = membershipId;
        this.playerId = playerId;
        this.groupId = groupId;
        this.dateJoined = dateJoined;
        this.role = role;
        this.groupAccessLevel = groupAccessLevel;
    }


    /**
     * Returns the membership id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getMembershipId() {
        return this.membershipId;
    }

    /**
     * Updates the membership id for this MySQL storage contract.
     *
     * @param membershipId Membership id supplied by the caller.
     */
    @Override
    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    /**
     * Returns the player id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getPlayerId() {
        return this.playerId;
    }

    /**
     * Returns the group id for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getGroupId() {
        return this.groupId;
    }

    /**
     * Returns the access level for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public GroupAccessLevel getAccessLevel() {
        return this.groupAccessLevel;
    }

    /**
     * Updates the access level for this MySQL storage contract.
     *
     * @param accessLevel Access level supplied by the caller.
     */
    @Override
    public void setAccessLevel(GroupAccessLevel accessLevel) {
        this.groupAccessLevel = accessLevel;
    }

    /**
     * Returns the date joined for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getDateJoined() {
        return this.dateJoined;
    }

    /**
     * Updates the role for this MySQL storage contract.
     *
     * @param role Role supplied by the caller.
     */
    @Override
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Returns the role for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getRole() {
        return this.role;
    }
}
