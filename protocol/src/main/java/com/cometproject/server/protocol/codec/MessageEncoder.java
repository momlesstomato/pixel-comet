package com.cometproject.server.protocol.codec;

import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.protocol.messages.Composer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Describes message encoder behavior for the protocol codec subsystem.
 */
public class MessageEncoder extends MessageToByteEncoder<IMessageComposer> {
    /**
     * Encodes a protocol object into outbound bytes.
     *
     * @param ctx Netty channel context for the current operation.
     * @param msg Composer buffer that receives serialized protocol fields.
     * @param out Output collection receiving decoded protocol objects.
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, IMessageComposer msg, ByteBuf out) {
        try {
            final Composer composer = ((Composer) msg.writeMessage(out));

            if (!composer.isFinalised()) {
                composer.content().setInt(0, composer.content().writerIndex() - 4);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
