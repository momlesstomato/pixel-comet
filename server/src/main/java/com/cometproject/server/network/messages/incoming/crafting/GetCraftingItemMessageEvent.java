package com.cometproject.server.network.messages.incoming.crafting;

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.crafting.CraftableProductsMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the get crafting item message event published by the network message subsystem.
 */
public class GetCraftingItemMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int itemId = msg.readInt();

        client.getPlayer().getSession().send(new CraftableProductsMessageComposer(client.getPlayer().getLastCraftingMachine()));
    }
}
