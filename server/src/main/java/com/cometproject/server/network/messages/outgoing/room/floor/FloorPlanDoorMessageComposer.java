package com.cometproject.server.network.messages.outgoing.room.floor;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the floor plan door message for the Pixel Protocol client.
 */
public class FloorPlanDoorMessageComposer extends MessageComposer {
    private final int x;
    private final int y;
    private final int rotation;

    /**
     * Creates a floor plan door message composer instance for the network message subsystem.
     *
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     * @param rotation Rotation supplied by the caller.
     */
    public FloorPlanDoorMessageComposer(final int x, final int y, final int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FloorPlanSendDoorMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(x);
        msg.writeInt(y);
        msg.writeInt(rotation);
    }
}
