package com.cometproject.server.protocol.codec;


import java.util.List;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.server.protocol.crypto.exceptions.HabboRC4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Describes encryption decoder behavior for the protocol codec subsystem.
 */
public class EncryptionDecoder extends ByteToMessageDecoder {

    private final HabboRC4 rc4;
    private final ConnectionCipher cipher;

    /**
     * Creates a encryption decoder instance for the protocol subsystem.
     *
     * @param key Key value supplied by the caller.
     */
    public EncryptionDecoder(byte[] key) {
        this.rc4 = new HabboRC4(key);
        this.cipher = null;
    }

    /**
     * Creates a encryption decoder instance for the protocol subsystem.
     *
     * @param cipher Cipher value supplied by the caller.
     */
    public EncryptionDecoder(final ConnectionCipher cipher) {
        this.rc4 = null;
        this.cipher = cipher;
    }

    /**
     * Decodes inbound bytes into the next protocol object.
     *
     * @param ctx Netty channel context for the current operation.
     * @param in Inbound byte buffer being decoded.
     * @param out Output collection receiving decoded protocol objects.
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (this.cipher != null) {
            out.add(this.cipher.decrypt(in));
            return;
        }

        out.add(this.rc4.decipher(in));
    }
}
