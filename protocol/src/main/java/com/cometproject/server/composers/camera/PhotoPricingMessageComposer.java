package com.cometproject.server.composers.camera;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the photo pricing message for the Pixel Protocol client.
 */
public class PhotoPricingMessageComposer extends MessageComposer {
    private final int coins;
    private final int duckets;

    /**
     * Creates a photo pricing message composer instance for the protocol composer subsystem.
     *
     * @param coins Coins value supplied by the caller.
     * @param duckets Duckets value supplied by the caller.
     */
    public PhotoPricingMessageComposer(int coins, int duckets) {
        this.coins = coins;
        this.duckets = duckets;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.PhotoPriceMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.coins);
        msg.writeInt(this.duckets);
    }
}