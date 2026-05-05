package com.cometproject.api.networking.registry;

import com.cometproject.api.networking.connections.Connection;

import java.util.Map;
import java.util.Optional;

/**
 * Stores metadata for currently active client connections.
 */
public interface ConnectionRegistry {
    /**
     * Records a newly opened connection.
     *
     * @param connection The connection to register.
     */
    void register(Connection connection);

    /**
     * Removes a connection from the registry.
     *
     * @param connectionId The identifier of the connection to remove.
     */
    void unregister(String connectionId);

    /**
     * Looks up a registry entry by connection identifier.
     *
     * @param connectionId The connection identifier to resolve.
     * @return The registry entry when present.
     */
    Optional<ConnectionRegistryEntry> findById(String connectionId);

    /**
     * Returns a snapshot of all registered connections.
     *
     * @return All registry entries keyed by connection identifier.
     */
    Map<String, ConnectionRegistryEntry> getAll();

    /**
     * Returns the number of active registry entries.
     *
     * @return The current registry size.
     */
    int count();
}