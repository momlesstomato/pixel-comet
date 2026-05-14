package com.cometproject.server.network.messages.incoming.navigator.updated;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.navigator.updated.NavigatorSavedSearchesMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Represents the delete navigator saved search message event published by the network message subsystem.
 */
public class DeleteNavigatorSavedSearchMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int savedSearch = msg.readInt();

        if (client.getPlayer().getNavigator().getSavedSearches().containsKey(savedSearch)) {
            PlayerDao.deleteSearch(savedSearch);
            client.getPlayer().getNavigator().getSavedSearches().remove(savedSearch);

            client.send(new NavigatorSavedSearchesMessageComposer(client.getPlayer().getNavigator().getSavedSearches()));
        }
    }
}
