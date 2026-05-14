package com.cometproject.server.network.messages.incoming.room.trading;

import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.rooms.types.components.types.Trade;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

/**
 * Represents the trading offer items message event published by the network message subsystem.
 */
public class TradingOfferItemsMessageEvent implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int amount = msg.readInt();

        if (amount > 100) {
            amount = 100;
        }

        final long itemId = ItemManager.getInstance().getItemIdByVirtualId(msg.readInt());

        PlayerItem item = client.getPlayer().getInventory().getItem(itemId);
        Trade trade = client.getPlayer().getEntity().getRoom().getTrade().get(client.getPlayer().getEntity());
        if (trade == null) return;

        int i = 0;

        for (PlayerItem playerItem : client.getPlayer().getInventory().getInventoryItems().values()) {
            if (i >= amount)
                break;

            if (playerItem.getBaseId() == item.getBaseId() && !trade.isOffered(playerItem)) {
                i++;

                trade.addItem(trade.getUserNumber(client.getPlayer().getEntity()), playerItem, false);
            }
        }

        trade.updateWindow();
    }
}
