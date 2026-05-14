package com.cometproject.server.composers.catalog;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the catalog publish message for the Pixel Protocol client.
 */
public class CatalogPublishMessageComposer extends MessageComposer {

    private final boolean showNotification;

    /**
     * Creates a catalog publish message composer instance for the catalog subsystem.
     *
     * @param showNotification Show notification value supplied by the caller.
     */
    public CatalogPublishMessageComposer(final boolean showNotification) {
        this.showNotification = showNotification;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.CatalogUpdatedMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(this.showNotification);
    }
}
