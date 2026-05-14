package com.cometproject.server.network.messages.outgoing.room.engine;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the room entry info message for the Pixel Protocol client.
 */
public class RoomEntryInfoMessageComposer extends MessageComposer {
    private final int id;
    private final boolean hasOwnershipPermission;

    /**
     * Creates a room entry info message composer instance for the network message subsystem.
     *
     * @param id Id supplied by the caller.
     * @param hasOwnershipPermission Has ownership permission supplied by the caller.
     */
    public RoomEntryInfoMessageComposer(final int id, final boolean hasOwnershipPermission) {
        this.id = id;
        this.hasOwnershipPermission = hasOwnershipPermission;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.RoomEntryInfoMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(id);
        msg.writeBoolean(hasOwnershipPermission);
    }
}
