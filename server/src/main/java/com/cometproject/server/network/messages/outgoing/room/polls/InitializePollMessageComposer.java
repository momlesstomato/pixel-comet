package com.cometproject.server.network.messages.outgoing.room.polls;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the initialize poll message for the Pixel Protocol client.
 */
public class InitializePollMessageComposer extends MessageComposer {

    private final int pollId;
    private final String headline;
    private final String thanksMessage;

    /**
     * Creates a initialize poll message composer instance for the network message subsystem.
     *
     * @param pollId Poll id supplied by the caller.
     * @param headline Headline supplied by the caller.
     * @param thanksMessage Thanks message supplied by the caller.
     */
    public InitializePollMessageComposer(int pollId, String headline, String thanksMessage) {
        this.pollId = pollId;
        this.headline = headline;
        this.thanksMessage = thanksMessage;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.InitializePollMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.pollId);

        msg.writeString(this.headline);
        msg.writeString(this.headline);
        msg.writeString(this.headline);
        msg.writeString(this.thanksMessage);
    }
}
