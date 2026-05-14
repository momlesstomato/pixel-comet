package com.cometproject.server.network.messages.outgoing.quests;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.quests.IQuest;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the quest completed message for the Pixel Protocol client.
 */
public class QuestCompletedMessageComposer extends MessageComposer {
    private IQuest quest;
    private IPlayer player;

    /**
     * Creates a quest completed message composer instance for the network message subsystem.
     *
     * @param quest Quest supplied by the caller.
     * @param player Player participating in the operation.
     */
    public QuestCompletedMessageComposer(IQuest quest, IPlayer player) {
        this.quest = quest;
        this.player = player;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.QuestCompletedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        quest.compose(this.player, msg);
        msg.writeBoolean(this.player.getQuests().hasCompletedQuest(this.quest.getId()));
    }
}
