package com.cometproject.server.network.messages.outgoing.room.permissions;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the flood filter message for the Pixel Protocol client.
 */
public class FloodFilterMessageComposer extends MessageComposer {
    private final double seconds;

    /**
     * Creates a flood filter message composer instance for the network message subsystem.
     *
     * @param seconds Seconds supplied by the caller.
     */
    public FloodFilterMessageComposer(double seconds) {
        this.seconds = seconds;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FloodControlMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(((int) Math.round(this.seconds)));
    }
}
