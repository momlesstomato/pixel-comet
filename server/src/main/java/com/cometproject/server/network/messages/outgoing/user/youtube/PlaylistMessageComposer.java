package com.cometproject.server.network.messages.outgoing.user.youtube;

import com.cometproject.api.game.players.data.types.IPlaylistItem;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the playlist message for the Pixel Protocol client.
 */
public class PlaylistMessageComposer extends MessageComposer {
    private final int itemId;
    private final List<IPlaylistItem> playlist;
    private final int videoId;

    /**
     * Creates a playlist message composer instance for the network message subsystem.
     *
     * @param itemId Item id supplied by the caller.
     * @param playlist Playlist supplied by the caller.
     * @param videoId Video id supplied by the caller.
     */
    public PlaylistMessageComposer(final int itemId, final List<IPlaylistItem> playlist, final int videoId) {
        this.itemId = itemId;
        this.playlist = playlist;
        this.videoId = videoId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.YoutubeDisplayPlaylistsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(itemId);

        msg.writeInt(playlist.size());

        for (IPlaylistItem playListItem : playlist) {
            msg.writeString(playlist.indexOf(playListItem)); // not sure if can do this...
            msg.writeString(playListItem.getTitle());
            msg.writeString(playListItem.getDescription());
        }

        msg.writeString(videoId);
    }
}
