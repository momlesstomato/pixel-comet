package com.cometproject.server.composers.handshake;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the unique id message for the Pixel Protocol client.
 */
public class UniqueIdMessageComposer extends MessageComposer {
    private final String uniqueId;

    /**
     * Creates a unique id message composer instance for the protocol composer subsystem.
     *
     * @param uniqueId Unique id value supplied by the caller.
     */
    public UniqueIdMessageComposer(final String uniqueId) {
        this.uniqueId = uniqueId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
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
