package com.cometproject.server.composers.handshake;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;


/**
 * Serializes the init crypto message for the Pixel Protocol client.
 */
public class InitCryptoMessageComposer extends MessageComposer {
    private final String prime;
    private final String generator;

    /**
     * Creates a init crypto message composer instance for the protocol composer subsystem.
     *
     * @param prime Prime value supplied by the caller.
     * @param generator Generator value supplied by the caller.
     */
    public InitCryptoMessageComposer(final String prime, final String generator) {
        this.prime = prime;
        this.generator = generator;
    }

    /**
     * Returns the outgoing Pixel Protocol message id.
     *
     * @return Outgoing message id registered in the protocol header table.
     */
    @Override
    public short getId() {
        return Composers.InitCryptoMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeString(this.prime);
        msg.writeString(this.generator);
    }
}
