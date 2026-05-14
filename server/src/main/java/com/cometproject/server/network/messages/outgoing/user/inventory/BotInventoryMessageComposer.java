package com.cometproject.server.network.messages.outgoing.user.inventory;

import com.cometproject.api.game.bots.IBotData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the bot inventory message for the Pixel Protocol client.
 */
public class BotInventoryMessageComposer extends MessageComposer {

    private final Map<Integer, IBotData> bots;

    /**
     * Creates a bot inventory message composer instance for the network message subsystem.
     *
     * @param bots Bots supplied by the caller.
     */
    public BotInventoryMessageComposer(final Map<Integer, IBotData> bots) {
        this.bots = bots;
    }

    /**
     * Creates a bot inventory message composer instance for the network message subsystem.
     */
    public BotInventoryMessageComposer() {
        this.bots = null;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.BotInventoryMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if (this.bots == null) {
            msg.writeInt(0);
            return;
        }

        msg.writeInt(bots.size());

        for (Map.Entry<Integer, IBotData> bot : bots.entrySet()) {
            msg.writeInt(bot.getKey());
            msg.writeString(bot.getValue().getUsername());
            msg.writeString(bot.getValue().getMotto());
            msg.writeString(bot.getValue().getGender());
            msg.writeString(bot.getValue().getFigure());
        }
    }
}
