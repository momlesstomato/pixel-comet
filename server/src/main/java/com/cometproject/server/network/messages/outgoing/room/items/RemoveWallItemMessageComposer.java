package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the remove wall item message for the Pixel Protocol client.
 */
public class RemoveWallItemMessageComposer extends MessageComposer {
    private final int itemId;
    private final int playerId;

    /**
     * Creates a remove wall item message composer instance for the network message subsystem.
     *
     * @param itemId Item id supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public RemoveWallItemMessageComposer(final int itemId, final int playerId) {
        this.itemId = itemId;
        this.playerId = playerId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RemoveWallItemMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.itemId);
        msg.writeInt(this.playerId);
    }
}
