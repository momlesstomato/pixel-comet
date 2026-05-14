package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the enforce room zoom message for the Pixel Protocol client.
 */
public class EnforceRoomZoomMessageComposer extends MessageComposer {
    private final int level;

    /**
     * Creates a enforce room zoom message composer instance for the network message subsystem.
     *
     * @param level Level supplied by the caller.
     */
    public EnforceRoomZoomMessageComposer(int level) {
        this.level = level;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.EnforceRoomZoomMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(level);
    }
}
