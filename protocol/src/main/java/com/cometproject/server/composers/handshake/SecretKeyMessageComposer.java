package com.cometproject.server.composers.handshake;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the secret key message for the Pixel Protocol client.
 */
public class SecretKeyMessageComposer extends MessageComposer {
    private final String publicKey;

    /**
     * Creates a secret key message composer instance for the protocol composer subsystem.
     *
     * @param publicKey Public key value supplied by the caller.
     */
    public SecretKeyMessageComposer(final String publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.SecretKeyMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.publicKey);
    }
}
