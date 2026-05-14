package com.cometproject.server.network.messages.outgoing.room.settings;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the room rating message for the Pixel Protocol client.
 */
public class RoomRatingMessageComposer extends MessageComposer {
    private final int score;
    private final boolean canRate;

    /**
     * Creates a room rating message composer instance for the network message subsystem.
     *
     * @param score Score supplied by the caller.
     * @param canRate Can rate supplied by the caller.
     */
    public RoomRatingMessageComposer(int score, boolean canRate) {
        this.score = score;
        this.canRate = canRate;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomRatingMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.score);
        msg.writeBoolean(this.canRate);
    }
}
