package com.cometproject.server.network.messages.incoming.catalog;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

public class GetCataIndexMessageEvent
implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) {
        //client.send(new CatalogIndexMessageComposer((ICatalogService)CatalogManager.getInstance(), (IFurnitureService)ItemManager.getInstance(), client.getPlayer().getData().getRank()));
    }
}

