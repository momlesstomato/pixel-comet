package com.cometproject.server.network.messages.incoming.user.inventory;

import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;


/**
 * Represents the open inventory message event published by the network message subsystem.
 */
public class OpenInventoryMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void handle(Session client, MessageEvent msg) {
        PlayerManager.getInstance().getPlayerLoadExecutionService().submit(() -> {
            if (!client.getPlayer().getInventory().itemsLoaded()) {
                client.getPlayer().getInventory().loadItems(0);
            }

            client.getPlayer().getInventory().send();
//            (new InventoryMessageComposer(client.getPlayer().getInventory()));
        });
    }
}
