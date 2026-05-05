package com.cometproject.server.network.connections;

import com.cometproject.api.networking.connections.ConnectionCloseCode;
import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.network.util.ProtocolMessageCodec;
import io.javalin.websocket.WsContext;

import java.nio.ByteBuffer;

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

    @Override
    public String getRemoteAddress() {
        return this.context.host();
    }

    @Override
    public void flush() {
    }

    @Override
    protected void sendInternal(final IMessageComposer composer) {
        final ByteBuffer payload = ProtocolMessageCodec.encode(composer, this.getCipher());
        this.context.send(payload);
    }

    @Override
    protected void sendRawInternal(final String payload) {
        this.context.send(payload);
    }

    @Override
    protected void closeInternal(final ConnectionCloseCode closeCode) {
        this.context.closeSession();
    }
}