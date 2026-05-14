package com.cometproject.server.network.messages.outgoing.room.pets.breeding;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the pet breeding started message for the Pixel Protocol client.
 */
public class PetBreedingStartedMessageComposer extends MessageComposer {
    private final int itemId;
    private final int flag;

    /**
     * Creates a pet breeding started message composer instance for the network message subsystem.
     *
     * @param itemId Item id supplied by the caller.
     * @param flag Flag supplied by the caller.
     */
    public PetBreedingStartedMessageComposer(int itemId, int flag) {
        this.itemId = itemId;
        this.flag = flag;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PetBreedingStartedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.itemId);
        msg.writeInt(this.flag);
    }
}
