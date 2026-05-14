package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the typing status message for the Pixel Protocol client.
 */
public class TypingStatusMessageComposer extends MessageComposer {
    private final int playerId;
    private final int status;

    /**
     * Creates a typing status message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param status Status supplied by the caller.
     */
    public TypingStatusMessageComposer(final int playerId, final int status) {
        this.playerId = playerId;
        this.status = status;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserTypingMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(status);
    }
}
