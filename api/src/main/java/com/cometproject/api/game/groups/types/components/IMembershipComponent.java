package com.cometproject.api.game.groups.types.components;

import com.cometproject.api.game.groups.types.GroupComponent;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.api.networking.sessions.ISessionService;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Defines the i membership component contract for the group subsystem.
 */
public interface IMembershipComponent extends GroupComponent {
    /**
     * Executes the broadcast message operation for this group contract.
     *
     * @param sessionService Session service value supplied by the caller.
     * @param messageComposer Message composer value supplied by the caller.
     * @param sender Sender value supplied by the caller.
     */
    void broadcastMessage(ISessionService sessionService, IMessageComposer messageComposer, int sender);

    /**
     * Indicates whether this group contract has membership.
     *
     * @param playerId Player id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasMembership(final int playerId);

    /**
     * Indicates whether this group contract has admin perm.
     *
     * @param playerId Player id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasAdminPerm(final int playerId);

    /**
     * Returns the all associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, IGroupMember> getAll();

    /**
     * Returns the members as list associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<IGroupMember> getMembersAsList();

    /**
     * Returns the administrators associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Set<Integer> getAdministrators();

    /**
     * Returns the membership requests associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Set<Integer> getMembershipRequests();

    /**
     * Returns the member avatars associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<PlayerAvatar> getMemberAvatars();

    /**
     * Returns the request avatars associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<PlayerAvatar> getRequestAvatars();

    /**
     * Returns the admin avatars associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<PlayerAvatar> getAdminAvatars();
}
