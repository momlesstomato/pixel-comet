package com.cometproject.server.network.messages.outgoing.notification;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the purchase error message for the Pixel Protocol client.
 */
public class PurchaseErrorMessageComposer extends MessageComposer {

    private final int ErrorCode;

    /**
     * Creates a purchase error message composer instance for the network message subsystem.
     *
     * @param ErrorCode Error code supplied by the caller.
     */
    public PurchaseErrorMessageComposer(final int ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PurchaseErrorMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(ErrorCode);
    }
}
