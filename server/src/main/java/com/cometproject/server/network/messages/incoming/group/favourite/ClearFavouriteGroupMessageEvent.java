package com.cometproject.server.network.messages.incoming.group.favourite;

import com.cometproject.server.composers.group.UpdateFavouriteGroupMessageComposer;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.avatar.AvatarUpdateMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.AvatarsMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.avatar.LeaveRoomMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the clear favourite group message event published by the network message subsystem.
 */
public class ClearFavouriteGroupMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.getPlayer().getData().setFavouriteGroup(0);
        client.getPlayer().getData().save();

        if (client.getPlayer().getEntity() != null) {
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new LeaveRoomMessageComposer(client.getPlayer().getEntity().getId()));
            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new AvatarsMessageComposer(client.getPlayer().getEntity()));

            client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new AvatarUpdateMessageComposer(client.getPlayer().getEntity()));
        }

        client.send(new UpdateFavouriteGroupMessageComposer(client.getPlayer().getId()));
    }
}
