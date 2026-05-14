package com.cometproject.server.network.messages.outgoing.room.pets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the pet package message for the Pixel Protocol client.
 */
public class PetPackageMessageComposer extends MessageComposer {

    private final int itemId;

    /**
     * Creates a pet package message composer instance for the network message subsystem.
     *
     * @param itemId Item id supplied by the caller.
     */
    public PetPackageMessageComposer(final int itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PetPackageMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.itemId);
    }
}
