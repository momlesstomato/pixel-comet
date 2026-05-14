package com.cometproject.server.network.messages.outgoing.gamecenter.snowwar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes user left game composer behavior for the network message subsystem.
 */
public class UserLeftGameComposer extends MessageComposer {
    private final int playerId;

    /**
     * Creates a user left game composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     */
    public UserLeftGameComposer(int playerId) {
        this.playerId = playerId;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        msg.writeInt(this.playerId);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    public short getId() {
        return Composers.SnowStormQueuePlayerRemovedComposer;
    }
}