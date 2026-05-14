package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the get guest room result message for the Pixel Protocol client.
 */
public class GetGuestRoomResultMessageComposer extends MessageComposer {
    private final int roomId;

    /**
     * Creates a get guest room result message composer instance for the network message subsystem.
     *
     * @param roomId Room identifier used by the operation.
     */
    public GetGuestRoomResultMessageComposer(int roomId) {
        this.roomId = roomId;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.GetGuestRoomResultMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(roomId);
    }
}