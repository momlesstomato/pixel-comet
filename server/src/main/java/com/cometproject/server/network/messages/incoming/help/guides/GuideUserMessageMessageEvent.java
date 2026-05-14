package com.cometproject.server.network.messages.incoming.help.guides;

import com.cometproject.api.config.CometSettings;
import com.cometproject.server.game.guides.types.HelpRequest;
import com.cometproject.server.logging.LogManager;
import com.cometproject.server.logging.entries.AlfaLogEntry;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionErrorMessageComposer;
import com.cometproject.server.network.messages.outgoing.help.guides.GuideSessionMessageMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the guide user message message event published by the network message subsystem.
 */
public class GuideUserMessageMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        final HelpRequest helpRequest = client.getPlayer().getHelpRequest();
        final String message = msg.readString();


        if(helpRequest != null)
        {
            if(helpRequest.getGuideSession() == null || helpRequest.getPlayerSession() == null)
            {
                client.send(new GuideSessionErrorMessageComposer(0));
                client.getPlayer().setHelpRequest(null);
                return;
            }

            helpRequest.getGuideSession().send(new GuideSessionMessageMessageComposer(message, client.getPlayer().getData().getId()));
            helpRequest.getPlayerSession().send(new GuideSessionMessageMessageComposer(message, client.getPlayer().getData().getId()));
            try {
                if (LogManager.ENABLED && CometSettings.messengerLogMessages)
                    LogManager.getInstance().getStore().getLogEntryContainer().put(new AlfaLogEntry(client.getPlayer().getId(), client.getPlayer().getId(), message));
            } catch (Exception ignored) {

            }
        } else {
            client.send(new GuideSessionErrorMessageComposer(0));
            client.getPlayer().setHelpRequest(null);
        }
    }
}
