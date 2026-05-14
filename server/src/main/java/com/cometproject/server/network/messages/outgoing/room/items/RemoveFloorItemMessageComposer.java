package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the remove floor item message for the Pixel Protocol client.
 */
public class RemoveFloorItemMessageComposer extends MessageComposer {
    private final int id;
    private final int ownerId;
    private final int delay;

    /**
     * Creates a remove floor item message composer instance for the network message subsystem.
     *
     * @param id Id supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     * @param delay Delay supplied by the caller.
     */
    public RemoveFloorItemMessageComposer(final int id, final int ownerId, final int delay) {
        this.id = id;
        this.ownerId = ownerId;
        this.delay = delay;
    }

    /**
     * Creates a remove floor item message composer instance for the network message subsystem.
     *
     * @param id Id supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     */
    public RemoveFloorItemMessageComposer(final int id, final int ownerId) {
        this(id, ownerId, 0);
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.ObjectRemoveMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.id);
        msg.writeBoolean(false); // Is expired
        msg.writeInt(this.ownerId); // Picker ID
        msg.writeInt(this.delay);
    }
}
