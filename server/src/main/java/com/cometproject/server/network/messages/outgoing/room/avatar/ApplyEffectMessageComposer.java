package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the apply effect message for the Pixel Protocol client.
 */
public class ApplyEffectMessageComposer extends MessageComposer {

    private final int avatarId;
    private final int effectId;

    /**
     * Creates a apply effect message composer instance for the network message subsystem.
     *
     * @param avatarId Avatar id supplied by the caller.
     * @param effectId Effect id supplied by the caller.
     */
    public ApplyEffectMessageComposer(final int avatarId, final int effectId) {
        this.avatarId = avatarId;
        this.effectId = effectId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.AvatarEffectMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(avatarId);
        msg.writeInt(effectId);
        msg.writeInt(0);
    }
}
