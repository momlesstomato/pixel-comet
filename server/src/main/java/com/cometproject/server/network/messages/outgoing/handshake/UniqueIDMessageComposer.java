package com.cometproject.server.network.messages.outgoing.handshake;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the unique id message for the Pixel Protocol client.
 */
public class UniqueIDMessageComposer extends MessageComposer {
    private final String uniqueId;

    /**
     * Creates a unique id message composer instance for the network message subsystem.
     *
     * @param uniqueId Unique id supplied by the caller.
     */
    public UniqueIDMessageComposer(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UniqueMachineIDMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.uniqueId);
    }
}
