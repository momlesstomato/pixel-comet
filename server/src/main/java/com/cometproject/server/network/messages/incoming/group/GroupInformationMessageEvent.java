package com.cometproject.server.network.messages.incoming.group;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.server.composers.group.GroupInformationMessageComposer;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the group information message event published by the network message subsystem.
 */
public class GroupInformationMessageEvent implements Event {
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
        boolean flag = msg.readBoolean();

        IGroup group = GameContext.getCurrent().getGroupService().getGroup(groupId);
        if (group == null) {
            return;
        }
        client.send(new GroupInformationMessageComposer(group, GameContext.getCurrent().getRoomService().getRoomData(group.getData().getRoomId()), flag, client.getPlayer().getId() == group.getData().getOwnerId(), group.getMembers().getAdministrators().contains(client.getPlayer().getId()), group.getMembers().getAll().containsKey(client.getPlayer().getId()) ? 1 : (group.getMembers().getMembershipRequests().contains(client.getPlayer().getId()) ? 2 : 0)));
    }
}
