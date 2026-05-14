package com.cometproject.server.network.messages.outgoing.room.access;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Describes doorbell request composer behavior for the network message subsystem.
 */
public class DoorbellRequestComposer extends MessageComposer {
    private final String username;

    /**
     * Creates a doorbell request composer instance for the network message subsystem.
     *
     * @param username Username supplied by the caller.
     */
    public DoorbellRequestComposer(final String username) {
        this.username = username;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.DoorbellMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.username);
    }
}
