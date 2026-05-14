package com.cometproject.server.network.messages.incoming.help;

import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.moderation.types.tickets.HelpTicket;
import com.cometproject.server.game.moderation.types.tickets.HelpTicketState;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.help.InitHelpToolMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the delete pending ticket message event published by the network message subsystem.
 */
public class DeletePendingTicketMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final HelpTicket helpTicket = ModerationManager.getInstance().getActiveTicketByPlayerId(client.getPlayer().getId());

        if (helpTicket != null) {
            helpTicket.setState(HelpTicketState.CLOSED);
            helpTicket.save();

            ModerationManager.getInstance().broadcastTicket(helpTicket);

            ModerationManager.getInstance().getTickets().remove(helpTicket.getId());
            client.send(new InitHelpToolMessageComposer(null));
        }
    }
}
