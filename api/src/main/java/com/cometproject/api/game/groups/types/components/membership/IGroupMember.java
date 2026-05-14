package com.cometproject.api.game.groups.types.components.membership;

/**
 * Defines the i group member contract for the group subsystem.
 */
public interface IGroupMember {
    /**
     * Returns the membership id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getMembershipId();

    /**
     * Updates the membership id value for this group contract.
     *
     * @param membershipId Membership id value supplied by the caller.
     */
    void setMembershipId(int membershipId);

    /**
     * Returns the player id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPlayerId();

    /**
     * Returns the group id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getGroupId();

    /**
     * Returns the access level associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    GroupAccessLevel getAccessLevel();

    /**
     * Updates the access level value for this group contract.
     *
     * @param accessLevel Access level value supplied by the caller.
     */
    void setAccessLevel(GroupAccessLevel accessLevel);

    /**
     * Updates the role value for this group contract.
     *
     * @param role Role value supplied by the caller.
     */
    void setRole(String role);

    /**
     * Returns the date joined associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDateJoined();

    /**
     * Returns the role associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getRole();
}
