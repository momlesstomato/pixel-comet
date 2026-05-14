package com.cometproject.server.network.messages.incoming.room.access;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the load room by door bell message event published by the network message subsystem.
 */
public class LoadRoomByDoorBellMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null) {
            return;
        }

        // re-do room checks like max player check etc
        client.getPlayer().getEntity().joinRoom(client.getPlayer().getEntity().getRoom(), "");
    }
}
