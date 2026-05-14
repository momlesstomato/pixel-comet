package com.cometproject.server.composers.help;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the ticket sent message for the Pixel Protocol client.
 */
public class TicketSentMessageComposer extends MessageComposer {
    /**
     * Creates a ticket sent message composer instance for the protocol composer subsystem.
     */
    public TicketSentMessageComposer() {

    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
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
