package com.cometproject.server.network.messages.outgoing.moderation.tickets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.moderation.types.tickets.HelpTicket;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the help ticket message for the Pixel Protocol client.
 */
public class HelpTicketMessageComposer extends MessageComposer {
    private final HelpTicket helpTicket;

    /**
     * Creates a help ticket message composer instance for the network message subsystem.
     *
     * @param helpTicket Help ticket supplied by the caller.
     */
    public HelpTicketMessageComposer(HelpTicket helpTicket) {
        this.helpTicket = helpTicket;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ModeratorSupportTicketMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        this.helpTicket.compose(msg);
    }
}
