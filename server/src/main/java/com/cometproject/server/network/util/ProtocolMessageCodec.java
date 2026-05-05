package com.cometproject.server.network.util;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.protocol.messages.Composer;
import com.cometproject.server.protocol.messages.MessageEvent;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Encodes and decodes hotel protocol frames independent of transport.
 */
public final class ProtocolMessageCodec {
    private ProtocolMessageCodec() {
    }

    /**
     * Encodes a composer into a transport-ready payload.
     *
     * @param composer The composer to encode.
     * @param cipher The cipher to apply to the encoded payload.
     * @return The encoded payload.
     */
    public static ByteBuffer encode(final IMessageComposer composer, final ConnectionCipher cipher) {
        ByteBuf buffer = Unpooled.buffer();

        try {
            final Composer payload = (Composer) composer.writeMessage(buffer);

            if (!payload.isFinalised()) {
                payload.content().setInt(0, payload.content().writerIndex() - 4);
            }

            if (cipher != null && cipher.isActive()) {
                buffer = cipher.encrypt(buffer);
            }

            final ByteBuffer byteBuffer = ByteBuffer.allocate(buffer.readableBytes());
            buffer.readBytes(byteBuffer);
            byteBuffer.flip();
            return byteBuffer;
        } finally {
            if (buffer.refCnt() > 0) {
                buffer.release();
            }
        }
    }

    /**
     * Decodes one or more hotel protocol packets from an inbound payload.
     *
     * @param data The inbound payload.
     * @param cipher The cipher to apply before packet parsing.
     * @return The decoded protocol events.
     */
    public static List<MessageEvent> decode(final ByteBuf data, final ConnectionCipher cipher) {
        final List<MessageEvent> events = new ArrayList<>();
        ByteBuf payload = data;

        if (cipher != null && cipher.isActive()) {
            payload = cipher.decrypt(data);
        }

        while (payload.readableBytes() >= 4) {
            payload.markReaderIndex();
            final int packetLength = payload.readInt();

            if (payload.readableBytes() < packetLength || packetLength < 2) {
                payload.resetReaderIndex();
                break;
            }

            final ByteBuf packet = payload.readBytes(packetLength);
            final short header = packet.readShort();
            events.add(new MessageEvent(header, packet.readBytes(packet.readableBytes())));
        }

        return events;
    }
}