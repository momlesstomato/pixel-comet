package com.cometproject.server.network.messages.outgoing.user.mistery;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the mistery box reward message for the Pixel Protocol client.
 */
public class MisteryBoxRewardMessageComposer extends MessageComposer {

    /**
     * Creates a mistery box reward message composer instance for the network message subsystem.
     */
    public MisteryBoxRewardMessageComposer() {
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.MisteryBoxRewardMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString("s"); // item type S / I
        msg.writeInt(2323); // item spriteId
    }
}
