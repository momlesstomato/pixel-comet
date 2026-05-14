package com.cometproject.server.network.messages.outgoing.room.trading;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the trade accept update message for the Pixel Protocol client.
 */
public class TradeAcceptUpdateMessageComposer extends MessageComposer {
    private final int playerId;
    private final boolean accepted;

    /**
     * Creates a trade accept update message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param accepted Accepted supplied by the caller.
     */
    public TradeAcceptUpdateMessageComposer(int playerId, boolean accepted) {
        this.playerId = playerId;
        this.accepted = accepted;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.TradingAcceptMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(accepted ? 1 : 0);
    }
}
