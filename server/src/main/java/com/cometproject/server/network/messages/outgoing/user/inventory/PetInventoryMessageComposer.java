package com.cometproject.server.network.messages.outgoing.user.inventory;

import com.cometproject.api.game.pets.IPetData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.pets.data.PetMonsterPlantData;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Map;


/**
 * Serializes the pet inventory message for the Pixel Protocol client.
 */
public class PetInventoryMessageComposer extends MessageComposer {
    private final Map<Integer, IPetData> pets;

    /**
     * Creates a pet inventory message composer instance for the network message subsystem.
     *
     * @param pets Pets supplied by the caller.
     */
    public PetInventoryMessageComposer(final Map<Integer, IPetData> pets) {
        this.pets = pets;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PetInventoryMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(1);
        msg.writeInt(1);

        msg.writeInt(pets.size());

        for (IPetData data : pets.values()) {
            msg.writeInt(data.getId());
            msg.writeString(data.getName());

            msg.writeInt(data.getTypeId()); // type ID
            msg.writeInt(data.getRaceId()); // palette ID
            msg.writeString(data.getColour()); // color

            if (data instanceof PetMonsterPlantData) {
                msg.writeInt(0); // CANCER 1
                // PARKER ENLOQUECISTE XDXDXD

                msg.writeInt(5); // CRASH

                msg.writeInt(4);
                msg.writeInt(((PetMonsterPlantData) data).getEyes());
                msg.writeInt(((PetMonsterPlantData) data).getEyesColor());

                msg.writeInt(3);
                msg.writeInt(((PetMonsterPlantData) data).getNose());
                msg.writeInt(((PetMonsterPlantData) data).getNoseColor()); // RIEN

                msg.writeInt(2); // RIEN
                msg.writeInt(((PetMonsterPlantData) data).getMouth());
                msg.writeInt(((PetMonsterPlantData) data).getMouthColor()); // RIEN

                msg.writeInt(1);
                msg.writeInt(((PetMonsterPlantData) data).getBody().getId()); // BODY
                msg.writeInt(((PetMonsterPlantData) data).getPlantColor()); // Couleur body


                msg.writeInt(0);
                msg.writeInt(-1);
                msg.writeInt(2);

                msg.writeInt(((PetMonsterPlantData) data).getGrowthStage()); // level
            } else {
                msg.writeInt(0);
                msg.writeInt(0);
                msg.writeInt(5); // niveau de l'animal
            }
        }
    }
}
