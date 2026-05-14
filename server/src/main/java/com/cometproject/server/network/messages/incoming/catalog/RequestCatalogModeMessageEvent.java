package com.cometproject.server.network.messages.incoming.catalog;

import com.cometproject.server.composers.catalog.CatalogIndexMessageComposer;
import com.cometproject.server.composers.catalog.pets.CatalogModeComposer;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the request catalog mode message event published by the network message subsystem.
 */
public class RequestCatalogModeMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        String MODE = msg.readString();
        client.send(new CatalogModeComposer(MODE.equalsIgnoreCase("normal") ? 0 : 1));
        client.send(new CatalogIndexMessageComposer(CatalogManager.getInstance(), ItemManager.getInstance(), client.getPlayer().getData().getRank(), MODE));
    }
}
