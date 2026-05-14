package com.cometproject.server.network.messages.outgoing.user.wardrobe;

import com.cometproject.api.game.catalog.types.IClothingItem;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

import java.util.HashSet;
import java.util.Set;

/**
 * Serializes the figure set ids message for the Pixel Protocol client.
 */
public class FigureSetIdsMessageComposer extends MessageComposer {

    private final Set<String> clothing;

    /**
     * Creates a figure set ids message composer instance for the network message subsystem.
     *
     * @param clothing Clothing supplied by the caller.
     */
    public FigureSetIdsMessageComposer(final Set<String> clothing) {
        this.clothing = clothing;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FigureSetIdsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        final Set<Integer> parts = new HashSet<>();

        for (String clothing : this.clothing) {
            final IClothingItem clothingItem = CatalogManager.getInstance().getClothingItems().get(clothing);

            if (clothingItem != null) {
                for (int part : clothingItem.getParts()) {
                    parts.add(part);
                }
            }
        }

        msg.writeInt(parts.size());

        for (int part : parts) {
            msg.writeInt(part);
        }

        msg.writeInt(this.clothing.size());

        for (String clothing : this.clothing) {
            msg.writeString(clothing);
        }
    }
}
