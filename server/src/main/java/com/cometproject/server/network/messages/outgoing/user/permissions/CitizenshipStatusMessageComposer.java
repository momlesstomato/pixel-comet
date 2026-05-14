package com.cometproject.server.network.messages.outgoing.user.permissions;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the citizenship status message for the Pixel Protocol client.
 */
public class CitizenshipStatusMessageComposer extends MessageComposer {
    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return 0;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString("helper");
        msg.writeInt(4);
        msg.writeInt(4);

    }
}
