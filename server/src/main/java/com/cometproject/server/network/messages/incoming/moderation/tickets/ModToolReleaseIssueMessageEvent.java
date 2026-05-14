package com.cometproject.server.network.messages.incoming.moderation.tickets;

import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.game.moderation.types.tickets.HelpTicket;
import com.cometproject.server.game.moderation.types.tickets.HelpTicketState;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the mod tool release issue message event published by the network message subsystem.
 */
public class ModToolReleaseIssueMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int ticketCount = msg.readInt();
        if (!client.getPlayer().getPermissions().getRank().modTool()) {
            client.disconnect();
            return;
        }

        for (int i = 0; i < ticketCount; i++) {
            int ticketId = msg.readInt();

            final HelpTicket helpTicket = ModerationManager.getInstance().getTicket(ticketId);

            if (helpTicket == null || helpTicket.getModeratorId() != client.getPlayer().getId()) return;

            helpTicket.setState(HelpTicketState.OPEN);
            helpTicket.setModeratorId(0);

            helpTicket.save();

            ModerationManager.getInstance().broadcastTicket(helpTicket);
        }
    }
}
