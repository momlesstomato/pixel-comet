package com.cometproject.server.network.messages.outgoing.music;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the song id message for the Pixel Protocol client.
 */
public class SongIdMessageComposer extends MessageComposer {

    private String songName;
    private int songId;

    /**
     * Creates a song id message composer instance for the network message subsystem.
     *
     * @param songName Song name supplied by the caller.
     * @param songId Song id supplied by the caller.
     */
    public SongIdMessageComposer(String songName, int songId) {
        this.songName = songName;
        this.songId = songId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SongIdMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.songName);
        msg.writeInt(this.songId);
    }
}
