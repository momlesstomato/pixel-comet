package com.cometproject.server.protocol.codec;


import java.util.List;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.server.protocol.crypto.exceptions.HabboRC4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class EncryptionDecoder extends ByteToMessageDecoder {

    private final HabboRC4 rc4;
    private final ConnectionCipher cipher;

    public EncryptionDecoder(byte[] key) {
        this.rc4 = new HabboRC4(key);
        this.cipher = null;
    }

    public EncryptionDecoder(final ConnectionCipher cipher) {
        this.rc4 = null;
        this.cipher = cipher;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (this.cipher != null) {
            out.add(this.cipher.decrypt(in));
            return;
        }

        out.add(this.rc4.decipher(in));
    }
}
