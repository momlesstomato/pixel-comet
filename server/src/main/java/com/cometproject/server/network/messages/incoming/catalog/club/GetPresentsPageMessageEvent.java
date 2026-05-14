package com.cometproject.server.network.messages.incoming.catalog.club;

import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.user.club.CatalogGiftsPageMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the get presents page message event published by the network message subsystem.
 */
public class GetPresentsPageMessageEvent
implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void handle(Session client, MessageEvent msg) {
        int pageId = 153;
        if (CatalogManager.getInstance().pageExists(pageId) && CatalogManager.getInstance().getPage(pageId).isEnabled()) {
            client.send(new CatalogGiftsPageMessageComposer(CatalogManager.getInstance().getPage(pageId), client.getPlayer().getSubscription()));
        }
    }
}

