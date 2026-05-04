package com.cometproject.server.network.messages.incoming.user.details;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


public class GetSoundSettingsMessageEvent implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        //client.send(new PlayerSettingsMessageComposer(client.getPlayer().getSettings(), 2));
    }
}
