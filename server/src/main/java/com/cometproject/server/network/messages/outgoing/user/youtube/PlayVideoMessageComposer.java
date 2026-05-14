package com.cometproject.server.network.messages.outgoing.user.youtube;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the play video message for the Pixel Protocol client.
 */
public class PlayVideoMessageComposer extends MessageComposer {
    private final int itemId;
    private final String videoId;
    private final int videoLength;

    /**
     * Creates a play video message composer instance for the network message subsystem.
     *
     * @param itemId Item id supplied by the caller.
     * @param videoId Video id supplied by the caller.
     * @param videoLength Video length supplied by the caller.
     */
    public PlayVideoMessageComposer(final int itemId, final String videoId, final int videoLength) {
        this.itemId = itemId;
        this.videoId = videoId;
        this.videoLength = videoLength;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.YouTubeDisplayVideoMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(itemId);
        msg.writeString(videoId);
        msg.writeInt(0);
        msg.writeInt(videoLength);
        msg.writeInt(0);
    }
}
