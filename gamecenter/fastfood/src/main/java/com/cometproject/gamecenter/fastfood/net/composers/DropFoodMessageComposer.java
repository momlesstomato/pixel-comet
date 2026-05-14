package com.cometproject.gamecenter.fastfood.net.composers;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.gamecenter.fastfood.objects.FoodPlate;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the drop food message for the Pixel Protocol client.
 */
public class DropFoodMessageComposer extends MessageComposer {
    private final int objectId;
    private final FoodPlate foodPlate;
    private boolean falseStarted;

    /**
     * Creates a drop food message composer instance for the protocol composer subsystem.
     *
     * @param objectId Object id supplied by the caller.
     * @param foodPlate Food plate supplied by the caller.
     * @param falseStarted False started supplied by the caller.
     */
    public DropFoodMessageComposer(int objectId, FoodPlate foodPlate, boolean falseStarted) {
        this.objectId = objectId;
        this.foodPlate = foodPlate;
        this.falseStarted = falseStarted;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return 4;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(objectId);

        msg.writeInt(this.foodPlate.getPlayerId());
        msg.writeString(Float.toString(this.foodPlate.getLocation())); // locY
        msg.writeString(Float.toString(this.foodPlate.getSpeed()));//speed
        msg.writeInt(this.foodPlate.getState());// state
        msg.writeBoolean(this.falseStarted);// isFalseStarted
    }
}