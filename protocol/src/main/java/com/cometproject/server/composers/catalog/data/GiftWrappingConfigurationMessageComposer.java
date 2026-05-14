package com.cometproject.server.composers.catalog.data;

import com.cometproject.api.game.catalog.ICatalogService;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the gift wrapping configuration message for the Pixel Protocol client.
 */
public class GiftWrappingConfigurationMessageComposer extends MessageComposer {
    private static final int[] giftColours = {
            0, 1, 2, 3, 4, 5, 6, 8
    };

    private static final int[] giftDecorations = {
            0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    };

    private final ICatalogService catalogService;

    /**
     * Creates a gift wrapping configuration message composer instance for the catalog subsystem.
     *
     * @param catalogService Catalog service value supplied by the caller.
     */
    public GiftWrappingConfigurationMessageComposer(final ICatalogService catalogService) {
        this.catalogService = catalogService;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.GiftWrappingConfigurationMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(true);//?
        msg.writeInt(1);//?
        msg.writeInt(this.catalogService.getGiftBoxesNew().size());

        for (int spriteId : this.catalogService.getGiftBoxesNew()) {
            msg.writeInt(spriteId);
        }

        msg.writeInt(giftColours.length);

        for (int giftColour : giftColours) {
            msg.writeInt(giftColour);
        }

        msg.writeInt(giftDecorations.length);

        for (int giftDecoration : giftDecorations) {
            msg.writeInt(giftDecoration);
        }

        msg.writeInt(this.catalogService.getGiftBoxesOld().size());

        for (int spriteId : this.catalogService.getGiftBoxesOld()) {
            msg.writeInt(spriteId);
        }
    }
}
