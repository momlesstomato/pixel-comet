package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.quests.IQuest;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the daily quest message for the Pixel Protocol client.
 */
public class DailyQuestMessageComposer extends MessageComposer {

    private final int campaignString;
    private final int campaignName;
    private final IPlayer player;
    private final IQuest quest;

    /**
     * Creates a daily quest message composer instance for the network message subsystem.
     *
     * @param campaignString Campaign string supplied by the caller.
     * @param campaignName Campaign name supplied by the caller.
     * @param quest Quest supplied by the caller.
     * @param player Player participating in the operation.
     */
    public DailyQuestMessageComposer(int campaignString, int campaignName, IQuest quest, IPlayer player) {
        this.campaignString = campaignString;
        this.campaignName = campaignName;
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
        return Composers.DailyQuestMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        this.quest.compose(player, msg);

        msg.writeInt(this.campaignString);
        msg.writeInt(this.campaignName);
    }
}
