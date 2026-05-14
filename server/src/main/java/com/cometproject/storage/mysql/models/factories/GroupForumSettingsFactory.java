package com.cometproject.storage.mysql.models.factories;

import com.cometproject.api.game.groups.types.components.forum.ForumPermission;
import com.cometproject.api.game.groups.types.components.forum.IForumSettings;
import com.cometproject.storage.mysql.models.GroupForumSettingsData;

/**
 * Creates group forum settings instances for the MySQL storage subsystem.
 */
public class GroupForumSettingsFactory {

    /**
     * Creates settings for this MySQL storage contract.
     *
     * @param groupId Group id supplied by the caller.
     * @param readPermission Read permission supplied by the caller.
     * @param postPermission Post permission supplied by the caller.
     * @param startThreadPermission Start thread permission supplied by the caller.
     * @param moderatePermission Moderate permission supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static IForumSettings createSettings(int groupId, ForumPermission readPermission, ForumPermission postPermission,
                                                ForumPermission startThreadPermission, ForumPermission moderatePermission) {
        return new GroupForumSettingsData(groupId, readPermission, postPermission, startThreadPermission, moderatePermission);
    }
}
