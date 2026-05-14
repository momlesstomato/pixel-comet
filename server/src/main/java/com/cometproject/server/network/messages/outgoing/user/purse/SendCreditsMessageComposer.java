package com.cometproject.server.network.messages.outgoing.user.purse;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the send credits message for the Pixel Protocol client.
 */
public class SendCreditsMessageComposer extends MessageComposer {
    private final String credits;

    /**
     * Creates a send credits message composer instance for the network message subsystem.
     *
     * @param credits Credits supplied by the caller.
     */
    public SendCreditsMessageComposer(final String credits) {
        this.credits = credits;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CreditBalanceMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString((credits + ".0"));
    }
}
