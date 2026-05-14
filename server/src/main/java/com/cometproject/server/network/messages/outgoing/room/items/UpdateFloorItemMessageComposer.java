package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the update floor item message for the Pixel Protocol client.
 */
public class UpdateFloorItemMessageComposer extends MessageComposer {
    private final RoomItemFloor item;

    /**
     * Creates a update floor item message composer instance for the network message subsystem.
     *
     * @param item Item supplied by the caller.
     */
    public UpdateFloorItemMessageComposer(RoomItemFloor item) {
        this.item = item;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ObjectUpdateMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        this.item.serialize(msg);
        msg.writeInt(this.item.getItemData().getOwnerId());
    }
}
