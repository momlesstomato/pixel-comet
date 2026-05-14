package com.cometproject.server.network.messages.outgoing.room.gifts;

import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.furniture.types.GiftData;
import com.cometproject.api.game.furniture.types.ItemType;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the open gift message for the Pixel Protocol client.
 */
public class OpenGiftMessageComposer extends MessageComposer {
    private final int presentId;
    private final String type;
    private final GiftData giftData;
    private final FurnitureDefinition itemDefinition;

    /**
     * Creates a open gift message composer instance for the network message subsystem.
     *
     * @param presentId Present id supplied by the caller.
     * @param type Type supplied by the caller.
     * @param giftData Gift data supplied by the caller.
     * @param itemDefinition Item definition supplied by the caller.
     */
    public OpenGiftMessageComposer(final int presentId, final String type, final GiftData giftData, final FurnitureDefinition itemDefinition) {
        this.presentId = presentId;
        this.type = type;
        this.giftData = giftData;
        this.itemDefinition = itemDefinition;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.OpenGiftMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(itemDefinition.getType());
        msg.writeInt(itemDefinition.getSpriteId());
        msg.writeString(itemDefinition.getPublicName());
        msg.writeInt(presentId);
        msg.writeString(type);
        msg.writeBoolean(itemDefinition.getItemType() == ItemType.FLOOR);
        msg.writeString(giftData.getExtraData());
    }
}
