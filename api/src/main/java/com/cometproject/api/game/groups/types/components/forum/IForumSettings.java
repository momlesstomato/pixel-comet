package com.cometproject.api.game.groups.types.components.forum;

/**
 * Defines the i forum settings contract for the group subsystem.
 */
public interface IForumSettings {
    /**
     * Returns the group id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getGroupId();

    /**
     * Returns the read permission associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ForumPermission getReadPermission();

    /**
     * Updates the read permission value for this group contract.
     *
     * @param readPermission Read permission value supplied by the caller.
     */
    void setReadPermission(ForumPermission readPermission);

    /**
     * Returns the post permission associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ForumPermission getPostPermission();

    /**
     * Updates the post permission value for this group contract.
     *
     * @param postPermission Post permission value supplied by the caller.
     */
    void setPostPermission(ForumPermission postPermission);

    /**
     * Returns the start threads permission associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ForumPermission getStartThreadsPermission();

    /**
     * Updates the start threads permission value for this group contract.
     *
     * @param startThreadsPermission Start threads permission value supplied by the caller.
     */
    void setStartThreadsPermission(ForumPermission startThreadsPermission);

    /**
     * Returns the moderate permission associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    ForumPermission getModeratePermission();

    /**
     * Updates the moderate permission value for this group contract.
     *
     * @param moderatePermission Moderate permission value supplied by the caller.
     */
    void setModeratePermission(ForumPermission moderatePermission);
}
