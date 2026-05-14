package com.cometproject.server.network.messages.incoming.catalog.club;

import com.cometproject.api.game.furniture.IFurnitureService;
import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.server.composers.catalog.UnseenItemsMessageComposer;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.game.players.components.types.inventory.InventoryItem;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.items.ItemDao;
import com.cometproject.storage.api.StorageContext;
import com.cometproject.storage.api.data.Data;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Represents the get club present message event published by the network message subsystem.
 */
public class GetClubPresentMessageEvent
implements Event {
    /**
     * Executes handle for this network message contract.
     *
     * @param client Client supplied by the caller.
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void handle(Session client, MessageEvent msg) {
        if (client.getPlayer().getSubscription().getPresents() <= 0) {
            return;
        }
        String item = msg.readString();
        int id = ItemDao.getItemByName(item);
        FurnitureDefinition itemDefinition = ItemManager.getInstance().getDefinition(id);
        Player e = client.getPlayer();
        if (itemDefinition != null) {
            Data newItem = Data.createEmpty();
            StorageContext.getCurrentContext().getRoomItemRepository().createItem(e.getData().getId(), id, "", ((Data)newItem)::set);
            InventoryItem playerItem = new InventoryItem((Long)newItem.get(), id, "");
            client.getPlayer().getInventory().addItem((PlayerItem)playerItem);
            client.getPlayer().getSubscription().decrementPresents(client.getPlayer().getData().getId());
            e.getSession().send(new UpdateInventoryMessageComposer());
            e.getSession().send(new UnseenItemsMessageComposer((Set)Sets.newHashSet((Object[])new PlayerItem[]{playerItem}), (IFurnitureService)ItemManager.getInstance()));
        }
    }
}

