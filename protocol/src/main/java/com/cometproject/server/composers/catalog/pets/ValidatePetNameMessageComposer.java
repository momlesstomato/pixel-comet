package com.cometproject.server.composers.catalog.pets;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the validate pet name message for the Pixel Protocol client.
 */
public class ValidatePetNameMessageComposer extends MessageComposer {

    private final int errorCode;
    private final String data;

    /**
     * Creates a validate pet name message composer instance for the pet subsystem.
     *
     * @param errorCode Error code value supplied by the caller.
     * @param data Data value supplied by the caller.
     */
    public ValidatePetNameMessageComposer(final int errorCode, final String data) {
        this.errorCode = errorCode;
        this.data = data;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.CheckPetNameMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(errorCode);
        msg.writeString(data);
    }
}
