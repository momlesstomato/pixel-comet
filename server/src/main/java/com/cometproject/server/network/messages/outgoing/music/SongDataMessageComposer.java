package com.cometproject.server.network.messages.outgoing.music;

import com.cometproject.api.game.furniture.types.IMusicData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the song data message for the Pixel Protocol client.
 */
public class SongDataMessageComposer extends MessageComposer {

    private final List<IMusicData> musicData;

    /**
     * Creates a song data message composer instance for the network message subsystem.
     *
     * @param musicData Music data supplied by the caller.
     */
    public SongDataMessageComposer(List<IMusicData> musicData) {
        this.musicData = musicData;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SongDataMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(musicData.size());

        for (IMusicData musicData : this.musicData) {
            msg.writeInt(musicData.getSongId());
            msg.writeString(musicData.getName());
            msg.writeString(musicData.getTitle());
            msg.writeString(musicData.getData());
            msg.writeInt(musicData.getLengthMilliseconds());
            msg.writeString(musicData.getArtist());
        }
    }
}
