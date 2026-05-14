package com.cometproject.server.network.messages.incoming.help.guides;

import com.cometproject.server.game.guides.types.HelpRequest;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionCoreMessageComposer;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionDetachedMessageComposer;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionErrorMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the guide handle help request message event published by the network message subsystem.
 */
public class GuideHandleHelpRequestMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        boolean valid = msg.readBoolean();
        final HelpRequest helpRequest = client.getPlayer().getHelpRequest();

        if (helpRequest == null) {
            client.send(new GuideSessionErrorMessageComposer(0));
            client.getPlayer().setHelpRequest(null);
            return;
        }

        if (valid) {
            helpRequest.setIsBEGIN();
            helpRequest.setGuide(client.getPlayer().getId());
            helpRequest.getPlayerSession().send(new GuideSessionCoreMessageComposer(helpRequest));
            client.send(new GuideSessionCoreMessageComposer(helpRequest));
        } else {
            helpRequest.decline(client.getPlayer().getId());
            helpRequest.setGuide(0);
            client.send(new GuideSessionDetachedMessageComposer());
        }
    }
}
