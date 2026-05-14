package com.cometproject.server.network.messages.incoming.catalog;

import com.cometproject.api.game.catalog.types.ICatalogItem;
import com.cometproject.server.composers.catalog.CatalogOfferMessageComposer;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the get catalog offer message event published by the network message subsystem.
 */
public class GetCatalogOfferMessageEvent
implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int offerId = msg.readInt();
        if (offerId == -1) {
            return;
        }
        ICatalogItem catalogItem = CatalogManager.getInstance().getCatalogItemByOfferId(offerId);
        if (catalogItem != null) {
            client.send(new CatalogOfferMessageComposer(catalogItem));
        }
    }
}

