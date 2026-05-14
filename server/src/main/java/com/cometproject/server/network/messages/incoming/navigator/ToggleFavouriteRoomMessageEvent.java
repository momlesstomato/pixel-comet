package com.cometproject.server.network.messages.incoming.navigator;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.navigator.FavouriteRoomsMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Represents the toggle favourite room message event published by the network message subsystem.
 */
public class ToggleFavouriteRoomMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final int roomId = msg.readInt();

        if (client.getPlayer() == null || client.getPlayer().getNavigator() == null) {
            return;
        }

        if (client.getPlayer().getNavigator().getFavouriteRooms().contains(roomId)) {
            client.getPlayer().getNavigator().getFavouriteRooms().remove(roomId);
            PlayerDao.deleteFavouriteRoom(client.getPlayer().getId(), roomId);
        } else {
            client.getPlayer().getNavigator().getFavouriteRooms().add(roomId);
            PlayerDao.saveFavouriteRoom(client.getPlayer().getId(), roomId);
        }

        client.send(new FavouriteRoomsMessageComposer(client.getPlayer().getNavigator().getFavouriteRooms()));
    }
}
