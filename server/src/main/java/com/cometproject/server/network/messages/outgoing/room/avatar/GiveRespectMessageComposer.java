package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the give respect message for the Pixel Protocol client.
 */
public class GiveRespectMessageComposer extends MessageComposer {
    private final int playerId;
    private final int totalRespects;

    /**
     * Creates a give respect message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param totalRespects Total respects supplied by the caller.
     */
    public GiveRespectMessageComposer(final int playerId, final int totalRespects) {
        this.playerId = playerId;
        this.totalRespects = totalRespects;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RespectNotificationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(totalRespects);
    }
}
