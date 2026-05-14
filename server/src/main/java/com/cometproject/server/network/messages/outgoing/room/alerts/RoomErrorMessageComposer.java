package com.cometproject.server.network.messages.outgoing.room.alerts;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the room error message for the Pixel Protocol client.
 */
public class RoomErrorMessageComposer extends MessageComposer {
    private final int errorCode;

    /**
     * Creates a room error message composer instance for the network message subsystem.
     *
     * @param errorCode Error code supplied by the caller.
     */
    public RoomErrorMessageComposer(final int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GenericErrorMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.errorCode);
    }
}
