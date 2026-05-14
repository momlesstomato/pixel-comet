package com.cometproject.server.network.messages.outgoing.user.mistery;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.types.MisteryComponent;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the mistery box data message for the Pixel Protocol client.
 */
public class MisteryBoxDataMessageComposer extends MessageComposer {

    private final MisteryComponent mistery;

    /**
     * Creates a mistery box data message composer instance for the network message subsystem.
     *
     * @param mistery Mistery supplied by the caller.
     */
    public MisteryBoxDataMessageComposer(final MisteryComponent mistery) {
        this.mistery = mistery;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.MisteryBoxDataMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(mistery.getMisteryBox());
        msg.writeString(mistery.getMisteryKey());
    }
}
