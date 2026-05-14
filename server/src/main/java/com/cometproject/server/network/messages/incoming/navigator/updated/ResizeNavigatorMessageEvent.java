package com.cometproject.server.network.messages.incoming.navigator.updated;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Represents the resize navigator message event published by the network message subsystem.
 */
public class ResizeNavigatorMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int x = msg.readInt();
        final int y = msg.readInt();
        final int width = msg.readInt();
        final int height = msg.readInt();
        final boolean savedSearchesVisible = msg.readBoolean();

        client.getPlayer().getSettings().setNavigatorX(x);
        client.getPlayer().getSettings().setNavigatorY(y);
        client.getPlayer().getSettings().setNavigatorHeight(height);
        client.getPlayer().getSettings().setNavigatorWidth(width);
        client.getPlayer().getSettings().setNavigatorShowSearches(savedSearchesVisible);

        PlayerDao.saveNavigatorSettings(x, y, height, width, savedSearchesVisible, client.getPlayer().getId());
    }
}
