package com.cometproject.server.network.messages.outgoing.help.guides;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the guide session ended message for the Pixel Protocol client.
 */
public class GuideSessionEndedMessageComposer extends MessageComposer {

    private final int code;

    /**
     * Creates a guide session ended message composer instance for the network message subsystem.
     *
     * @param c C supplied by the caller.
     */
    public GuideSessionEndedMessageComposer(final int c) {
        this.code = c;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GuideSessionEndedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.code);
    }
}
