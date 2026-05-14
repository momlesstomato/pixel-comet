package com.cometproject.server.network.registry;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.registry.ConnectionRegistry;
import com.cometproject.api.networking.registry.ConnectionRegistryEntry;

/**
 * In-memory registry implementation used when Redis is disabled.
 */
public final class InMemoryConnectionRegistry implements ConnectionRegistry {
    private final Map<String, ConnectionRegistryEntry> entries = new ConcurrentHashMap<>();

    /**
     * Executes register for this networking contract.
     *
     * @param connection Connection supplied by the caller.
     */
    @Override
    public void register(final Connection connection) {
        this.entries.put(connection.getId(), ConnectionRegistrySupport.toEntry(connection));
    }

    /**
     * Executes unregister for this networking contract.
     *
     * @param connectionId Connection id supplied by the caller.
     */
    @Override
    public void unregister(final String connectionId) {
        this.entries.remove(connectionId);
    }

    /**
     * Finds by id for this networking contract.
     *
     * @param connectionId Connection id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Optional<ConnectionRegistryEntry> findById(final String connectionId) {
        return Optional.ofNullable(this.entries.get(connectionId));
    }

    /**
     * Returns the all for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<String, ConnectionRegistryEntry> getAll() {
        return Map.copyOf(this.entries);
    }

    /**
     * Executes count for this networking contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public int count() {
        return this.entries.size();
    }
}