package com.cometproject.server.network.messages.incoming.group;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.server.composers.group.GroupMembersMessageComposer;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

import java.util.ArrayList;

/**
 * Represents the decline membership message event published by the network message subsystem.
 */
public class DeclineMembershipMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int groupId = msg.readInt();
        int playerId = msg.readInt();

        if (!client.getPlayer().getGroups().contains(groupId))
            return;

        IGroup group = GameContext.getCurrent().getGroupService().getGroup(groupId);

        if (group == null || group.getData().getOwnerId() != client.getPlayer().getId())
            return;

        if (!group.getMembers().getMembershipRequests().contains(playerId))
            return;


        GameContext.getCurrent().getGroupService().removeRequest(group, playerId);

        client.send(new GroupMembersMessageComposer(group.getData(), 0,
                new ArrayList<PlayerAvatar>(), 2, "",
                true));
    }
}
