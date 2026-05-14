package com.cometproject.server.network.messages.incoming.navigator.updated;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Represents the navigator save view mode message event published by the network message subsystem.
 */
public class NavigatorSaveViewModeMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final String category = msg.readString();
        final int viewMode = msg.readInt();

        client.getPlayer().getNavigator().getViewModes().put(category, viewMode);
        PlayerDao.saveViewMode(category, viewMode, client.getPlayer().getId());
    }
}
