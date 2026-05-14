package com.cometproject.gamecenter.fastfood.net.composers;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the use big parachute message for the Pixel Protocol client.
 */
public class UseBigParachuteMessageComposer extends MessageComposer {
    private final int playerId;
    private final int objectId;

    /**
     * Creates a use big parachute message composer instance for the protocol composer subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param objectId Object id supplied by the caller.
     */
    public UseBigParachuteMessageComposer(int playerId, int objectId) {
        this.playerId = playerId;
        this.objectId = objectId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return 9;
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
    }
}
