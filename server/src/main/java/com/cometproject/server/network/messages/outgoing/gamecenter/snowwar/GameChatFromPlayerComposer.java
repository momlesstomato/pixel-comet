package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes game chat from player composer behavior for the network message subsystem.
 */
public class GameChatFromPlayerComposer extends MessageComposer {
    private final int userId;
    private final String text;

    /**
     * Creates a game chat from player composer instance for the network message subsystem.
     *
     * @param userId User id supplied by the caller.
     * @param text Text supplied by the caller.
     */
    public GameChatFromPlayerComposer(int userId, String text) {
        this.userId = userId;
        this.text = text;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.userId);
        msg.writeString(this.text);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SnowStormUserChatMessageComposer;
    }
}