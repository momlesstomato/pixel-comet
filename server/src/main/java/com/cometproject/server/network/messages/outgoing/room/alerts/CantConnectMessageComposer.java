package com.cometproject.server.network.messages.outgoing.room.alerts;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the cant connect message for the Pixel Protocol client.
 */
public class CantConnectMessageComposer extends MessageComposer {
    private final int Error;

    /**
     * Creates a cant connect message composer instance for the network message subsystem.
     *
     * @param Error Error supplied by the caller.
     */
    public CantConnectMessageComposer(final int Error) {
        this.Error = Error;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomAccessErrorMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(Error);
    }
}
