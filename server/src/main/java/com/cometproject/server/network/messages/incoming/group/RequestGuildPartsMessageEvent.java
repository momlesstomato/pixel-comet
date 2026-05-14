package com.cometproject.server.network.messages.incoming.group;

import com.cometproject.api.game.GameContext;
import com.cometproject.server.composers.catalog.groups.GroupElementsMessageComposer;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the request guild parts message event published by the network message subsystem.
 */
public class RequestGuildPartsMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        if (client == null || GameContext.getCurrent().getGroupService() == null) {
            return;
        }
        client.send(new GroupElementsMessageComposer(GameContext.getCurrent().getGroupService().getItemService()));
    }
}
