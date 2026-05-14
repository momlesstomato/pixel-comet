package com.cometproject.server.network.messages.incoming.help.guides;

import com.cometproject.server.game.guides.types.HelpRequest;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.engine.RoomForwardMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the guide follow user message event published by the network message subsystem.
 */
public class GuideFollowUserMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final HelpRequest helpRequest = client.getPlayer().getHelpRequest();

        Room room = helpRequest.getOtherElement(client, helpRequest).getPlayer().getEntity().getRoom();

        if (room == null)
            return;


        if (client.getPlayer().getEntity() != null && client.getPlayer().getEntity().getRoom() != null) {
            Room roomInstance = client.getPlayer().getEntity().getRoom();

            if (roomInstance.getId() == room.getId()) {
                client.getPlayer().getEntity().leaveRoom(false, false, false);
            }
        }

        client.send(new RoomForwardMessageComposer(room.getId()));
    }
}
