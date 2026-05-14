package com.cometproject.server.network.messages.outgoing.music.playlist;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.items.music.SongItemData;
import com.cometproject.server.game.rooms.objects.items.types.floor.SoundMachineFloorItem;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the playlist message for the Pixel Protocol client.
 */
public class PlaylistMessageComposer extends MessageComposer {
    private List<SongItemData> songItemDatas;

    /**
     * Creates a playlist message composer instance for the network message subsystem.
     *
     * @param songItemDatas Song item datas supplied by the caller.
     */
    public PlaylistMessageComposer(List<SongItemData> songItemDatas) {
        this.songItemDatas = songItemDatas;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.PlaylistMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(SoundMachineFloorItem.MAX_CAPACITY);
        msg.writeInt(songItemDatas.size());

        for (SongItemData songItemData : this.songItemDatas) {
            msg.writeInt(songItemData.getItemSnapshot().getBaseItemId());
            msg.writeInt(songItemData.getSongId());
        }
    }
}
