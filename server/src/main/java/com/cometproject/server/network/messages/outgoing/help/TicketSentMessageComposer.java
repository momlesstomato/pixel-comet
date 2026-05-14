package com.cometproject.server.network.messages.outgoing.help;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the ticket sent message for the Pixel Protocol client.
 */
public class TicketSentMessageComposer extends MessageComposer {
    /**
     * Creates a ticket sent message composer instance for the network message subsystem.
     */
    public TicketSentMessageComposer() {

    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return 0;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(0);
        msg.writeInt(0);
    }
}
