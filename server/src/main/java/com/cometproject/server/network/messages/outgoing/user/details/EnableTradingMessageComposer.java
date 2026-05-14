package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the enable trading message for the Pixel Protocol client.
 */
public class EnableTradingMessageComposer extends MessageComposer {

    private final boolean tradingEnabled;

    /**
     * Creates a enable trading message composer instance for the network message subsystem.
     *
     * @param tradingEnabled Trading enabled supplied by the caller.
     */
    public EnableTradingMessageComposer(final boolean tradingEnabled) {
        this.tradingEnabled = tradingEnabled;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return 0;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.tradingEnabled);
    }
}
