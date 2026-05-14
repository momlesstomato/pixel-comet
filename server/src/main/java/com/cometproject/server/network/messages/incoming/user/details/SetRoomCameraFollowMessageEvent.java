package com.cometproject.server.network.messages.incoming.user.details;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;


/**
 * Represents the set room camera follow message event published by the network message subsystem.
 */
public class SetRoomCameraFollowMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        boolean roomCameraFollow = msg.readBoolean();

        client.getPlayer().getSettings().setRoomCameraFollow(roomCameraFollow);
        PlayerDao.saveRoomCameraFollow(roomCameraFollow, client.getPlayer().getId());
    }
}
