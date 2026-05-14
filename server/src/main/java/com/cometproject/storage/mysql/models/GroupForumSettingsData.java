package com.cometproject.storage.mysql.models;

import com.cometproject.api.game.groups.types.components.forum.ForumPermission;
import com.cometproject.api.game.groups.types.components.forum.IForumSettings;

/**
 * Carries group forum settings data data for the MySQL storage subsystem.
 */
public class GroupForumSettingsData implements IForumSettings {

    private final int groupId;
    private ForumPermission readPermission;
    private ForumPermission postPermission;
    private ForumPermission startThreadPermission;
    private ForumPermission moderatePermission;

    /**
     * Creates a group forum settings data instance for the MySQL storage subsystem.
     *
     * @param groupId Group id supplied by the caller.
     * @param readPermission Read permission supplied by the caller.
     * @param postPermission Post permission supplied by the caller.
     * @param startThreadPermission Start thread permission supplied by the caller.
     * @param moderatePermission Moderate permission supplied by the caller.
     */
    public GroupForumSettingsData(int groupId, ForumPermission readPermission, ForumPermission postPermission, ForumPermission startThreadPermission, ForumPermission moderatePermission) {
        this.groupId = groupId;
        this.readPermission = readPermission;
        this.postPermission = postPermission;
        this.startThreadPermission = startThreadPermission;
        this.moderatePermission = moderatePermission;
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
     * Returns the read permission for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public ForumPermission getReadPermission() {
        return this.readPermission;
    }

    /**
     * Updates the read permission for this MySQL storage contract.
     *
     * @param readPermission Read permission supplied by the caller.
     */
    @Override
    public void setReadPermission(ForumPermission readPermission) {
        this.readPermission = readPermission;
    }

    /**
     * Returns the post permission for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public ForumPermission getPostPermission() {
        return this.postPermission;
    }

    /**
     * Updates the post permission for this MySQL storage contract.
     *
     * @param postPermission Post permission supplied by the caller.
     */
    @Override
    public void setPostPermission(ForumPermission postPermission) {
        this.postPermission = postPermission;
    }

    /**
     * Returns the start threads permission for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public ForumPermission getStartThreadsPermission() {
        return this.startThreadPermission;
    }

    /**
     * Updates the start threads permission for this MySQL storage contract.
     *
     * @param startThreadsPermission Start threads permission supplied by the caller.
     */
    @Override
    public void setStartThreadsPermission(ForumPermission startThreadsPermission) {
        this.startThreadPermission = startThreadsPermission;
    }

    /**
     * Returns the moderate permission for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public ForumPermission getModeratePermission() {
        return this.moderatePermission;
    }

    /**
     * Updates the moderate permission for this MySQL storage contract.
     *
     * @param moderatePermission Moderate permission supplied by the caller.
     */
    @Override
    public void setModeratePermission(ForumPermission moderatePermission) {
        this.moderatePermission = moderatePermission;
    }
}
