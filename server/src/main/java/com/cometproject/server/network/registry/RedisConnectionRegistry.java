package com.cometproject.server.network.registry;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.cache.RedisConfiguration;
import com.cometproject.api.config.network.ConnectionRegistryConfiguration;
import com.cometproject.api.networking.connections.Connection;
import com.cometproject.api.networking.connections.ConnectionState;
import com.cometproject.api.networking.connections.ConnectionTransportType;
import com.cometproject.api.networking.registry.ConnectionRegistry;
import com.cometproject.api.networking.registry.ConnectionRegistryEntry;
import com.cometproject.api.utilities.Disposable;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis-backed registry implementation for active transport connections.
 */
public final class RedisConnectionRegistry implements ConnectionRegistry, Disposable {
    private final JedisPool jedisPool;
    private final String keyPrefix;
    private final int ttlSeconds;

    /**
     * Creates a Redis-backed registry using the configured cache connection.
     */
    public RedisConnectionRegistry() {
        this.keyPrefix = String.valueOf(Configuration.currentConfig().getOrDefault(
                RedisConfiguration.PREFIX,
                RedisConfiguration.defaults().get(RedisConfiguration.PREFIX)
        ));
        this.ttlSeconds = Integer.parseInt(String.valueOf(Configuration.currentConfig().getOrDefault(
                ConnectionRegistryConfiguration.TTL_SECONDS,
                ConnectionRegistryConfiguration.defaults().get(ConnectionRegistryConfiguration.TTL_SECONDS)
        )));

        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(128);
        poolConfig.setMaxWaitMillis(1000);

        this.jedisPool = new JedisPool(
                poolConfig,
                String.valueOf(Configuration.currentConfig().getOrDefault(
                        RedisConfiguration.CONNECTION_HOST,
                        RedisConfiguration.defaults().get(RedisConfiguration.CONNECTION_HOST)
                )),
                Integer.parseInt(String.valueOf(Configuration.currentConfig().getOrDefault(
                        RedisConfiguration.CONNECTION_PORT,
                        RedisConfiguration.defaults().get(RedisConfiguration.CONNECTION_PORT)
                ))),
                3000
        );
    }

    /**
     * Executes register for this networking contract.
     *
     * @param connection Connection supplied by the caller.
     */
    @Override
    public void register(final Connection connection) {
        final ConnectionRegistryEntry entry = ConnectionRegistrySupport.toEntry(connection);
        final Map<String, String> values = new HashMap<>();
        values.put("remote_address", entry.remoteAddress());
        values.put("connected_at", entry.connectedAt().toString());
        values.put("state", entry.state().name());
        values.put("transport", entry.transportType().name());

        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.hset(this.key(connection.getId()), values);
            jedis.expire(this.key(connection.getId()), this.ttlSeconds);
        }
    }

    /**
     * Executes unregister for this networking contract.
     *
     * @param connectionId Connection id supplied by the caller.
     */
    @Override
    public void unregister(final String connectionId) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            jedis.del(this.key(connectionId));
        }
    }

    /**
     * Finds by id for this networking contract.
     *
     * @param connectionId Connection id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public Optional<ConnectionRegistryEntry> findById(final String connectionId) {
        try (Jedis jedis = this.jedisPool.getResource()) {
            final Map<String, String> data = jedis.hgetAll(this.key(connectionId));

            if (data == null || data.isEmpty()) {
                return Optional.empty();
            }

            return Optional.of(this.toEntry(connectionId, data));
        }
    }

    /**
     * Returns the all for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<String, ConnectionRegistryEntry> getAll() {
        final Map<String, ConnectionRegistryEntry> entries = new HashMap<>();

        try (Jedis jedis = this.jedisPool.getResource()) {
            for (String key : jedis.keys(this.key("*"))) {
                final String connectionId = key.substring(key.lastIndexOf(':') + 1);
                this.findById(connectionId).ifPresent(entry -> entries.put(connectionId, entry));
            }
        }

        return Map.copyOf(entries);
    }

    /**
     * Executes count for this networking contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public int count() {
        try (Jedis jedis = this.jedisPool.getResource()) {
            return jedis.keys(this.key("*")).size();
        }
    }

    /**
     * Releases resources owned by this networking component.
     */
    @Override
    public void dispose() {
        this.jedisPool.close();
    }

    private String key(final String connectionId) {
        return this.keyPrefix + ":conn:" + connectionId;
    }

    private ConnectionRegistryEntry toEntry(final String connectionId, final Map<String, String> data) {
        return new ConnectionRegistryEntry(
                connectionId,
                data.getOrDefault("remote_address", "0.0.0.0"),
                Instant.parse(data.getOrDefault("connected_at", Instant.now().toString())),
                ConnectionState.valueOf(data.getOrDefault("state", ConnectionState.CONNECTING.name())),
                ConnectionTransportType.valueOf(data.getOrDefault("transport", ConnectionTransportType.TCP.name()))
        );
    }
}