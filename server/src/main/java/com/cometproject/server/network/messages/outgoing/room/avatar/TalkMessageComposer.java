package com.cometproject.server.network.messages.outgoing.room.avatar;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.types.misc.ChatEmotion;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the talk message for the Pixel Protocol client.
 */
public class TalkMessageComposer extends MessageComposer {
    private final int playerId;
    private final String message;
    private final ChatEmotion emoticon;
    private final int colour;

    /**
     * Creates a talk message composer instance for the network message subsystem.
     *
     * @param playerId Player identifier used by the operation.
     * @param message Message supplied by the caller.
     * @param emoticion Emoticion supplied by the caller.
     * @param colour Colour supplied by the caller.
     */
    public TalkMessageComposer(final int playerId, final String message, final ChatEmotion emoticion, final int colour) {
        this.playerId = playerId;
        this.message = message;
        this.emoticon = emoticion;
        this.colour = colour;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ChatMessageComposer;
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
        msg.writeInt(emoticon.getEmotionId());
        msg.writeInt(colour);
        msg.writeInt(0);
        msg.writeInt(-1);
    }
}
