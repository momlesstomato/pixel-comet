package com.cometproject.server.composers.group;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the update favourite group message for the Pixel Protocol client.
 */
public class UpdateFavouriteGroupMessageComposer extends MessageComposer {
    private final int playerId;

    /**
     * Creates a update favourite group message composer instance for the protocol composer subsystem.
     *
     * @param playerId Player id value supplied by the caller.
     */
    public UpdateFavouriteGroupMessageComposer(final int playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.RefreshFavouriteGroupMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.playerId);
    }
}
