package com.cometproject.server.network.messages.outgoing.room.bots;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the bot config message for the Pixel Protocol client.
 */
public class BotConfigMessageComposer extends MessageComposer {
    private final int botId;
    private final int skill;
    private final String message;

    /**
     * Creates a bot config message composer instance for the network message subsystem.
     *
     * @param botId Bot id supplied by the caller.
     * @param skill Skill supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public BotConfigMessageComposer(final int botId, final int skill, final String message) {
        this.botId = botId;
        this.skill = skill;
        this.message = message;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.OpenBotActionMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(botId);
        msg.writeInt(skill);
        msg.writeString(message);
    }
}
