package com.cometproject.gamecenter.fastfood.net.composers;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the apply shield message for the Pixel Protocol client.
 */
public class ApplyShieldMessageComposer extends MessageComposer {
    private final int playerId;
    private final int objectId;
    private final boolean flush;

    /**
     * Creates a apply shield message composer instance for the protocol composer subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param objectId Object id supplied by the caller.
     * @param flush Flush supplied by the caller.
     */
    public ApplyShieldMessageComposer(int playerId, int objectId, boolean flush) {
        this.playerId = playerId;
        this.objectId = objectId;
        this.flush = flush;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return 12;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.objectId);
        msg.writeInt(this.playerId);
        msg.writeBoolean(this.flush);
    }
}
