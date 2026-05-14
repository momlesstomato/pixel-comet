package com.cometproject.server.network.messages.outgoing.room.items;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the firework data charges message for the Pixel Protocol client.
 */
public class FireworkDataChargesMessageComposer extends MessageComposer {
    private final int virtualId;
    private final int fireworks;

    /**
     * Creates a firework data charges message composer instance for the network message subsystem.
     *
     * @param virtualId Virtual id supplied by the caller.
     * @param fireworks Fireworks supplied by the caller.
     */
    public FireworkDataChargesMessageComposer(int virtualId, int fireworks) {
        this.virtualId = virtualId;
        this.fireworks = fireworks;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.FireworkDataChargesMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.virtualId);
        msg.writeInt(this.fireworks);
        msg.writeInt(0);// amount
        msg.writeInt(5); // multiple currencies
        msg.writeInt(0); // activity points
        msg.writeInt(10); // charge recieved
    }
}