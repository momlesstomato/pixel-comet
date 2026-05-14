package com.cometproject.server.network.messages.outgoing.room.pets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the scratch pet notification message for the Pixel Protocol client.
 */
public class ScratchPetNotificationMessageComposer extends MessageComposer {

    private final PetEntity petEntity;

    /**
     * Creates a scratch pet notification message composer instance for the network message subsystem.
     *
     * @param petEntity Pet entity supplied by the caller.
     */
    public ScratchPetNotificationMessageComposer(PetEntity petEntity) {
        this.petEntity = petEntity;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RespectPetNotificationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.petEntity.getId());
        msg.writeInt(this.petEntity.getId());
        msg.writeInt(this.petEntity.getData().getId());

        msg.writeString(this.petEntity.getData().getName());
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeString(this.petEntity.getData().getColour());
        msg.writeInt(0);
        msg.writeInt(0);
        msg.writeInt(1);

    }
}
