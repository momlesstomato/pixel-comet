package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the stop avatar effect message for the Pixel Protocol client.
 */
public class StopAvatarEffectMessageComposer extends MessageComposer {
    private final int effectId;

    /**
     * Creates a stop avatar effect message composer instance for the network message subsystem.
     *
     * @param effectId Effect id supplied by the caller.
     */
    public StopAvatarEffectMessageComposer(final int effectId) {
        this.effectId = effectId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.StopAvatarEffectMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(effectId);
    }
}
