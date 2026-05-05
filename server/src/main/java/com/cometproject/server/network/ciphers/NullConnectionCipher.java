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

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public ByteBuf encrypt(final ByteBuf payload) {
        return payload;
    }

    @Override
    public ByteBuf decrypt(final ByteBuf payload) {
        return payload;
    }
}