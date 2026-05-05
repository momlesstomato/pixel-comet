package com.cometproject.api.networking.connections;

import java.time.Instant;

import com.cometproject.api.networking.ciphers.ConnectionCipher;
import com.cometproject.api.networking.messages.IMessageComposer;
import com.cometproject.api.utilities.Disposable;

/**
 * Represents a single logical client connection independent of transport.
 */
public interface Connection extends Disposable {
    /**
     * Returns the stable identifier assigned when the connection was accepted.
     *
     * @return The unique connection identifier.
     */
    String getId();

    /**
     * Returns the instant the connection was created.
     *
     * @return The connection creation timestamp.
     */
    Instant getConnectedAt();

    /**
     * Returns the current lifecycle state.
     *
     * @return The current connection state.
     */
    ConnectionState getState();

    /**
     * Updates the lifecycle state tracked for this connection.
     *
     * @param state The new connection state.
     */
    void setState(ConnectionState state);

    /**
     * Returns the transport used by this connection.
     *
     * @return The active transport type.
     */
    ConnectionTransportType getTransportType();

    /**
     * Returns the remote peer IP address.
     *
     * @return The remote address string.
     */
    String getRemoteAddress();

    /**
     * Returns the cipher currently applied to the connection.
     *
     * @return The active cipher implementation.
     */
    ConnectionCipher getCipher();

    /**
     * Applies the cipher that should protect subsequent traffic.
     *
     * @param cipher The cipher to activate.
     */
    void setCipher(ConnectionCipher cipher);

    /**
     * Sends a protocol composer over this connection.
     *
     * @param composer The message to send.
     */
    void send(IMessageComposer composer);

    /**
     * Sends a raw string payload over this connection.
     *
     * @param payload The raw payload to send.
     */
    void sendRaw(String payload);

    /**
     * Flushes any queued writes.
     */
    void flush();

    /**
     * Closes the connection using a typed close reason.
     *
     * @param closeCode The reason that triggered the closure.
     */
    void close(ConnectionCloseCode closeCode);
}