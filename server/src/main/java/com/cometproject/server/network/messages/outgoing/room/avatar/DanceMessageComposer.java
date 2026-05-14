package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the dance message for the Pixel Protocol client.
 */
public class DanceMessageComposer extends MessageComposer {
    private final int playerId;
    private final int danceId;

    /**
     * Creates a dance message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param danceId Dance id supplied by the caller.
     */
    public DanceMessageComposer(final int playerId, final int danceId) {
        this.playerId = playerId;
        this.danceId = danceId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.DanceMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeInt(danceId);
    }
}
