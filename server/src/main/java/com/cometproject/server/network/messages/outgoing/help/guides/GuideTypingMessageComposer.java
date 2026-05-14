package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide typing message for the Pixel Protocol client.
 */
public class GuideTypingMessageComposer extends MessageComposer {

    private final boolean typing;

    /**
     * Creates a guide typing message composer instance for the network message subsystem.
     *
     * @param t T supplied by the caller.
     */
    public GuideTypingMessageComposer(final boolean t) {
        this.typing = t;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GuideSessionPartnerIsTypingMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.typing);
    }
}
