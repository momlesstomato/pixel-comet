package com.cometproject.game.groups.factories;

import com.cometproject.api.game.groups.IGroupService;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.groups.types.components.IForumComponent;
import com.cometproject.api.game.groups.types.components.IMembershipComponent;
import com.cometproject.api.game.groups.types.components.forum.IForumSettings;
import com.cometproject.api.game.groups.types.components.forum.IForumThread;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;
import com.cometproject.game.groups.types.Group;
import com.cometproject.game.groups.types.components.ForumComponent;
import com.cometproject.game.groups.types.components.MembershipComponent;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Creates group instances for the group subsystem.
 */
public class GroupFactory {
    private final IGroupService groupService;

    /**
     * Creates a group factory instance for the group subsystem.
     *
     * @param groupService Group service supplied by the caller.
     */
    public GroupFactory(IGroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Creates group instance for this group contract.
     *
     * @param groupData Group data supplied by the caller.
     * @param groupMembers Group members supplied by the caller.
     * @param membershipRequests Membership requests supplied by the caller.
     * @param administrators Administrators supplied by the caller.
     * @param forumSettings Forum settings supplied by the caller.
     * @param pinnedThreads Pinned threads supplied by the caller.
     * @param forumThreads Forum threads supplied by the caller.
     * @return Value exposed by the contract.
     */
    public IGroup createGroupInstance(IGroupData groupData, Map<Integer, IGroupMember> groupMembers,
                                      Set<Integer> membershipRequests, Set<Integer> administrators,
                                      IForumSettings forumSettings, List<Integer> pinnedThreads,
                                      Map<Integer, IForumThread> forumThreads) {
        final IMembershipComponent membershipComponent = new MembershipComponent(groupData.getId(), this.groupService,
                groupMembers, membershipRequests, administrators);

        IForumComponent forumComponent = null;

        if (forumSettings != null && pinnedThreads != null && forumThreads != null) {
            forumComponent = new ForumComponent(forumSettings, pinnedThreads, forumThreads);
        }

        final IGroup group = new Group(groupData, membershipComponent, forumComponent);

        // Do anything else to it? E.g

        return group;
    }
}
