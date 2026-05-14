package com.cometproject.server.network.messages.incoming.room.settings;

import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.settings.RightsListMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the users with rights message event published by the network message subsystem.
 */
public class UsersWithRightsMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        int roomId = msg.readInt();

        Room room = client.getPlayer().getEntity().getRoom();

        if (room == null) {
            return;
        }

        client.send(new RightsListMessageComposer(room.getId(), room.getRights().getAll()));
    }
}
