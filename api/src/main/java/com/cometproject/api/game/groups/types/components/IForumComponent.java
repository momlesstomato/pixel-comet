package com.cometproject.api.game.groups.types.components;

import com.cometproject.api.game.groups.types.GroupComponent;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.groups.types.components.forum.IForumSettings;
import com.cometproject.api.game.groups.types.components.forum.IForumThread;
import com.cometproject.api.networking.messages.IComposer;

import java.util.List;
import java.util.Map;

/**
 * Defines the i forum component contract for the group subsystem.
 */
public interface IForumComponent extends GroupComponent {
    /**
     * Executes the compose data operation for this group contract.
     *
     * @param msg Msg value supplied by the caller.
     * @param groupData Group data value supplied by the caller.
     */
    void composeData(IComposer msg, IGroupData groupData);

    /**
     * Returns the forum threads associated with this group contract.
     *
     * @param start Start value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IForumThread> getForumThreads(int start);

    /**
     * Returns the forum settings associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IForumSettings getForumSettings();

    /**
     * Returns the forum threads associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, IForumThread> getForumThreads();

    /**
     * Returns the pinned threads associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<Integer> getPinnedThreads();
}