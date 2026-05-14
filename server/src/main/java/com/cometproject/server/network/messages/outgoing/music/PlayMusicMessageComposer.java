package com.cometproject.server.network.messages.outgoing.music;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the play music message for the Pixel Protocol client.
 */
public class PlayMusicMessageComposer extends MessageComposer {

    private int songId;
    private int playlistIndex;
    private int timestamp;

    /**
     * Creates a play music message composer instance for the network message subsystem.
     *
     * @param songId Song id supplied by the caller.
     * @param playlistIndex Playlist index supplied by the caller.
     * @param timestamp Timestamp supplied by the caller.
     */
    public PlayMusicMessageComposer(int songId, int playlistIndex, int timestamp) {
        this.songId = songId;
        this.playlistIndex = playlistIndex;
        this.timestamp = timestamp;
    }

    /**
     * Creates a play music message composer instance for the network message subsystem.
     */
    public PlayMusicMessageComposer() {
        this.songId = -1;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PlayMusicMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if (this.songId == -1) {
            msg.writeInt(-1);
            msg.writeInt(-1);
            msg.writeInt(-1);
            msg.writeInt(-1);
            msg.writeInt(0);
            return;
        }

        msg.writeInt(songId);
        msg.writeInt(playlistIndex);
        msg.writeInt(songId);
        msg.writeInt(0);
        msg.writeInt(timestamp);
    }
}
