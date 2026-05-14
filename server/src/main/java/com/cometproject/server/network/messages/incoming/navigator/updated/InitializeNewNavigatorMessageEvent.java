package com.cometproject.server.network.messages.incoming.navigator.updated;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.navigator.NavigatorMetaDataMessageComposer;
import com.cometproject.server.network.messages.outgoing.navigator.updated.NavigatorCollapsedCategoriesMessageComposer;
import com.cometproject.server.network.messages.outgoing.navigator.updated.NavigatorLiftedRoomsMessageComposer;
import com.cometproject.server.network.messages.outgoing.navigator.updated.NavigatorPreferencesMessageComposer;
import com.cometproject.server.network.messages.outgoing.navigator.updated.NavigatorSavedSearchesMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the initialize new navigator message event published by the network message subsystem.
 */
public class InitializeNewNavigatorMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        client.sendQueue(new NavigatorPreferencesMessageComposer(client.getPlayer().getSettings()))
                .sendQueue(new NavigatorMetaDataMessageComposer())
                .sendQueue(new NavigatorLiftedRoomsMessageComposer())
                .sendQueue(new NavigatorSavedSearchesMessageComposer(client.getPlayer().getNavigator().getSavedSearches()))
                .sendQueue(new NavigatorCollapsedCategoriesMessageComposer());

        client.flush();
    }
}
