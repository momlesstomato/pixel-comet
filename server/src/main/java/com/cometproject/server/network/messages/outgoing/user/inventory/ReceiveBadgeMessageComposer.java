package com.cometproject.server.network.messages.outgoing.user.inventory;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the receive badge message for the Pixel Protocol client.
 */
public class ReceiveBadgeMessageComposer extends MessageComposer {

    private final int index;
    private final String badgeId;

    /**
     * Creates a receive badge message composer instance for the network message subsystem.
     *
     * @param index Index supplied by the caller.
     * @param badgeId Badge id supplied by the caller.
     */
    public ReceiveBadgeMessageComposer(int index, String badgeId) {
        this.index = index;
        this.badgeId = badgeId;
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
        msg.writeInt(index); // fk knows
        msg.writeString(badgeId);
    }
}
