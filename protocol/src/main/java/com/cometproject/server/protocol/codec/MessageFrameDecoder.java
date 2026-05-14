package com.cometproject.server.protocol.codec;

import com.cometproject.api.config.Configuration;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * Describes message frame decoder behavior for the protocol codec subsystem.
 */
public class MessageFrameDecoder extends LengthFieldBasedFrameDecoder {
    private static final int MAX_PACKET_LENGTH = Integer.parseInt(Configuration.currentConfig().get("comet.network.maxPacketSize", "500000"));
    private static final int LENGTH_FIELD_OFFSET = 0;
    private static final int LENGTH_FIELD_LENGTH = 4;
    private static final int LENGTH_FIELD_ADJUSTMENT = 0;
    private static final int INITIAL_BYTES_TO_STRIP = 4;

    /**
     * Creates a message frame decoder instance for the protocol subsystem.
     */
    public MessageFrameDecoder() {
        super(MAX_PACKET_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH, LENGTH_FIELD_ADJUSTMENT, INITIAL_BYTES_TO_STRIP);
    }

    /**
     * Decodes inbound bytes into the next protocol object.
     *
     * @param ctx Netty channel context for the current operation.
     * @param in Inbound byte buffer being decoded.
     * @return Value exposed by the contract.
     * @throws Exception When the protocol operation cannot complete.
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception
    {
        return super.decode(ctx, in);
    }
}