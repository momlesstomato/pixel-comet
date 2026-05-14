package com.cometproject.server.network.messages.incoming.room.pets.monsterplants;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.avatar.WhisperMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the monster plant breed message event published by the network message subsystem.
 */
public class MonsterPlantBreedMessageEvent implements Event {
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

        client.send(new WhisperMessageComposer(-1, "Los cruces entre plantas todavía no están disponibles en HTHOR. En una próxima update estarán disponibles.", 34));
    }
}
