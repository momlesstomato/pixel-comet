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

    @Override
    public void register(final Connection connection) {
        this.entries.put(connection.getId(), ConnectionRegistrySupport.toEntry(connection));
    }

    @Override
    public void unregister(final String connectionId) {
        this.entries.remove(connectionId);
    }

    @Override
    public Optional<ConnectionRegistryEntry> findById(final String connectionId) {
        return Optional.ofNullable(this.entries.get(connectionId));
    }

    @Override
    public Map<String, ConnectionRegistryEntry> getAll() {
        return Map.copyOf(this.entries);
    }

    @Override
    public int count() {
        return this.entries.size();
    }
}