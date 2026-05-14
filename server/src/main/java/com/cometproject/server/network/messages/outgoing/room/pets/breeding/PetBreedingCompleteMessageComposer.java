package com.cometproject.server.network.messages.outgoing.room.pets.breeding;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the pet breeding complete message for the Pixel Protocol client.
 */
public class PetBreedingCompleteMessageComposer extends MessageComposer {

    private final int babyPetId;
    private final int rarityLevel;

    /**
     * Creates a pet breeding complete message composer instance for the network message subsystem.
     *
     * @param babyPetId Baby pet id supplied by the caller.
     * @param rarityLevel Rarity level supplied by the caller.
     */
    public PetBreedingCompleteMessageComposer(final int babyPetId, final int rarityLevel) {
        this.babyPetId = babyPetId;
        this.rarityLevel = rarityLevel;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PetBreedingCompleteMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.babyPetId);
        msg.writeInt(this.rarityLevel);
    }
}
