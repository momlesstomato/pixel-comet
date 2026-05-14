package com.cometproject.server.composers.handshake;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the authentication ok message for the Pixel Protocol client.
 */
public class AuthenticationOKMessageComposer extends MessageComposer {
    /**
     * Creates a authentication ok message composer instance for the protocol composer subsystem.
     */
    public AuthenticationOKMessageComposer() {

    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
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
