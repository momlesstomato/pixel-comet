package com.cometproject.server.network.connections;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.connections.ConnectionCloseCode;
import com.cometproject.api.networking.connections.ConnectionState;
import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.server.network.ciphers.NullConnectionCipher;

/**
 * Base connection implementation shared by all transport adapters.
 */
public abstract class AbstractConnection implements Connection {
    private final String id = UUID.randomUUID().toString();
    private final Instant connectedAt = Instant.now();
    private final ConnectionTransportType transportType;
    private final AtomicReference<ConnectionState> state = new AtomicReference<>(ConnectionState.CONNECTING);
    private final AtomicReference<ConnectionCipher> cipher = new AtomicReference<>(NullConnectionCipher.INSTANCE);

    protected AbstractConnection(final ConnectionTransportType transportType) {
        this.transportType = transportType;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public Instant getConnectedAt() {
        return this.connectedAt;
    }

    @Override
    public ConnectionState getState() {
        return this.state.get();
    }

    @Override
    public void setState(final ConnectionState state) {
        this.state.set(state);
    }

    @Override
    public ConnectionTransportType getTransportType() {
        return this.transportType;
    }

    @Override
    public ConnectionCipher getCipher() {
        return this.cipher.get();
    }

    @Override
    public void setCipher(final ConnectionCipher cipher) {
        final ConnectionCipher resolvedCipher = cipher == null ? NullConnectionCipher.INSTANCE : cipher;
        this.cipher.set(resolvedCipher);
        this.onCipherChanged(resolvedCipher);
    }

    @Override
    public void send(final IMessageComposer composer) {
        if (!this.canWrite()) {
            return;
        }

        try {
            this.sendInternal(composer);
        } catch (Exception exception) {
            this.markClosed();
        }
    }

    @Override
    public void sendRaw(final String payload) {
        if (!this.canWrite()) {
            return;
        }

        try {
            this.sendRawInternal(payload);
        } catch (Exception exception) {
            this.markClosed();
        }
    }

    @Override
    public void close(final ConnectionCloseCode closeCode) {
        if (!this.state.compareAndSet(ConnectionState.CONNECTING, ConnectionState.CLOSING)
                && !this.state.compareAndSet(ConnectionState.AUTHENTICATING, ConnectionState.CLOSING)
                && !this.state.compareAndSet(ConnectionState.AUTHENTICATED, ConnectionState.CLOSING)) {
            return;
        }

        try {
            this.closeInternal(closeCode);
        } catch (Exception exception) {
            // The transport may already be gone; the logical connection is still closed.
        } finally {
            this.markClosed();
        }
    }

    @Override
    public void dispose() {
        this.close(ConnectionCloseCode.NORMAL);
    }

    /**
     * Handles transport-specific cipher activation work.
     *
     * @param cipher The cipher that has just been applied.
     */
    protected void onCipherChanged(final ConnectionCipher cipher) {
    }

    /**
     * Returns whether this connection can still accept outgoing writes.
     *
     * @return true when the logical connection is not closing or closed.
     */
    protected boolean canWrite() {
        final ConnectionState currentState = this.state.get();
        return currentState != ConnectionState.CLOSING && currentState != ConnectionState.CLOSED;
    }

    /**
     * Marks the logical connection closed without invoking transport close callbacks.
     */
    protected void markClosed() {
        this.state.set(ConnectionState.CLOSED);
    }

    /**
     * Performs the transport-specific send operation.
     *
     * @param composer The protocol message to send.
     */
    protected abstract void sendInternal(IMessageComposer composer);

    /**
     * Performs the transport-specific raw send operation.
     *
     * @param payload The payload to send.
     */
    protected abstract void sendRawInternal(String payload);

    /**
     * Performs the transport-specific close operation.
     *
     * @param closeCode The reason for closing.
     */
    protected abstract void closeInternal(ConnectionCloseCode closeCode);
}
