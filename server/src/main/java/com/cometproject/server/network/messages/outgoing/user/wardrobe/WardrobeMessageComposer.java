package com.cometproject.server.network.messages.outgoing.user.wardrobe;

import com.cometproject.api.game.players.data.types.IWardrobeItem;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.List;


/**
 * Serializes the wardrobe message for the Pixel Protocol client.
 */
public class WardrobeMessageComposer extends MessageComposer {
    private final List<IWardrobeItem> wardrobe;

    /**
     * Creates a wardrobe message composer instance for the network message subsystem.
     *
     * @param wardrobe Wardrobe supplied by the caller.
     */
    public WardrobeMessageComposer(final List<IWardrobeItem> wardrobe) {
        this.wardrobe = wardrobe;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.WardrobeMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(1);
        msg.writeInt(wardrobe.size());

        for (IWardrobeItem item : wardrobe) {
            msg.writeInt(item.getSlot());
            msg.writeString(item.getFigure());

            if (item.getGender() != null) {
                msg.writeString(item.getGender().toUpperCase());
            } else {
                msg.writeString("M");
            }
        }
    }
}
