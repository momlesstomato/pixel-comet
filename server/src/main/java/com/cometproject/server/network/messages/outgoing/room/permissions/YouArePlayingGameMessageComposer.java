package com.cometproject.server.network.messages.outgoing.room.permissions;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the you are playing game message for the Pixel Protocol client.
 */
public class YouArePlayingGameMessageComposer extends MessageComposer {

    private final boolean isPlaying;

    /**
     * Creates a you are playing game message composer instance for the network message subsystem.
     *
     * @param isPlaying Is playing supplied by the caller.
     */
    public YouArePlayingGameMessageComposer(final boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.YouArePlayingGameMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(isPlaying);
    }
}
