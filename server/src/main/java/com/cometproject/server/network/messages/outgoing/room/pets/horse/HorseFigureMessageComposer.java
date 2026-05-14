package com.cometproject.server.network.messages.outgoing.room.pets.horse;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the horse figure message for the Pixel Protocol client.
 */
public class HorseFigureMessageComposer extends MessageComposer {

    private final PetEntity petEntity;

    /**
     * Creates a horse figure message composer instance for the network message subsystem.
     *
     * @param petEntity Pet entity supplied by the caller.
     */
    public HorseFigureMessageComposer(final PetEntity petEntity) {
        this.petEntity = petEntity;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PetHorseFigureInformationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.petEntity.getId());
        msg.writeInt(this.petEntity.getData().getId());
        msg.writeInt(this.petEntity.getData().getTypeId());
        msg.writeInt(this.petEntity.getData().getRaceId());
        msg.writeString(this.petEntity.getData().getColour());

        if (this.petEntity.getData().isSaddled()) {
            msg.writeInt(4);
            msg.writeInt(3);
            msg.writeInt(3);

            msg.writeInt(this.petEntity.getData().getHair());
            msg.writeInt(this.petEntity.getData().getHairDye());
            msg.writeInt(2);
            msg.writeInt(this.petEntity.getData().getHair());
            msg.writeInt(this.petEntity.getData().getHairDye());
            msg.writeInt(4);
            msg.writeInt(9);
            msg.writeInt(0);
        } else {
            msg.writeInt(1);
            msg.writeInt(2);
            msg.writeInt(2);

            msg.writeInt(this.petEntity.getData().getHair());
            msg.writeInt(this.petEntity.getData().getHairDye());
            msg.writeInt(3);
            msg.writeInt(this.petEntity.getData().getHair());
            msg.writeInt(this.petEntity.getData().getHairDye());
        }

        msg.writeBoolean(this.petEntity.getData().isSaddled());
        msg.writeBoolean(this.petEntity.getMountedEntity() != null);
    }
}
