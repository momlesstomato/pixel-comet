package com.cometproject.server.network.messages.incoming.navigator.updated;

import com.cometproject.server.game.players.components.types.navigator.SavedSearch;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.navigator.updated.NavigatorSavedSearchesMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Represents the save navigator search message event published by the network message subsystem.
 */
public class SaveNavigatorSearchMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final String view = msg.readString();
        final String searchQuery = msg.readString();

        final SavedSearch savedSearch = new SavedSearch(view, searchQuery);

        if (!client.getPlayer().getNavigator().isSearchSaved(savedSearch)) {
            // Save the search
            final int searchId = PlayerDao.saveSearch(client.getPlayer().getId(), savedSearch);

            client.getPlayer().getNavigator().getSavedSearches().put(searchId, savedSearch);
            client.send(new NavigatorSavedSearchesMessageComposer(client.getPlayer().getNavigator().getSavedSearches()));
        }
    }
}
