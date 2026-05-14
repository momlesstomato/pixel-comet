package com.cometproject.api.game.groups.types;

import com.cometproject.api.game.groups.types.components.IForumComponent;
import com.cometproject.api.game.groups.types.components.IMembershipComponent;

/**
 * Defines the i group contract for the group subsystem.
 */
public interface IGroup {
    /**
     * Returns the id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the data associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IGroupData getData();

    /**
     * Returns the members associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IMembershipComponent getMembers();

    /**
     * Returns the forum associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IForumComponent getForum();

    /**
     * Updates the forum value for this group contract.
     *
     * @param forumComponent Forum component value supplied by the caller.
     */
    void setForum(IForumComponent forumComponent);

    /**
     * Executes the dispose operation for this group contract.
     */
    void dispose();
}
