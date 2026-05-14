package com.cometproject.server.composers.catalog.pets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Describes catalog mode composer behavior for the protocol composer subsystem.
 */
public class CatalogModeComposer extends MessageComposer {
    private final int mode;

    /**
     * Creates a catalog mode composer instance for the pet subsystem.
     *
     * @param mode Mode value supplied by the caller.
     */
    public CatalogModeComposer(int mode) {
        this.mode = mode;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.CatalogModeMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(final IComposer msg) {
        msg.writeInt(mode);
    }
}
