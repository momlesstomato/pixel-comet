package com.cometproject.server.composers.gamecenter;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the game status message for the Pixel Protocol client.
 */
public class GameStatusMessageComposer extends MessageComposer {

    private final int gameTypeId;
    private final int status;

    /**
     * Creates a game status message composer instance for the protocol composer subsystem.
     *
     * @param gameTypeId Game type id value supplied by the caller.
     * @param status Status value supplied by the caller.
     */
    public GameStatusMessageComposer(int gameTypeId, int status) {
        this.gameTypeId = gameTypeId;
        this.status = status;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.GameStatusMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(gameTypeId);
        msg.writeInt(status);
    }
}
