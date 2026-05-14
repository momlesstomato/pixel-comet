package com.cometproject.server.network.connections;

import java.net.InetSocketAddress;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.api.networking.connections.ConnectionCloseCode;
import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.network.ciphers.Rc4ConnectionCipher;
import com.cometproject.server.protocol.codec.EncryptionDecoder;
import com.cometproject.server.protocol.codec.EncryptionEncoder;

import io.netty.channel.ChannelHandlerContext;

/**
 * Connection adapter for the Netty TCP transport.
 */
public final class NettyTcpConnection extends AbstractConnection {
    private final ChannelHandlerContext context;

    /**
     * Creates a TCP connection wrapper for a Netty channel.
     *
     * @param context The backing Netty channel context.
     */
    public NettyTcpConnection(final ChannelHandlerContext context) {
        super(ConnectionTransportType.TCP);
        this.context = context;
    }

    /**
     * Returns the backing Netty channel context for server-internal adapters.
     *
     * @return The underlying channel context.
     */
    public ChannelHandlerContext getContext() {
        return this.context;
    }

    /**
     * Returns the remote address for this network connection contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getRemoteAddress() {
        return ((InetSocketAddress) this.context.channel().remoteAddress()).getAddress().getHostAddress();
    }

    /**
     * Executes flush for this network connection contract.
     */
    @Override
    public void flush() {
        this.context.flush();
    }

    /**
     * Handles the cipher changed callback for this network connection contract.
     *
     * @param cipher Cipher supplied by the caller.
     */
    @Override
    protected void onCipherChanged(final ConnectionCipher cipher) {
        if (!(cipher instanceof Rc4ConnectionCipher)) {
            return;
        }

        final Rc4ConnectionCipher rc4Cipher = (Rc4ConnectionCipher) cipher;

        if (this.context.pipeline().get("encryptionDecoder") == null) {
            this.context.pipeline().addFirst("encryptionDecoder", new EncryptionDecoder(rc4Cipher));
        }

        if (this.context.pipeline().get("encryptionEncoder") == null) {
            this.context.pipeline().addFirst("encryptionEncoder", new EncryptionEncoder(rc4Cipher));
        }
    }

    /**
     * Executes send internal for this network connection contract.
     *
     * @param composer Composer supplied by the caller.
     */
    @Override
    protected void sendInternal(final IMessageComposer composer) {
        this.context.writeAndFlush(composer, this.context.voidPromise());
    }

    /**
     * Executes send raw internal for this network connection contract.
     *
     * @param payload Payload supplied by the caller.
     */
    @Override
    protected void sendRawInternal(final String payload) {
        this.context.writeAndFlush(payload, this.context.voidPromise());
    }

    /**
     * Executes close internal for this network connection contract.
     *
     * @param closeCode Close code supplied by the caller.
     */
    @Override
    protected void closeInternal(final ConnectionCloseCode closeCode) {
        this.context.close();
    }
}