package com.cometproject.server.storage.cache.objects;

import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.groups.types.components.forum.IForumSettings;
import com.cometproject.api.game.groups.types.components.forum.IForumThread;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;
import com.cometproject.server.storage.cache.CachableObject;

import java.util.List;
import java.util.Map;

/**
 * Describes group data object behavior for the storage subsystem.
 */
public class GroupDataObject extends CachableObject {
    private final int id;
    private final IGroupData groupData;
    private final List<IGroupMember> groupMembers;
    private final List<Integer> groupRequests;

    private final IForumSettings forumSettings;
    private final Map<Integer, IForumThread> forumThreads;

    /**
     * Creates a group data object instance for the storage subsystem.
     *
     * @param id Id supplied by the caller.
     * @param groupData Group data supplied by the caller.
     * @param groupMembers Group members supplied by the caller.
     * @param groupRequests Group requests supplied by the caller.
     * @param forumSettings Forum settings supplied by the caller.
     * @param forumThreads Forum threads supplied by the caller.
     */
    public GroupDataObject(int id, IGroupData groupData, List<IGroupMember> groupMembers, List<Integer> groupRequests, IForumSettings forumSettings, Map<Integer, IForumThread> forumThreads) {
        this.id = id;
        this.groupData = groupData;
        this.groupMembers = groupMembers;
        this.groupRequests = groupRequests;
        this.forumSettings = forumSettings;
        this.forumThreads = forumThreads;
    }

    /**
     * Returns the id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the group data for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IGroupData getGroupData() {
        return groupData;
    }

    /**
     * Returns the group members for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public List<IGroupMember> getGroupMembers() {
        return groupMembers;
    }

    /**
     * Returns the forum settings for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public IForumSettings getForumSettings() {
        return forumSettings;
    }

    /**
     * Returns the forum threads for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, IForumThread> getForumThreads() {
        return forumThreads;
    }

    /**
     * Returns the group requests for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public List<Integer> getGroupRequests() {
        return groupRequests;
    }
}
