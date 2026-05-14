package com.cometproject.game.groups.services;

import com.cometproject.api.caching.Cache;
import com.cometproject.api.game.groups.IGroupItemService;
import com.cometproject.api.game.groups.IGroupService;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.groups.types.components.IForumComponent;
import com.cometproject.api.game.groups.types.components.forum.ForumPermission;
import com.cometproject.api.game.groups.types.components.forum.IForumSettings;
import com.cometproject.api.game.groups.types.components.forum.IForumThread;
import com.cometproject.api.game.groups.types.components.membership.GroupAccessLevel;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;
import com.cometproject.game.groups.factories.GroupFactory;
import com.cometproject.game.groups.types.components.ForumComponent;
import com.cometproject.storage.api.data.Data;
import com.cometproject.storage.api.repositories.IGroupForumRepository;
import com.cometproject.storage.api.repositories.IGroupMemberRepository;
import com.cometproject.storage.api.repositories.IGroupRepository;
import com.cometproject.storage.mysql.models.factories.GroupForumSettingsFactory;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * Coordinates group behavior for the group subsystem.
 */
public class GroupService implements IGroupService {

    private final Cache<Integer, IGroup> groupCache;
    private final Cache<Integer, IGroupData> groupDataCache;

    private IGroupItemService groupItemService;

    private final IGroupMemberRepository groupMemberRepository;
    private final IGroupRepository groupRepository;
    private final IGroupForumRepository groupForumRepository;

    private final GroupFactory groupFactory;

    /**
     * Creates a group service instance for the group subsystem.
     *
     * @param groupCache Group cache supplied by the caller.
     * @param groupDataCache Group data cache supplied by the caller.
     * @param groupItemService Group item service supplied by the caller.
     * @param groupRepository Group repository supplied by the caller.
     * @param groupMemberRepository Group member repository supplied by the caller.
     * @param groupForumRepository Group forum repository supplied by the caller.
     */
    public GroupService(Cache<Integer, IGroup> groupCache, Cache<Integer, IGroupData> groupDataCache,
                        IGroupItemService groupItemService, IGroupRepository groupRepository,
                        IGroupMemberRepository groupMemberRepository, IGroupForumRepository groupForumRepository) {
        this.groupCache = groupCache;
        this.groupDataCache = groupDataCache;
        this.groupItemService = groupItemService;
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupForumRepository = groupForumRepository;

        this.groupFactory = new GroupFactory(this);
    }

    /**
     * Returns the data for this group contract.
     *
     * @param groupId Group id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IGroupData getData(final int groupId) {
        if(groupId == 0) {
            return null;
        }

        if (this.groupDataCache.contains(groupId)) {
            return this.groupDataCache.get(groupId);
        }

        final Data<IGroupData> data = new Data<>();

        this.groupRepository.getDataById(groupId, data::set);

        if (data.has()) {
            this.groupDataCache.add(groupId, data.get());
        }

        return data.get();
    }

    /**
     * Returns the group for this group contract.
     *
     * @param groupId Group id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IGroup getGroup(final int groupId) {
        if (groupId == 0) {
            return null;
        }

        if (this.groupCache.contains(groupId)) {
            return this.groupCache.get(groupId);
        }

        final IGroupData groupData = this.getData(groupId);

        if (groupData == null) {
            return null;
        }

        final Data<List<IGroupMember>> groupMemberData = new Data<>();
        final Data<List<Integer>> requestsData = new Data<>();

        this.groupMemberRepository.getAllByGroupId(groupId, groupMemberData::set);
        this.groupMemberRepository.getAllRequests(groupId, requestsData::set);

        if (!groupMemberData.has() || !requestsData.has()) {
            return null;
        }

        boolean foundOwner = false;

        for(IGroupMember member : groupMemberData.get()) {
            if(member.getPlayerId() == groupData.getOwnerId()) {
                foundOwner = true;
            }
        }

        if(!foundOwner) {
            this.groupMemberRepository.create(groupId, groupData.getOwnerId(), GroupAccessLevel.OWNER,
                    (member) -> groupMemberData.get().add(member));
        }

        return build(groupMemberData.get(), requestsData.get(), groupData);
    }

    /**
     * Persists group data for this group contract.
     *
     * @param groupData Group data supplied by the caller.
     */
    @Override
    public void saveGroupData(IGroupData groupData) {
        // Queue to save?
        this.groupRepository.saveGroupData(groupData);
    }

    /**
     * Creates forum settings for this group contract.
     *
     * @param forumComponent Forum component supplied by the caller.
     */
    @Override
    public void createForumSettings(IForumComponent forumComponent) {
        // Queue to save?
        this.groupRepository.createForumSettings(forumComponent);
    }

    /**
     * Adds forum to this group contract.
     *
     * @param group Group supplied by the caller.
     */
    @Override
    public void addForum(IGroup group) {
        group.getData().setHasForum(true);
        this.saveGroupData(group.getData());

        // Add forum component to the group. - me deprimo ken te lo juro
        Map<Integer, IForumThread> forumThreads = new HashMap<>();
        List<Integer> pinnedThreads = new ArrayList<>();

        ForumComponent forumComponent = new ForumComponent(GroupForumSettingsFactory.createSettings(group.getData().getId(), ForumPermission.getById(0), ForumPermission.getById(1), ForumPermission.getById(1), ForumPermission.getById(2)), pinnedThreads, forumThreads);
        group.setForum(forumComponent);

        this.createForumSettings(forumComponent);
    }

    /**
     * Creates group for this group contract.
     *
     * @param groupData Group data supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IGroup createGroup(IGroupData groupData, int ownerId) {
        final List<IGroupMember> groupMembers = Lists.newArrayList();
        final List<Integer> requests = Lists.newArrayList();

        this.groupRepository.create(groupData);

        this.groupMemberRepository.create(groupData.getId(), ownerId, GroupAccessLevel.OWNER, groupMembers::add);
        return this.build(groupMembers, requests, groupData);
    }

    /**
     * Adds group member to this group contract.
     *
     * @param group Group supplied by the caller.
     * @param groupMember Group member supplied by the caller.
     */
    @Override
    public void addGroupMember(IGroup group, IGroupMember groupMember) {
        if (groupMember.getMembershipId() == 0) {
            this.groupMemberRepository.create(group.getId(), groupMember.getPlayerId(), groupMember.getAccessLevel(), (member) -> {
                groupMember.setMembershipId(member.getMembershipId());
            });
        }

        group.getMembers().getAll().remove(groupMember.getPlayerId());

        if (groupMember.getAccessLevel().isAdmin()) {
            group.getMembers().getAdministrators().add(groupMember.getPlayerId());
        }

        group.getMembers().getAll().put(groupMember.getPlayerId(), groupMember);
    }

    /**
     * Removes group member from this group contract.
     *
     * @param group Group supplied by the caller.
     * @param groupMember Group member supplied by the caller.
     */
    @Override
    public void removeGroupMember(IGroup group, IGroupMember groupMember) {
        this.groupMemberRepository.delete(groupMember.getMembershipId());

        if(groupMember.getAccessLevel().isAdmin()) {
            group.getMembers().getAdministrators().remove(groupMember.getPlayerId());
        }

        group.getMembers().getAll().remove(groupMember.getPlayerId());
    }

    /**
     * Creates request for this group contract.
     *
     * @param group Group supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    @Override
    public void createRequest(IGroup group, int playerId) {
        if (group.getMembers().hasMembership(playerId) ||
                group.getMembers().getMembershipRequests().contains(playerId)) {
            return;
        }

        this.groupMemberRepository.createRequest(group.getId(), playerId);
        group.getMembers().getMembershipRequests().add(playerId);
    }

    /**
     * Removes request from this group contract.
     *
     * @param group Group supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    @Override
    public void removeRequest(IGroup group, int playerId) {
        if (!group.getMembers().getMembershipRequests().contains(playerId)) {
            return;
        }

        this.groupMemberRepository.deleteRequest(group.getId(), playerId);
        group.getMembers().getMembershipRequests().remove(playerId);
    }

    /**
     * Executes clear requests for this group contract.
     *
     * @param group Group supplied by the caller.
     */
    @Override
    public void clearRequests(IGroup group) {
        this.groupMemberRepository.clearRequests(group.getId());

        group.getMembers().getMembershipRequests().clear();
    }

    /**
     * Persists forum settings for this group contract.
     *
     * @param forumSettings Forum settings supplied by the caller.
     */
    @Override
    public void saveForumSettings(IForumSettings forumSettings) {
        this.groupForumRepository.saveSettings(forumSettings);
    }

    private IGroup build(List<IGroupMember> groupMemberData, List<Integer> requestsData,
                         IGroupData groupData) {
        final Map<Integer, IGroupMember> groupMembers = Maps.newConcurrentMap();
        final Set<Integer> requests = Sets.newConcurrentHashSet();
        final Set<Integer> administrators = Sets.newConcurrentHashSet();

        requests.addAll(requestsData);

        for (final IGroupMember groupMember : groupMemberData) {
            if (groupMember.getAccessLevel().isAdmin())
                administrators.add(groupMember.getPlayerId());

            groupMembers.put(groupMember.getPlayerId(), groupMember);
        }

        groupMemberData.clear();
        requestsData.clear();

        IForumSettings forumSettings = null;
        Map<Integer, IForumThread> forumThreads = null;
        List<Integer> pinnedThreads = null;

        if (groupData.hasForum()) {
            final Data<IForumSettings> forumSettingsData = new Data<>();
            final Data<Map<Integer, IForumThread>> forumThreadData = new Data<>();
            final Data<List<Integer>> pinnedThreadData = new Data<>();

            this.groupForumRepository.getSettingsByGroupId(groupData.getId(), forumSettingsData::set);

            if (forumSettingsData.has()) {
                forumSettings = forumSettingsData.get();

                // Now we have the forum settings, we can load the rest of the forum data
                this.groupForumRepository.getAllMessages(groupData.getId(), (threads, pinned) -> {
                    forumThreadData.set(threads);
                    pinnedThreadData.set(pinned);
                });

                if (forumThreadData.has() && pinnedThreadData.has()) {
                    forumThreads = forumThreadData.get();
                    pinnedThreads = pinnedThreadData.get();
                }
            }
        }

        final IGroup group = this.groupFactory.createGroupInstance(groupData, groupMembers, requests,
                administrators, forumSettings, pinnedThreads, forumThreads);

        this.groupCache.add(groupData.getId(), group);

        return group;
    }

    /**
     * Returns the item service for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IGroupItemService getItemService() {
        return this.groupItemService;
    }

    /**
     * Updates the item service for this group contract.
     *
     * @param itemService Item service supplied by the caller.
     */
    @Override
    public void setItemService(IGroupItemService itemService) {
        this.groupItemService = itemService;
    }

    /**
     * Removes group from this group contract.
     *
     * @param id Id supplied by the caller.
     */
    @Override
    public void removeGroup(int id) {
        final IGroup group = this.getGroup(id);

        if (group == null) {
            return;
        }

        group.dispose();

        this.groupRepository.deleteGroup(id);

        this.groupCache.remove(id);
        this.groupDataCache.remove(id);
    }
}
