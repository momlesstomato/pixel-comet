package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the muted message for the Pixel Protocol client.
 */
public class MutedMessageComposer extends MessageComposer {
    private final int secondsLeft;

    /**
     * Creates a muted message composer instance for the network message subsystem.
     *
     * @param secondsLeft Seconds left supplied by the caller.
     */
    public MutedMessageComposer(final int secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.MutedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(secondsLeft);
    }
}
