package com.cometproject.server.composers.catalog;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the purchase unavailable error message for the Pixel Protocol client.
 */
public class PurchaseUnavailableErrorMessageComposer extends MessageComposer {

    public final static int ILEGAL = 0;
    public final static int REQUIERE_CLUB = 1;

    private final int code;

    /**
     * Creates a purchase unavailable error message composer instance for the catalog subsystem.
     *
     * @param code Code value supplied by the caller.
     */
    public PurchaseUnavailableErrorMessageComposer(int code){
        this.code = code;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.PurchaseUnavailableErrorMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(code);
    }
}
