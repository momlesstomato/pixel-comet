package com.cometproject.api.game.groups;

import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.groups.types.components.IForumComponent;
import com.cometproject.api.game.groups.types.components.forum.IForumSettings;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;

/**
 * Defines the i group service contract for the group subsystem.
 */
public interface IGroupService {

    /**
     * Returns the data associated with this group contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IGroupData getData(int groupId);

    /**
     * Returns the group associated with this group contract.
     *
     * @param groupId Group id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IGroup getGroup(int groupId);

    /**
     * Persists group data data for this group contract.
     *
     * @param groupData Group data value supplied by the caller.
     */
    void saveGroupData(IGroupData groupData);

    /**
     * Creates forum settings data for this group contract.
     *
     * @param forumComponent Forum component value supplied by the caller.
     */
    void createForumSettings(IForumComponent forumComponent);

    /**
     * Adds forum data to this group contract.
     *
     * @param group Group value supplied by the caller.
     */
    void addForum(IGroup group);

    /**
     * Creates group data for this group contract.
     *
     * @param groupData Group data value supplied by the caller.
     * @param ownerId Owner id value supplied by the caller.
     * @return Result produced by the mutation.
     */
    IGroup createGroup(IGroupData groupData, int ownerId);

    /**
     * Adds group member data to this group contract.
     *
     * @param group Group value supplied by the caller.
     * @param groupMember Group member value supplied by the caller.
     */
    void addGroupMember(IGroup group, IGroupMember groupMember);

    /**
     * Removes group member data from this group contract.
     *
     * @param group Group value supplied by the caller.
     * @param groupMember Group member value supplied by the caller.
     */
    void removeGroupMember(IGroup group, IGroupMember groupMember);

    /**
     * Creates request data for this group contract.
     *
     * @param group Group value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     */
    void createRequest(IGroup group, int playerId);

    /**
     * Removes request data from this group contract.
     *
     * @param group Group value supplied by the caller.
     * @param playerId Player id value supplied by the caller.
     */
    void removeRequest(IGroup group, int playerId);

    /**
     * Executes the clear requests operation for this group contract.
     *
     * @param group Group value supplied by the caller.
     */
    void clearRequests(IGroup group);

    /**
     * Persists forum settings data for this group contract.
     *
     * @param forumSettings Forum settings value supplied by the caller.
     */
    void saveForumSettings(IForumSettings forumSettings);

    /**
     * Returns the item service associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IGroupItemService getItemService();

    /**
     * Updates the item service value for this group contract.
     *
     * @param itemService Item service value supplied by the caller.
     */
    void setItemService(IGroupItemService itemService);

    /**
     * Removes group data from this group contract.
     *
     * @param id Id value supplied by the caller.
     */
    void removeGroup(int id);
}
