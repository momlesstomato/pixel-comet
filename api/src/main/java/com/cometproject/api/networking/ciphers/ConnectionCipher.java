package com.cometproject.api.networking.ciphers;

import io.netty.buffer.ByteBuf;

/**
 * Defines the symmetric cipher applied to a transport connection.
 */
public interface ConnectionCipher {
    /**
     * Returns whether the cipher is actively transforming traffic.
     *
     * @return True when the cipher is active.
     */
    boolean isActive();

    /**
     * Encrypts an outbound payload.
     *
     * @param payload The outbound payload.
     * @return The encrypted payload.
     */
    ByteBuf encrypt(ByteBuf payload);

    /**
     * Decrypts an inbound payload.
     *
     * @param payload The inbound payload.
     * @return The decrypted payload.
     */
    ByteBuf decrypt(ByteBuf payload);
}