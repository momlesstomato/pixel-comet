package com.cometproject.server.protocol.codec;

import com.cometproject.server.protocol.messages.MessageEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Describes message decoder behavior for the protocol codec subsystem.
 */
public class MessageDecoder extends ByteToMessageDecoder {

    /**
     * Decodes inbound bytes into the next protocol object.
     *
     * @param ctx Netty channel context for the current operation.
     * @param in Inbound byte buffer being decoded.
     * @param out Output collection receiving decoded protocol objects.
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        try {
            short header = in.readShort();

            out.add(new MessageEvent(header, in.readBytes(in.readableBytes())));
//            ctx.fireChannelReadComplete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}