package com.cometproject.server.network.messages.incoming.user.club;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.user.club.ClubStatusMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the club status message event published by the network message subsystem.
 */
public class ClubStatusMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        if (client == null) {
            return;
        }

        client.send(new ClubStatusMessageComposer(client.getPlayer().getSubscription()));
        //client.send(client.getPlayer().composeCurrenciesBalance());
    }
}
