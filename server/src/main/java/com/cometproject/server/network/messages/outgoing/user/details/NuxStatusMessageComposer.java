package com.cometproject.server.network.messages.outgoing.user.details;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the NUX status message for the Pixel Protocol client.
 */
public class NuxStatusMessageComposer extends MessageComposer {
    private int status;

    /**
     * Creates a NUX status message composer instance for the network message subsystem.
     *
     * @param status Status supplied by the caller.
     */
    public NuxStatusMessageComposer(int status) {
        this.status = status;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.MassEventMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(status);
    }
}
