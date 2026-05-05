package com.cometproject.server.network.registry;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.registry.ConnectionRegistryEntry;

/**
 * Shared helpers for connection registry implementations.
 */
public final class ConnectionRegistrySupport {
    private ConnectionRegistrySupport() {
    }

    /**
     * Converts a live connection into an immutable registry entry snapshot.
     *
     * @param connection The live connection.
     * @return The registry entry snapshot.
     */
    public static ConnectionRegistryEntry toEntry(final Connection connection) {
        return new ConnectionRegistryEntry(
                connection.getId(),
                connection.getRemoteAddress(),
                connection.getConnectedAt(),
                connection.getState(),
                connection.getTransportType()
        );
    }
}