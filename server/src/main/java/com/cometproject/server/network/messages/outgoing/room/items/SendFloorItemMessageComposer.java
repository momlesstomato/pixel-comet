package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the send floor item message for the Pixel Protocol client.
 */
public class SendFloorItemMessageComposer extends MessageComposer {
    private final RoomItemFloor itemFloor;

    /**
     * Creates a send floor item message composer instance for the network message subsystem.
     *
     * @param itemFloor Item floor supplied by the caller.
     */
    public SendFloorItemMessageComposer(RoomItemFloor itemFloor) {
        this.itemFloor = itemFloor;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ObjectAddMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        this.itemFloor.serialize(msg, true);
    }
}
