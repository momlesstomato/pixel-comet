package com.cometproject.server.network.messages.incoming.room.action;

import com.cometproject.api.game.quests.QuestType;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.room.avatar.DanceMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the apply dance message event published by the network message subsystem.
 */
public class ApplyDanceMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        if(client.getPlayer().antiSpam(getClass().getName(), 0.5))
            return;

        int danceId = msg.readInt();

        if (client.getPlayer().getEntity().getDanceId() == danceId) {
            return;
        }

        client.getPlayer().getEntity().unIdle();

        if (!client.getPlayer().getEntity().isVisible()) {
            return;
        }

        client.getPlayer().getEntity().setDanceId(danceId);
        client.getPlayer().getEntity().getRoom().getEntities().broadcastMessage(new DanceMessageComposer(client.getPlayer().getEntity().getId(), danceId));

        client.getPlayer().getQuests().progressQuest(QuestType.SOCIAL_DANCE);
    }
}
