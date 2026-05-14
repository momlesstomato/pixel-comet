package com.cometproject.server.network.messages.outgoing.handshake;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the authentication ok message for the Pixel Protocol client.
 */
public class AuthenticationOKMessageComposer extends MessageComposer {
    /**
     * Creates a authentication ok message composer instance for the network message subsystem.
     */
    public AuthenticationOKMessageComposer() {

    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.AuthenticationOKMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {

    }
}
