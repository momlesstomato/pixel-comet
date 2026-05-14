package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the room action message for the Pixel Protocol client.
 */
public class RoomActionMessageComposer extends MessageComposer {
    private final int action;

    /**
     * Creates a room action message composer instance for the network message subsystem.
     *
     * @param action Action supplied by the caller.
     */
    public RoomActionMessageComposer(int action) {
        this.action = action;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomActionMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(action);
    }
}
