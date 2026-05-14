package com.cometproject.server.network.messages.outgoing.room.trading;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the trade start message for the Pixel Protocol client.
 */
public class TradeStartMessageComposer extends MessageComposer {
    private final int user1;
    private final int user2;

    /**
     * Creates a trade start message composer instance for the network message subsystem.
     *
     * @param user1 User1 supplied by the caller.
     * @param user2 User2 supplied by the caller.
     */
    public TradeStartMessageComposer(int user1, int user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.TradingStartMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(user1);
        msg.writeInt(1);
        msg.writeInt(user2);
        msg.writeInt(1);
    }
}
