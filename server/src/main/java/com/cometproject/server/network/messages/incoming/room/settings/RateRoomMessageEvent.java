package com.cometproject.server.network.messages.incoming.room.settings;

import com.cometproject.api.game.GameContext;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the rate room message event published by the network message subsystem.
 */
public class RateRoomMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        if (client.getPlayer().getEntity() == null) {
            return;
        }

        Room room = client.getPlayer().getEntity().getRoom();

        if (!client.getPlayer().getEntity().canRateRoom()) {
            return;
        }

        room.getRatings().add(client.getPlayer().getId());

        int direction = msg.readInt();
        int score = room.getData().getScore();

        if (direction == 1)
            score++;
        else
            score--;

        room.getData().setScore(score);
        room.getEntities().refreshScore();

        GameContext.getCurrent().getRoomService().saveRoomData(room.getData());
    }
}
