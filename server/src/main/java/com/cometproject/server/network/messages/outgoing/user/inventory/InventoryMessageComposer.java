package com.cometproject.server.network.messages.outgoing.user.inventory;

import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the inventory message for the Pixel Protocol client.
 */
public class InventoryMessageComposer extends MessageComposer {
    public static final int ITEMS_PER_PAGE = 2000;

    private final int pageCount;
    private final int currentPage;
    private final Map<Long, PlayerItem> inventoryItems;

    /**
     * Creates a inventory message composer instance for the network message subsystem.
     *
     * @param pageCount Page count supplied by the caller.
     * @param currentPage Current page supplied by the caller.
     * @param inventoryItems Inventory items supplied by the caller.
     */
    public InventoryMessageComposer(int pageCount, int currentPage, Map<Long, PlayerItem> inventoryItems) {
        this.pageCount = pageCount;
        this.currentPage = currentPage;
        this.inventoryItems = inventoryItems;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FurniListMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.pageCount); // how many pages
        msg.writeInt(this.currentPage); // index of instance page
        msg.writeInt(this.inventoryItems.size());

        for (Map.Entry<Long, PlayerItem> inventoryItem : this.inventoryItems.entrySet()) {
            inventoryItem.getValue().compose(msg);
        }
    }
}
