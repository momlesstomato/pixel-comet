package com.cometproject.server.network.messages.incoming.user.details;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.player.PlayerDao;


/**
 * Represents the update chat style message event published by the network message subsystem.
 */
public class UpdateChatStyleMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        boolean useOldChat = msg.readBoolean();

        if (client.getPlayer() == null) {
            return;
        }

        client.getPlayer().getSettings().setUseOldChat(useOldChat);
        PlayerDao.saveChatStyle(useOldChat, client.getPlayer().getId());
    }
}
