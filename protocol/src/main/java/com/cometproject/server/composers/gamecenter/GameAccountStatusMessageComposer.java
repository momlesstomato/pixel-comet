package com.cometproject.server.composers.gamecenter;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the game account status message for the Pixel Protocol client.
 */
public class GameAccountStatusMessageComposer extends MessageComposer {

    private final int gameId;

    /**
     * Creates a game account status message composer instance for the protocol composer subsystem.
     *
     * @param gameId Game id value supplied by the caller.
     */
    public GameAccountStatusMessageComposer(int gameId) {
        this.gameId = gameId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.GameAccountStatusMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.gameId);
        msg.writeInt(10);// can play = -1
        msg.writeInt(1);
    }
}
