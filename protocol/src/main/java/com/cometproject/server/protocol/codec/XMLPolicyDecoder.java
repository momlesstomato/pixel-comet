package com.cometproject.server.protocol.codec;

import java.time.Instant;
import java.util.List;

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

/**
 * Describes XML policy decoder behavior for the protocol codec subsystem.
 */
public class XMLPolicyDecoder extends ByteToMessageDecoder {

    /**
     * Decodes inbound bytes into the next protocol object.
     *
     * @param ctx Netty channel context for the current operation.
     * @param in Inbound byte buffer being decoded.
     * @param out Output collection receiving decoded protocol objects.
     */
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

        /**
         * Returns the outgoing Pixel Protocol message id.
         *
         * @return Outgoing message id registered in the protocol header table.
         */
        @Override
        public String getId() {
            return this.context.channel().id().asLongText();
        }

        /**
         * Returns the connected at for this protocol codec contract.
         *
         * @return Value exposed by the contract.
         */
        @Override
        public Instant getConnectedAt() {
            return Instant.now();
        }

        /**
         * Returns the state for this protocol codec contract.
         *
         * @return Value exposed by the contract.
         */
        @Override
        public ConnectionState getState() {
            return ConnectionState.AUTHENTICATING;
        }

        /**
         * Updates the state for this protocol codec contract.
         *
         * @param state State supplied by the caller.
         */
        @Override
        public void setState(final ConnectionState state) {
        }

        /**
         * Returns the transport type for this protocol codec contract.
         *
         * @return Value exposed by the contract.
         */
        @Override
        public ConnectionTransportType getTransportType() {
            return ConnectionTransportType.TCP;
        }

        /**
         * Returns the remote address for this protocol codec contract.
         *
         * @return Value exposed by the contract.
         */
        @Override
        public String getRemoteAddress() {
            return this.context.channel().remoteAddress().toString();
        }

        /**
         * Returns the cipher for this protocol codec contract.
         *
         * @return Value exposed by the contract.
         */
        @Override
        public ConnectionCipher getCipher() {
            return null;
        }

        /**
         * Updates the cipher for this protocol codec contract.
         *
         * @param cipher Cipher supplied by the caller.
         */
        @Override
        public void setCipher(final ConnectionCipher cipher) {
        }

        /**
         * Executes send for this protocol codec contract.
         *
         * @param composer Composer supplied by the caller.
         */
        @Override
        public void send(final IMessageComposer composer) {
        }

        /**
         * Executes send raw for this protocol codec contract.
         *
         * @param payload Payload supplied by the caller.
         */
        @Override
        public void sendRaw(final String payload) {
            this.context.channel().writeAndFlush(payload);
        }

        /**
         * Executes flush for this protocol codec contract.
         */
        @Override
        public void flush() {
            this.context.flush();
        }

        /**
         * Executes close for this protocol codec contract.
         *
         * @param closeCode Close code supplied by the caller.
         */
        @Override
        public void close(final ConnectionCloseCode closeCode) {
            this.context.close();
        }

        /**
         * Releases references held by this protocol message.
         */
        @Override
        public void dispose() {
            this.context.close();
        }
    }
}