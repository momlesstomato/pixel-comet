package com.cometproject.server.network.messages.incoming.navigator;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the popular rooms message event published by the network message subsystem.
 */
public class PopularRoomsMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        int categoryId = Integer.parseInt(msg.readString());

//        client.send(new NavigatorFlatListMessageComposer(2, "", RoomManager.getInstance().getRoomsByCategory(categoryId, 50)));
    }
}
