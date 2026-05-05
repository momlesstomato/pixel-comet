package com.cometproject.api.networking.connections;

/**
 * Enumerates the lifecycle states of a client connection.
 */
public enum ConnectionState {
    CONNECTING,
    AUTHENTICATING,
    AUTHENTICATED,
    CLOSING,
    CLOSED
}