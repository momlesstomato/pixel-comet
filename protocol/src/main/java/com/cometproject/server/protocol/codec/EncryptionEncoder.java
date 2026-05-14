package com.cometproject.server.protocol.codec;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.server.protocol.crypto.exceptions.HabboRC4;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * Describes encryption encoder behavior for the protocol codec subsystem.
 */
public class EncryptionEncoder extends ChannelOutboundHandlerAdapter {
    private final HabboRC4 rc4;
    private final ConnectionCipher cipher;

    /**
     * Creates a encryption encoder instance for the protocol subsystem.
     *
     * @param key Key value supplied by the caller.
     */
    public EncryptionEncoder(byte[] key) {
        this.rc4 = new HabboRC4(key);
        this.cipher = null;
    }

    /**
     * Creates a encryption encoder instance for the protocol subsystem.
     *
     * @param cipher Cipher value supplied by the caller.
     */
    public EncryptionEncoder(final ConnectionCipher cipher) {
        this.rc4 = null;
        this.cipher = cipher;
    }

    /**
     * Writes outbound protocol data through the Netty pipeline.
     *
     * @param ctx Netty channel context for the current operation.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param promise Netty promise completed when the write finishes.
     * @throws Exception When the protocol operation cannot complete.
     */
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        // Convert msg to ByteBuf.
        ByteBuf out = (ByteBuf) msg;

        /*
        // Read all available bytes.
        byte[] data;
        if (out.hasArray()) {
            data = out.array();
        } else {
            data = out.readBytes(out.readableBytes()).array();
        }
        // Encrypt.
        ctx.channel().attr(ISessionManager.CRYPTO_SERVER).get().parse(data);
        */

        // Continue in the pipeline.
        if (this.cipher != null) {
            ctx.write(this.cipher.encrypt(out), promise);
            return;
        }

        ctx.write(this.rc4.decipher(out), promise);
    }


}
