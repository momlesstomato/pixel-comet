package com.cometproject.server.network.messages.incoming.catalog;

import com.cometproject.server.composers.catalog.CatalogPageMessageComposer;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the get cata page message event published by the network message subsystem.
 */
public class GetCataPageMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        int pageId = msg.readInt();
        int unknown = msg.readInt();
        String mode = msg.readString();

        if (client.getPlayer().cancelPageOpen) {
            client.getPlayer().cancelPageOpen = false;
            return;
        }

        if (CatalogManager.getInstance().pageExists(pageId) && CatalogManager.getInstance().getPage(pageId).isEnabled()) {
            client.send(new CatalogPageMessageComposer("NORMAL", CatalogManager.getInstance().getPage(pageId), client.getPlayer(), CatalogManager.getInstance()));
        }
    }
}


