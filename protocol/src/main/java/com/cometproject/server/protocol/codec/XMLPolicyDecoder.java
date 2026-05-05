package com.cometproject.server.protocol.codec;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.connections.ConnectionCloseCode;
import com.cometproject.api.networking.connections.ConnectionState;
import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.api.networking.sessions.SessionManagerAccessor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;

import java.time.Instant;
import java.util.List;

public class XMLPolicyDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        in.markReaderIndex();
        if (in.readableBytes() < 1) return;

        byte delimiter = in.readByte();

        in.resetReaderIndex();

        if (delimiter == 0x3C) {
            ctx.channel().writeAndFlush(
                    "<?xml version=\"1.0\"?>\r\n"
                            + "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n"
                            + "<cross-domain-policy>\r\n"
                            + "<allow-access-from domain=\"*\" to-ports=\"*\" />\r\n"
                            + "</cross-domain-policy>\0"
            ).addListener(ChannelFutureListener.CLOSE);
        } else if (delimiter == 0x3f) {
            try {
                String messageStr = in.toString(CharsetUtil.UTF_8);
                String[] message = messageStr.substring(1).split("\\|\\|");

                SessionManagerAccessor.getInstance().getSessionManager().parseCommand(message, new ControlConnection(ctx));
            } catch (Exception e) {

            }
        } else {
            ctx.channel().pipeline().remove(this);
        }
    }

    private static final class ControlConnection implements Connection {
        private final ChannelHandlerContext context;

        private ControlConnection(final ChannelHandlerContext context) {
            this.context = context;
        }

        @Override
        public String getId() {
            return this.context.channel().id().asLongText();
        }

        @Override
        public Instant getConnectedAt() {
            return Instant.now();
        }

        @Override
        public ConnectionState getState() {
            return ConnectionState.AUTHENTICATING;
        }

        @Override
        public void setState(final ConnectionState state) {
        }

        @Override
        public ConnectionTransportType getTransportType() {
            return ConnectionTransportType.TCP;
        }

        @Override
        public String getRemoteAddress() {
            return this.context.channel().remoteAddress().toString();
        }

        @Override
        public ConnectionCipher getCipher() {
            return null;
        }

        @Override
        public void setCipher(final ConnectionCipher cipher) {
        }

        @Override
        public void send(final IMessageComposer composer) {
        }

        @Override
        public void sendRaw(final String payload) {
            this.context.channel().writeAndFlush(payload);
        }

        @Override
        public void flush() {
            this.context.flush();
        }

        @Override
        public void close(final ConnectionCloseCode closeCode) {
            this.context.close();
        }

        @Override
        public void dispose() {
            this.context.close();
        }
    }
}