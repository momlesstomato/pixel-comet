package com.cometproject.server.network.messages.incoming.room.settings;

import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.settings.RoomSettingsDataMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the get room settings data message event published by the network message subsystem.
 */
public class GetRoomSettingsDataMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        int roomId = msg.readInt();

        Room room = RoomManager.getInstance().get(roomId);

        if (room == null) {
            return;
        }

        if (room.getData().getOwnerId() == client.getPlayer().getId() || client.getPlayer().getPermissions().getRank().roomFullControl()) {
            client.send(new RoomSettingsDataMessageComposer(room, client.getPlayer().getPermissions().getRank().modTool()));
        }
    }
}
