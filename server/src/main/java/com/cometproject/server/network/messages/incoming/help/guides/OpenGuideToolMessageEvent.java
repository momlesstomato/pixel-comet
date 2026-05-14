package com.cometproject.server.network.messages.incoming.help.guides;

import com.cometproject.server.game.guides.GuideManager;
import com.cometproject.server.game.guides.types.HelperSession;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideToolsMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the open guide tool message event published by the network message subsystem.
 */
public class OpenGuideToolMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final boolean onDuty = msg.readBoolean();

        final boolean handleTourRequests = msg.readBoolean();
        final boolean handleHelpRequests = msg.readBoolean();
        final boolean handleBullyRequests = msg.readBoolean();

        if (!onDuty && client.getPlayer().getHelperSession() != null) {
            GuideManager.getInstance().finishPlayerDuty(client.getPlayer().getHelperSession());
            client.getPlayer().setHelperSession(null);
        } else if (onDuty) {
            final HelperSession helperSession = new HelperSession(client.getPlayer().getId(), handleTourRequests, handleHelpRequests, handleBullyRequests);

            client.getPlayer().setHelperSession(helperSession);
            GuideManager.getInstance().startPlayerDuty(helperSession);
        }

        client.send(new GuideToolsMessageComposer(onDuty));
    }
}
