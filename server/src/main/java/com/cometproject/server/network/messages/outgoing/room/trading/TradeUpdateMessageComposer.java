package com.cometproject.server.network.messages.outgoing.room.trading;

import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.components.types.inventory.InventoryItem;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Set;


/**
 * Serializes the trade update message for the Pixel Protocol client.
 */
public class TradeUpdateMessageComposer extends MessageComposer {

    private final int user1;
    private final int user2;
    private final Set<PlayerItem> items1;
    private final Set<PlayerItem> items2;

    /**
     * Creates a trade update message composer instance for the network message subsystem.
     *
     * @param user1 User1 supplied by the caller.
     * @param user2 User2 supplied by the caller.
     * @param items1 Items1 supplied by the caller.
     * @param items2 Items2 supplied by the caller.
     */
    public TradeUpdateMessageComposer(int user1, int user2, Set<PlayerItem> items1, Set<PlayerItem> items2) {
        this.user1 = user1;
        this.user2 = user2;
        this.items1 = items1;
        this.items2 = items2;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.TradingUpdateMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(user1);
        msg.writeInt(items1.size());

        for (PlayerItem item : items1) {
            ((InventoryItem) item).serializeTrade(msg);
        }

        msg.writeInt(items1.size());
        msg.writeInt(0);

        msg.writeInt(user2);
        msg.writeInt(items2.size());

        for (PlayerItem item : items2) {
            ((InventoryItem) item).serializeTrade(msg);
        }

        msg.writeInt(items2.size());
        msg.writeInt(0);
    }
}
