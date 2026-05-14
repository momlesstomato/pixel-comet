package com.cometproject.server.network.messages.outgoing.room.settings;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the enforce room category message for the Pixel Protocol client.
 */
public class EnforceRoomCategoryMessageComposer extends MessageComposer {

    private int defaultCategory = 16;

    /**
     * Creates a enforce room category message composer instance for the network message subsystem.
     */
    public EnforceRoomCategoryMessageComposer() {

    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.EnforceCategoryUpdateMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(defaultCategory);
    }
}
