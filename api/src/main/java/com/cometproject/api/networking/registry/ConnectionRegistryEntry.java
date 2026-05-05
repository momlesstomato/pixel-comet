package com.cometproject.api.networking.registry;

import java.time.Instant;

import com.cometproject.api.networking.connections.ConnectionState;
import com.cometproject.api.networking.connections.ConnectionTransportType;

/**
 * Captures the immutable metadata stored for a registered connection.
 *
 * @param connectionId The unique connection identifier.
 * @param remoteAddress The remote IP address.
 * @param connectedAt The timestamp the connection was accepted.
 * @param state The latest observed connection state.
 * @param transportType The transport backing the connection.
 */
public record ConnectionRegistryEntry(
        String connectionId,
        String remoteAddress,
        Instant connectedAt,
        ConnectionState state,
        ConnectionTransportType transportType
) {
}