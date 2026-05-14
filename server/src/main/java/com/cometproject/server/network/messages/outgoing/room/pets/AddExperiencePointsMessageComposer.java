package com.cometproject.server.network.messages.outgoing.room.pets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the add experience points message for the Pixel Protocol client.
 */
public class AddExperiencePointsMessageComposer extends MessageComposer {

    private final int petId;
    private final int virtualId;
    private final int amount;

    /**
     * Creates a add experience points message composer instance for the network message subsystem.
     *
     * @param petId Pet id supplied by the caller.
     * @param virtualId Virtual id supplied by the caller.
     * @param amount Amount supplied by the caller.
     */
    public AddExperiencePointsMessageComposer(int petId, int virtualId, int amount) {
        this.petId = petId;
        this.virtualId = virtualId;
        this.amount = amount;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.AddExperiencePointsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.petId);
        msg.writeInt(this.virtualId);
        msg.writeInt(this.amount);
    }
}
