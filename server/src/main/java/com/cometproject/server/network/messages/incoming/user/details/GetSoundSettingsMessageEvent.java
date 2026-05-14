package com.cometproject.server.network.messages.incoming.user.details;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the get sound settings message event published by the network message subsystem.
 */
public class GetSoundSettingsMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        //client.send(new PlayerSettingsMessageComposer(client.getPlayer().getSettings(), 2));
    }
}
