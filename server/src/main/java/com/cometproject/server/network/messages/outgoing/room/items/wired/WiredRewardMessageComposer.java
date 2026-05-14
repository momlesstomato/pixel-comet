package com.cometproject.server.network.messages.outgoing.room.items.wired;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the wired reward message for the Pixel Protocol client.
 */
public class WiredRewardMessageComposer extends MessageComposer {
    private final int reason;

    /**
     * Creates a wired reward message composer instance for the network message subsystem.
     *
     * @param reason Reason supplied by the caller.
     */
    public WiredRewardMessageComposer(final int reason) {
        this.reason = reason;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.WiredRewardMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        // 1-5 = error
        // 6-7 = success (rewardMisc, rewardBadge)
        msg.writeInt(reason);
    }
}
