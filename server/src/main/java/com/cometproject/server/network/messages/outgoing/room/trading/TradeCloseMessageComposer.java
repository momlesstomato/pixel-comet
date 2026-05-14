package com.cometproject.server.network.messages.outgoing.room.trading;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the trade close message for the Pixel Protocol client.
 */
public class TradeCloseMessageComposer extends MessageComposer {
    private final int playerId;

    /**
     * Creates a trade close message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     */
    public TradeCloseMessageComposer(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.TradingClosedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(2);
    }
}
