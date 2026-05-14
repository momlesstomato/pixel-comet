package com.cometproject.server.network.messages.incoming.quests;

import com.cometproject.api.game.quests.IQuest;
import com.cometproject.server.game.quests.QuestManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the start quest message event published by the network message subsystem.
 */
public class StartQuestMessageEvent implements com.cometproject.server.network.messages.incoming.Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int questId = msg.readInt();

        if (client.getPlayer().getQuests().hasStartedQuest(questId)) {
            // Already started it!
            return;
        }

        if (client.getPlayer().getData().getQuestId() != 0) {
            // We need to cancel their instance one.
            if (!client.getPlayer().getQuests().hasCompletedQuest(client.getPlayer().getData().getQuestId())) {
                client.getPlayer().getQuests().cancelQuest(client.getPlayer().getData().getQuestId());
            }
        }

        final IQuest quest = QuestManager.getInstance().getById(questId);

        if (quest == null) {
            return;
        }

        client.getPlayer().getQuests().startQuest(quest);
    }
}
