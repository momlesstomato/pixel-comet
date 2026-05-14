package com.cometproject.server.network.messages.outgoing.room.pets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes pet update status composer behavior for the network message subsystem.
 */
public class PetUpdateStatusComposer extends MessageComposer {

    private PetEntity entity;

    /**
     * Creates a pet update status composer instance for the network message subsystem.
     *
     * @param entity Entity supplied by the caller.
     */
    public PetUpdateStatusComposer(PetEntity entity) {
        this.entity = entity;
    }


    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        this.entity.composeUpdate(msg);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PetUpdateStatusComposer;
    }
}