package com.cometproject.server.network.messages.outgoing.room.trading;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the trade error message for the Pixel Protocol client.
 */
public class TradeErrorMessageComposer extends MessageComposer {
    private final int errorCode;
    private final String extras;

    /**
     * Creates a trade error message composer instance for the network message subsystem.
     *
     * @param errorCode Error code supplied by the caller.
     * @param extras Extras supplied by the caller.
     */
    public TradeErrorMessageComposer(int errorCode, String extras) {
        this.errorCode = errorCode;
        this.extras = extras;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.TradingErrorMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(errorCode);
        msg.writeString(extras);
    }
}
