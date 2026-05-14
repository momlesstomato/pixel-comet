package com.cometproject.server.network.messages;

import com.cometproject.api.networking.messages.IMessageEvent;
import com.cometproject.networking.api.messages.IMessageHandler;
import com.cometproject.networking.api.sessions.INetSession;
import com.cometproject.server.network.sessions.net.NetSession;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Describes game message handler behavior for the network message subsystem.
 */
public class GameMessageHandler implements IMessageHandler {

    /**
     * Handles message for this network message contract.
     *
     * @param messageEvent Message event supplied by the caller.
     * @param session Session participating in the operation.
     */
    @Override
    public void handleMessage(IMessageEvent messageEvent, INetSession session) {
        if (!(session instanceof NetSession)) {
            return;
        }

        final NetSession netSession = (NetSession) session;
        netSession.getGameSession().handleMessageEvent((MessageEvent) messageEvent);
    }
}
