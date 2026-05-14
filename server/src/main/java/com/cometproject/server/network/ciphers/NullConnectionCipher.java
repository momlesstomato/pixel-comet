package com.cometproject.server.network.ciphers;

import com.cometproject.api.networking.ciphers.ConnectionCipher;

import io.netty.buffer.ByteBuf;

/**
 * Pass-through cipher used before a connection has negotiated encryption.
 */
public final class NullConnectionCipher implements ConnectionCipher {
    /**
     * Shared singleton instance.
     */
    public static final NullConnectionCipher INSTANCE = new NullConnectionCipher();

    private NullConnectionCipher() {
    }

    /**
     * Indicates whether active applies to this networking contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isActive() {
        return false;
    }

    /**
     * Executes encrypt for this networking contract.
     *
     * @param payload Payload supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public ByteBuf encrypt(final ByteBuf payload) {
        return payload;
    }

    /**
     * Executes decrypt for this networking contract.
     *
     * @param payload Payload supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public ByteBuf decrypt(final ByteBuf payload) {
        return payload;
    }
}