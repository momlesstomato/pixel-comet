package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the whisper message for the Pixel Protocol client.
 */
public class WhisperMessageComposer extends MessageComposer {
    private final int playerId;
    private final String message;
    private final int bubbleId;

    /**
     * Creates a whisper message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param message Message supplied by the caller.
     * @param bubbleId Bubble id supplied by the caller.
     */
    public WhisperMessageComposer(int playerId, String message, int bubbleId) {
        this.playerId = playerId;
        this.message = message;
        this.bubbleId = bubbleId;
    }

    /**
     * Creates a whisper message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param message Message supplied by the caller.
     */
    public WhisperMessageComposer(int playerId, String message) {
        this(playerId, message, 0);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.WhisperMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(playerId);
        msg.writeString(message);
        msg.writeInt(0);
        msg.writeInt(bubbleId);
        msg.writeInt(0);
        msg.writeInt(-1);
    }
}
