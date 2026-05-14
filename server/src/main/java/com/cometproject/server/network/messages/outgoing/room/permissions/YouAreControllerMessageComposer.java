package com.cometproject.server.network.messages.outgoing.room.permissions;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the you are controller message for the Pixel Protocol client.
 */
public class YouAreControllerMessageComposer extends MessageComposer {
    private final int rightId;

    /**
     * Creates a you are controller message composer instance for the network message subsystem.
     *
     * @param rightId Right id supplied by the caller.
     */
    public YouAreControllerMessageComposer(int rightId) {
        this.rightId = rightId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.YouAreControllerMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(rightId);
    }
}
