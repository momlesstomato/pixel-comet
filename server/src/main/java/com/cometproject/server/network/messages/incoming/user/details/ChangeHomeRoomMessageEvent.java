package com.cometproject.server.network.messages.incoming.user.details;

import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.handshake.HomeRoomMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.engine.FollowRoomDataMessageComposer;
import com.cometproject.server.network.messages.outgoing.room.settings.RoomInfoUpdatedMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;


/**
 * Represents the change home room message event published by the network message subsystem.
 */
public class ChangeHomeRoomMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null || client.getPlayer().getEntity().getRoom() == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        int roomId = room.getId();

        client.getPlayer().getSettings().setHomeRoom(roomId);
        client.send(new HomeRoomMessageComposer(roomId, roomId));
        client.send(new RoomInfoUpdatedMessageComposer(roomId));

        client.send(new FollowRoomDataMessageComposer(room.getData(), false, false, true, false));

        PlayerDao.updateHomeRoom(roomId, client.getPlayer().getId());
    }
}
