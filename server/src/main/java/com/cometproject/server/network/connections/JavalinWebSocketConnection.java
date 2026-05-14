package com.cometproject.server.network.connections;

import java.nio.ByteBuffer;

import com.cometproject.api.networking.connections.ConnectionCloseCode;
import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.network.util.ProtocolMessageCodec;

import io.javalin.websocket.WsContext;

/**
 * Connection adapter for the binary hotel WebSocket transport.
 */
public final class JavalinWebSocketConnection extends AbstractConnection {
    private final WsContext context;

    /**
     * Creates a WebSocket connection wrapper for a Javalin context.
     *
     * @param context The backing Javalin WebSocket context.
     */
    public JavalinWebSocketConnection(final WsContext context) {
        super(ConnectionTransportType.WEBSOCKETS);
        this.context = context;
    }

    /**
     * Returns the remote address for this network connection contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getRemoteAddress() {
        return this.context.host();
    }

    /**
     * Executes flush for this network connection contract.
     */
    @Override
    public void flush() {
    }

    /**
     * Executes send internal for this network connection contract.
     *
     * @param composer Composer supplied by the caller.
     */
    @Override
    protected void sendInternal(final IMessageComposer composer) {
        if (!this.context.session.isOpen()) {
            this.markClosed();
            return;
        }

        final ByteBuffer payload = ProtocolMessageCodec.encode(composer, this.getCipher());
        this.context.send(payload);
    }

    /**
     * Executes send raw internal for this network connection contract.
     *
     * @param payload Payload supplied by the caller.
     */
    @Override
    protected void sendRawInternal(final String payload) {
        if (!this.context.session.isOpen()) {
            this.markClosed();
            return;
        }

        this.context.send(payload);
    }

    /**
     * Executes close internal for this network connection contract.
     *
     * @param closeCode Close code supplied by the caller.
     */
    @Override
    protected void closeInternal(final ConnectionCloseCode closeCode) {
        if (!this.context.session.isOpen()) {
            return;
        }

        this.context.closeSession();
    }
}
