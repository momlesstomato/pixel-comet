package com.cometproject.server.network.messages.outgoing.handshake;

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
     * Creates a init crypto message composer instance for the network message subsystem.
     *
     * @param prime Prime supplied by the caller.
     * @param generator Generator supplied by the caller.
     */
    public InitCryptoMessageComposer(final String prime, final String generator) {
        this.prime = prime;
        this.generator = generator;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
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
