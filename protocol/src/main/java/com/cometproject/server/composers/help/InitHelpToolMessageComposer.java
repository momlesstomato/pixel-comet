package com.cometproject.server.composers.help;

import com.cometproject.api.game.moderation.IHelpTicket;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the init help tool message for the Pixel Protocol client.
 */
public class InitHelpToolMessageComposer extends MessageComposer {
    private final IHelpTicket helpTicket;

    /**
     * Creates a init help tool message composer instance for the protocol composer subsystem.
     *
     * @param helpTicket Help ticket value supplied by the caller.
     */
    public InitHelpToolMessageComposer(IHelpTicket helpTicket) {
        this.helpTicket = helpTicket;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
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
