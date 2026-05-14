package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the leave room message for the Pixel Protocol client.
 */
public class LeaveRoomMessageComposer extends MessageComposer {
    private final int playerId;

    /**
     * Creates a leave room message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     */
    public LeaveRoomMessageComposer(final int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UserRemoveMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(playerId);
    }
}
