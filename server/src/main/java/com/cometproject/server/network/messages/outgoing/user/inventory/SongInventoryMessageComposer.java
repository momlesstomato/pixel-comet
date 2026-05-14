package com.cometproject.server.network.messages.outgoing.user.inventory;

import com.cometproject.api.game.furniture.types.SongItem;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.items.ItemManager;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;

/**
 * Serializes the song inventory message for the Pixel Protocol client.
 */
public class SongInventoryMessageComposer extends MessageComposer {

    private List<SongItem> songItems;

    /**
     * Creates a song inventory message composer instance for the network message subsystem.
     *
     * @param songItems Song items supplied by the caller.
     */
    public SongInventoryMessageComposer(List<SongItem> songItems) {
        this.songItems = songItems;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.SongInventoryMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.songItems.size());

        for (SongItem songItem : this.songItems) {
            msg.writeInt(ItemManager.getInstance().getItemVirtualId(songItem.getItemSnapshot().getId()));
            msg.writeInt(songItem.getSongId());
        }
    }
}
