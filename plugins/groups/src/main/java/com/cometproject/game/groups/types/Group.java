package com.cometproject.game.groups.types;

import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.groups.types.components.IForumComponent;
import com.cometproject.api.game.groups.types.components.IMembershipComponent;

/**
 * Describes group behavior for the group subsystem.
 */
public class Group implements IGroup {
    private final IGroupData groupData;
    private final IMembershipComponent membershipComponent;

    private IForumComponent forumComponent;

    /**
     * Creates a group instance for the group subsystem.
     *
     * @param groupData Group data supplied by the caller.
     * @param membershipComponent Membership component supplied by the caller.
     * @param forumComponent Forum component supplied by the caller.
     */
    public Group(IGroupData groupData, IMembershipComponent membershipComponent, IForumComponent forumComponent) {
        this.groupData = groupData;

        this.membershipComponent = membershipComponent;
        this.forumComponent = forumComponent;
    }

    /**
     * Releases resources owned by this group component.
     */
    @Override
    public void dispose() {
        this.membershipComponent.dispose();

        if (this.forumComponent != null) {
            this.forumComponent.dispose();
        }
    }

    /**
     * Returns the id for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return this.getData().getId();
    }

    /**
     * Returns the data for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IGroupData getData() {
        return this.groupData;
    }

    /**
     * Returns the members for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IMembershipComponent getMembers() {
        return this.membershipComponent;
    }

    /**
     * Returns the forum for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IForumComponent getForum() {
        return this.forumComponent;
    }

    /**
     * Updates the forum for this group contract.
     *
     * @param forumComponent Forum component supplied by the caller.
     */
    @Override
    public void setForum(IForumComponent forumComponent) {
        this.forumComponent = forumComponent;
    }
}
