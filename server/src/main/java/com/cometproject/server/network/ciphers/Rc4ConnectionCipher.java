package com.cometproject.server.network.ciphers;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.server.protocol.crypto.exceptions.HabboRC4;

import io.netty.buffer.ByteBuf;

/**
 * RC4 cipher backed by distinct inbound and outbound key streams.
 */
public final class Rc4ConnectionCipher implements ConnectionCipher {
    private final HabboRC4 inboundCipher;
    private final HabboRC4 outboundCipher;

    /**
     * Creates a new RC4 cipher pair from the negotiated shared key.
     *
     * @param sharedKey The negotiated shared key.
     */
    public Rc4ConnectionCipher(final byte[] sharedKey) {
        this.inboundCipher = new HabboRC4(sharedKey);
        this.outboundCipher = new HabboRC4(sharedKey);
    }

    /**
     * Indicates whether active applies to this networking contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isActive() {
        return true;
    }

    /**
     * Executes encrypt for this networking contract.
     *
     * @param payload Payload supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public ByteBuf encrypt(final ByteBuf payload) {
        return this.outboundCipher.decipher(payload);
    }

    /**
     * Executes decrypt for this networking contract.
     *
     * @param payload Payload supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public ByteBuf decrypt(final ByteBuf payload) {
        return this.inboundCipher.decipher(payload);
    }
}