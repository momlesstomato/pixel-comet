package com.cometproject.server.network.messages.outgoing.room.freeze;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the update freeze lives message for the Pixel Protocol client.
 */
public class UpdateFreezeLivesMessageComposer extends MessageComposer {
    private final int avatar;
    private final int lives;

    /**
     * Creates a update freeze lives message composer instance for the network message subsystem.
     *
     * @param avatar Avatar supplied by the caller.
     * @param lives Lives supplied by the caller.
     */
    public UpdateFreezeLivesMessageComposer(int avatar, int lives) {
        this.avatar = avatar;
        this.lives = lives;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.UpdateFreezeLivesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.avatar);
        msg.writeInt(this.lives);
    }
}
