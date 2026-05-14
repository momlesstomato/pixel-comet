package com.cometproject.server.network.messages.incoming.help;

import com.cometproject.server.game.moderation.ModerationManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.help.InitHelpToolMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the init help tool message event published by the network message subsystem.
 */
public class InitHelpToolMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        client.send(new InitHelpToolMessageComposer(ModerationManager.getInstance().getActiveTicketByPlayerId(client.getPlayer().getId())));
    }
}
