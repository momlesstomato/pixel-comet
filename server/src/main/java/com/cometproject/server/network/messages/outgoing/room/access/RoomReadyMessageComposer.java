package com.cometproject.server.network.messages.outgoing.room.access;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the room ready message for the Pixel Protocol client.
 */
public class RoomReadyMessageComposer extends MessageComposer {

    private final int id;
    private final String model;

    /**
     * Creates a room ready message composer instance for the network message subsystem.
     *
     * @param id Id supplied by the caller.
     * @param model Model supplied by the caller.
     */
    public RoomReadyMessageComposer(int id, String model) {
        this.id = id;
        this.model = model;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomReadyMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.model);
        msg.writeInt(this.id);
    }
}
