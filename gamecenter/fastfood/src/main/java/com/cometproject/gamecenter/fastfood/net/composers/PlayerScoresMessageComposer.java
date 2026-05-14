package com.cometproject.gamecenter.fastfood.net.composers;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the player scores message for the Pixel Protocol client.
 */
public class PlayerScoresMessageComposer extends MessageComposer {

    private final int gameSession;

    /**
     * Creates a player scores message composer instance for the protocol composer subsystem.
     *
     * @param gameSession Game session supplied by the caller.
     */
    public PlayerScoresMessageComposer(final int gameSession) {
        this.gameSession = gameSession;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return 18;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.gameSession);
        msg.writeInt(1);
    }
}
