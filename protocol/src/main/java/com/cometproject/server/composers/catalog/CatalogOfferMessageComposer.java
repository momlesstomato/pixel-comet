package com.cometproject.server.composers.catalog;

import com.cometproject.api.game.catalog.types.ICatalogItem;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the catalog offer message for the Pixel Protocol client.
 */
public class CatalogOfferMessageComposer extends MessageComposer {
    private final ICatalogItem catalogItem;

    /**
     * Creates a catalog offer message composer instance for the catalog subsystem.
     *
     * @param catalogItem Catalog item value supplied by the caller.
     */
    public CatalogOfferMessageComposer(final ICatalogItem catalogItem) {
        this.catalogItem = catalogItem;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.CatalogOfferMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        this.catalogItem.compose(msg);
    }
}
