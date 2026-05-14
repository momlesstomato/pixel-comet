package com.cometproject.server.network.messages.incoming.catalog;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.crafting.MarketPlaceConfigMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the request marketplace config message event published by the network message subsystem.
 */
public class RequestMarketplaceConfigMessageEvent
implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void handle(Session client, MessageEvent msg) {
        client.send(new MarketPlaceConfigMessageComposer());
    }
}

