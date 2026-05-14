package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.protocol.headers.Composers;


/**
 * Serializes the shout message for the Pixel Protocol client.
 */
public class ShoutMessageComposer extends TalkMessageComposer {

    /**
     * Creates a shout message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param message Message supplied by the caller.
     * @param emotion Emotion supplied by the caller.
     * @param colour Colour supplied by the caller.
     */
    public ShoutMessageComposer(final int playerId, final String message, final ChatEmotion emotion, final int colour) {
        super(playerId, message, emotion, colour);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ShoutMessageComposer;
    }
}
