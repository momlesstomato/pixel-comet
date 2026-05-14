package com.cometproject.server.network.messages.incoming.user.wardrobe;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.user.wardrobe.WardrobeMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the wardrobe message event published by the network message subsystem.
 */
public class WardrobeMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.send(new WardrobeMessageComposer(client.getPlayer().getSettings().getWardrobe()));
    }
}
