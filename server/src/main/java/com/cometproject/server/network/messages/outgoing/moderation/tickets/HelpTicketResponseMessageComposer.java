package com.cometproject.server.network.messages.outgoing.moderation.tickets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the help ticket response message for the Pixel Protocol client.
 */
public class HelpTicketResponseMessageComposer extends MessageComposer {

    private int response;

    /**
     * Creates a help ticket response message composer instance for the network message subsystem.
     *
     * @param response Response supplied by the caller.
     */
    public HelpTicketResponseMessageComposer(final int response) {
        this.response = response;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ModeratorSupportTicketResponseMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.response);
        msg.writeString("");
    }
}
