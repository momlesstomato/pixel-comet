package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.items.RoomItemWall;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the send wall item message for the Pixel Protocol client.
 */
public class SendWallItemMessageComposer extends MessageComposer {
    private final RoomItemWall itemWall;

    /**
     * Creates a send wall item message composer instance for the network message subsystem.
     *
     * @param itemWall Item wall supplied by the caller.
     */
    public SendWallItemMessageComposer(RoomItemWall itemWall) {
        this.itemWall = itemWall;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ItemAddMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        this.itemWall.serialize(msg);
    }
}
