package com.cometproject.api.networking.connections;

/**
 * Enumerates the supported reasons for closing a client connection.
 */
public enum ConnectionCloseCode {
    NORMAL,
    AUTHENTICATION_FAILED,
    HANDSHAKE_FAILED,
    IDLE_TIMEOUT,
    PROTOCOL_ERROR,
    SERVER_SHUTDOWN
}