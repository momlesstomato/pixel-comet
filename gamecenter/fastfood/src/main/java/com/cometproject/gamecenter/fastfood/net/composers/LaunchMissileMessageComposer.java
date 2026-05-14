package com.cometproject.gamecenter.fastfood.net.composers;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the launch missile message for the Pixel Protocol client.
 */
public class LaunchMissileMessageComposer extends MessageComposer {
    private final int playerId;
    private final int targetId;

    /**
     * Creates a launch missile message composer instance for the protocol composer subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param targetId Target id supplied by the caller.
     */
    public LaunchMissileMessageComposer(int playerId, int targetId) {
        this.playerId = playerId;
        this.targetId = targetId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return 10;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerId);
        msg.writeInt(this.targetId);
    }
}
