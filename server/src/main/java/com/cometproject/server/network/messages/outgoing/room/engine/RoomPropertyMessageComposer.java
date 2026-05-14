package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the room property message for the Pixel Protocol client.
 */
public class RoomPropertyMessageComposer extends MessageComposer {
    private final String key;
    private final String value;

    /**
     * Creates a room property message composer instance for the network message subsystem.
     *
     * @param key Key supplied by the caller.
     * @param value Value supplied by the caller.
     */
    public RoomPropertyMessageComposer(final String key, final String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomPropertyMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.key);
        msg.writeString(this.value);
    }
}
