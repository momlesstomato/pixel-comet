package com.cometproject.server.network.messages.outgoing.nuxs;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.catalog.CatalogManager;
import com.cometproject.server.game.nuxs.NuxGift;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the NUX gift selection view message for the Pixel Protocol client.
 */
public class NuxGiftSelectionViewMessageComposer extends MessageComposer {
    private static int pageType = 0;

    /**
     * Creates a NUX gift selection view message composer instance for the network message subsystem.
     *
     * @param pageType Page type supplied by the caller.
     */
    public NuxGiftSelectionViewMessageComposer(int pageType) {
        this.pageType = pageType;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.NuxGiftSelectionViewMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {

        msg.writeInt(1); // Número de páginas.

        msg.writeInt(1);
        msg.writeInt(3);
        msg.writeInt(CatalogManager.getInstance().getNuxGiftsSelectionView(pageType).size()); // Número total de premios:

        for(NuxGift gift : CatalogManager.getInstance().getNuxGiftsSelectionView(pageType)) {
            msg.writeString(gift.getIcon()); // image.library.url + string
            msg.writeInt(1); // items:
            msg.writeString(gift.getProductdata()); // item_name (product_x_name)
            msg.writeString(""); // can be null
        }
    }
}