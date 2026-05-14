package com.cometproject.server.network.messages.incoming.room.action;

import com.cometproject.api.game.rooms.entities.RoomEntityStatus;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the sit message event published by the network message subsystem.
 */
public class SitMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() != null) {
            PlayerEntity playerEntity = client.getPlayer().getEntity();

            if (!client.getPlayer().getEntity().isVisible()) {
                return;
            }

            if (!playerEntity.hasStatus(RoomEntityStatus.SIT)) {
                playerEntity.sit(0.5, playerEntity.getBodyRotation());
            }
        }
    }
}
