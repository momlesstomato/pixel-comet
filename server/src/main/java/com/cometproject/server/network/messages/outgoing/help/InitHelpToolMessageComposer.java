package com.cometproject.server.network.messages.outgoing.help;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.moderation.types.tickets.HelpTicket;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the init help tool message for the Pixel Protocol client.
 */
public class InitHelpToolMessageComposer extends MessageComposer {
    private final HelpTicket helpTicket;

    /**
     * Creates a init help tool message composer instance for the network message subsystem.
     *
     * @param helpTicket Help ticket supplied by the caller.
     */
    public InitHelpToolMessageComposer(HelpTicket helpTicket) {
        this.helpTicket = helpTicket;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CallForHelpPendingCallsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.helpTicket == null ? 0 : 1);

        if (this.helpTicket != null) {
            msg.writeString(this.helpTicket.getId());
            msg.writeString(this.helpTicket.getDateSubmitted());
            msg.writeString(this.helpTicket.getMessage());
        }
    }
}
