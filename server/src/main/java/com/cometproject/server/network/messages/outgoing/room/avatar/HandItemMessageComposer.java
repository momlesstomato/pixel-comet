package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the hand item message for the Pixel Protocol client.
 */
public class HandItemMessageComposer extends MessageComposer {
    private final int avatarId;
    private final int handItemId;

    /**
     * Creates a hand item message composer instance for the network message subsystem.
     *
     * @param avatarId Avatar id supplied by the caller.
     * @param handItemId Hand item id supplied by the caller.
     */
    public HandItemMessageComposer(final int avatarId, final int handItemId) {
        this.avatarId = avatarId;
        this.handItemId = handItemId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CarryObjectMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(avatarId);
        msg.writeInt(handItemId);
    }
}
