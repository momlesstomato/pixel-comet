package com.cometproject.game.groups.types.components;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.IGroupService;
import com.cometproject.api.game.groups.types.components.IMembershipComponent;
import com.cometproject.api.game.groups.types.components.membership.IGroupMember;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.api.networking.sessions.ISessionService;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Owns membership behavior inside the group subsystem.
 */
public class MembershipComponent implements IMembershipComponent {

    private final int groupId;

    private final IGroupService groupService;

    private final Map<Integer, IGroupMember> groupMembers;
    private final Set<Integer> membershipRequests;
    private final Set<Integer> administrators;

    /**
     * Creates a membership component instance for the group subsystem.
     *
     * @param groupId Group id supplied by the caller.
     * @param groupService Group service supplied by the caller.
     * @param groupMembers Group members supplied by the caller.
     * @param membershipRequests Membership requests supplied by the caller.
     * @param administrators Administrators supplied by the caller.
     */
    public MembershipComponent(final int groupId, IGroupService groupService, Map<Integer, IGroupMember> groupMembers,
                               Set<Integer> membershipRequests, Set<Integer> administrators) {
        this.groupId = groupId;
        this.groupService = groupService;

        this.groupMembers = groupMembers;
        this.membershipRequests = membershipRequests;
        this.administrators = administrators;
    }

    /**
     * Releases resources owned by this group component.
     */
    @Override
    public void dispose() {
        this.groupMembers.clear();
        this.membershipRequests.clear();
        this.administrators.clear();
    }

    /**
     * Executes broadcast message for this group contract.
     *
     * @param sessionService Session service supplied by the caller.
     * @param messageComposer Message composer supplied by the caller.
     * @param sender Sender supplied by the caller.
     */
    @Override
    public void broadcastMessage(ISessionService sessionService, IMessageComposer messageComposer, int sender) {
        // Not implemented yet, might be revisited when module api for messenger is fleshed out, this won't be needed
    }

    /**
     * Indicates whether this group contract has membership.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasMembership(int playerId) {
        return this.groupMembers.containsKey(playerId);
    }

    /**
     * Indicates whether this group contract has admin perm.
     *
     * @param playerId Player identifier used by the operation.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasAdminPerm(int playerId) {
        return this.administrators.contains(playerId);
    }

    /**
     * Returns the all for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<Integer, IGroupMember> getAll() {
        return this.groupMembers;
    }

    /**
     * Returns the members as list for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<IGroupMember> getMembersAsList() {
        return new ArrayList<>(this.groupMembers.values());
    }

    /**
     * Returns the administrators for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Set<Integer> getAdministrators() {
        return this.administrators;
    }

    /**
     * Returns the membership requests for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Set<Integer> getMembershipRequests() {
        return this.membershipRequests;
    }

    private List<PlayerAvatar> getMembers(int type) {
        final List<PlayerAvatar> playerAvatars = Lists.newArrayList();

        switch (type) {
            default: {
                for(IGroupMember groupMember : this.getMembersAsList()) {
                    addPlayerAvatar(groupMember.getPlayerId(), playerAvatars, (playerAvatar -> {
                        playerAvatar.tempData(groupMember);
                    }));
                }
            } break;
            case 1: {

                for(Integer adminId : this.getAdministrators()) {
                    addPlayerAvatar(adminId, playerAvatars);
                }
            } break;
            case 2: {
                for(Integer requestPlayerId : this.getMembershipRequests()) {
                    addPlayerAvatar(requestPlayerId, playerAvatars);
                }
            } break;

        }

        return playerAvatars;
    }

    /**
     * Returns the member avatars for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<PlayerAvatar> getMemberAvatars() {
        return this.getMembers(0);
    }

    /**
     * Returns the admin avatars for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<PlayerAvatar> getAdminAvatars() {
        return this.getMembers(1);
    }

    /**
     * Returns the request avatars for this group contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<PlayerAvatar> getRequestAvatars() {
        return this.getMembers(2);
    }

    private void addPlayerAvatar(final int playerId, final List<PlayerAvatar> playerAvatars, Consumer<PlayerAvatar> avatarConsumer) {
        final PlayerAvatar playerAvatar = GameContext.getCurrent().getPlayerService().getAvatarByPlayerId(playerId, PlayerAvatar.USERNAME_FIGURE);

        if(playerAvatar != null) {
            if(avatarConsumer != null) {
                avatarConsumer.accept(playerAvatar);
            }

            playerAvatars.add(playerAvatar);
        }

    }

    private void addPlayerAvatar(final int playerId, final List<PlayerAvatar> playerAvatars) {
        addPlayerAvatar(playerId, playerAvatars, null);
    }
}