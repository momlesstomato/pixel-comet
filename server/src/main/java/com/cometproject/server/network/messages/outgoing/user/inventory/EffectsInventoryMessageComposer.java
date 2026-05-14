package com.cometproject.server.network.messages.outgoing.user.inventory;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.Set;


/**
 * Serializes the effects inventory message for the Pixel Protocol client.
 */
public class EffectsInventoryMessageComposer extends MessageComposer {

    private final Set<Integer> effects;
    private final int currentEffect;

    /**
     * Creates a effects inventory message composer instance for the network message subsystem.
     *
     * @param effects Effects supplied by the caller.
     * @param currentEffect Current effect supplied by the caller.
     */
    public EffectsInventoryMessageComposer(Set<Integer> effects, int currentEffect) {
        this.effects = effects;
        this.currentEffect = currentEffect;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.AvatarEffectsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.effects.size());

        for (int effect : this.effects) {
            msg.writeInt(effect);
            msg.writeInt(0);
            msg.writeInt(-1);//duration
            msg.writeInt(-1);
            msg.writeInt(currentEffect == effect ? 1 : -1);
            msg.writeBoolean(true);//perm
        }
    }
}
