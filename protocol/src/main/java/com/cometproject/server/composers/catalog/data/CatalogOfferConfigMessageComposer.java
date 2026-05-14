package com.cometproject.server.composers.catalog.data;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the catalog offer config message for the Pixel Protocol client.
 */
public class CatalogOfferConfigMessageComposer extends MessageComposer {

    /**
     * Creates a catalog offer config message composer instance for the catalog subsystem.
     */
    public CatalogOfferConfigMessageComposer() {

    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.CatalogItemDiscountMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(100);
        msg.writeInt(6);
        msg.writeInt(1);
        msg.writeInt(1);
        msg.writeInt(2);
        msg.writeInt(40);
        msg.writeInt(99);
    }
}
