package com.cometproject.server.storage.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.cache.RedisConfiguration;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.storage.cache.subscribers.GoToRoomSubscriber;
import com.cometproject.server.storage.cache.subscribers.ISubscriber;
import com.cometproject.server.storage.cache.subscribers.RefreshDataSubscriber;
import com.cometproject.server.tasks.CometThreadManager;
import com.cometproject.server.utilities.TimeSpan;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Manages cache runtime state for the storage subsystem.
 */
public class CacheManager extends CachableObject implements Startable {
    private final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class.getName());
    private final String keyPrefix;
    private final String host;
    private final int port;
    private JedisPool jedisPool;

    /**
     * Creates a cache manager instance for the storage subsystem.
     */
    public CacheManager() {
        this.keyPrefix = Configuration.currentConfig().getOrDefault(RedisConfiguration.PREFIX, RedisConfiguration.defaults().get(RedisConfiguration.PREFIX));
        this.host = Configuration.currentConfig().getOrDefault(RedisConfiguration.CONNECTION_HOST, RedisConfiguration.defaults().get(RedisConfiguration.CONNECTION_HOST));
        this.port = Integer.parseInt(Configuration.currentConfig().getOrDefault(RedisConfiguration.CONNECTION_PORT, RedisConfiguration.defaults().get(RedisConfiguration.CONNECTION_PORT)));
    }

    /**
     * Returns the instance for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public static CacheManager getInstance() {
        return CometBootstrap.resolve(CacheManager.class);
    }

    /**
     * Starts this storage component.
     */
    @Override
    public void start() {
        if (this.host.isEmpty()) {
            throw new IllegalStateException("Redis host is not configured (COMET_CACHE_CONNECTION_HOST)");
        }

        this.initializeJedis();

        this.doSubscriptions(new ISubscriber[]{
                new RefreshDataSubscriber(),
                new GoToRoomSubscriber()
        });

        LOGGER.info("Redis connected on {}:{}", this.host, this.port);
    }

    /**
     * Stops this storage component.
     */
    @Override
    public void stop() {
        if (this.jedisPool != null) {
            this.jedisPool.close();
        }
    }

    private void initializeJedis() {
        try {
            final JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(1000);
            poolConfig.setMaxWaitMillis(1000);

            this.jedisPool = new JedisPool(poolConfig, this.host, this.port, 3000);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to connect to Redis at " + this.host + ":" + this.port, e);
        }
    }

    private void doSubscriptions(ISubscriber[] subscribers) {
        for (ISubscriber subscriber : subscribers) {
            subscriber.setJedis(this.jedisPool);

            CometThreadManager.getInstance().executeOnce(subscriber::subscribe);

            LOGGER.info("Subscriber " + subscriber.getClass().getSimpleName() + " initialized");
        }
    }

    /**
     * Executes put for this storage contract.
     *
     * @param key Key supplied by the caller.
     * @param object Object supplied by the caller.
     */
    public void put(final String key, CachableObject object) {
        if (this.jedisPool == null) {
            return;
        }

        try {
            try (final Jedis connection = this.jedisPool.getResource()) {
                final long startTime = System.currentTimeMillis();
                final String objectData = object.toString();
                connection.set(this.getKey(key), objectData);
                LOGGER.info("Data put to redis: " + object.getClass().getSimpleName() + " in " + new TimeSpan(startTime, System.currentTimeMillis()).toMilliseconds() + "ms");
            }
        } catch (Exception e) {
            LOGGER.error("Error while setting object in Redis with key: " + key + ", type: " + object.getClass().getSimpleName(), e);
        }
    }

    /**
     * Executes publish string for this storage contract.
     *
     * @param key Key supplied by the caller.
     * @param value Value supplied by the caller.
     * @param setter Setter supplied by the caller.
     * @param setterKey Setter key supplied by the caller.
     */
    public void publishString(final String key, final String value, boolean setter, String setterKey) {
        if (this.jedisPool == null) {
            return;
        }

        try {
            try (final Jedis connection = this.jedisPool.getResource()) {
                final long startTime = System.currentTimeMillis();
                connection.publish(this.getKey(key), value);

                if (setter && setterKey != null) {
                    connection.set(this.getKey(setterKey), value);
                }

                LOGGER.info("Data published to redis channel: " + key + " in " + new TimeSpan(startTime, System.currentTimeMillis()).toMilliseconds() + "ms");
            }
        } catch (Exception e) {
            LOGGER.error("Error while setting string with key: " + key, e);
        }
    }

    /**
     * Executes put string for this storage contract.
     *
     * @param key Key supplied by the caller.
     * @param value Value supplied by the caller.
     */
    public void putString(final String key, final String value) {
        if (this.jedisPool == null) {
            return;
        }

        try {
            try (final Jedis connection = this.jedisPool.getResource()) {
                final long startTime = System.currentTimeMillis();
                connection.set(this.getKey(key), value);
                LOGGER.info("Data put to redis with key: " + key + " in " + new TimeSpan(startTime, System.currentTimeMillis()).toMilliseconds() + "ms");
            }
        } catch (Exception e) {
            LOGGER.error("Error while setting string with key: " + key, e);
        }
    }

    /**
     * Returns the string for this storage contract.
     *
     * @param key Key supplied by the caller.
     * @return Value exposed by the contract.
     */
    public String getString(String key) {
        try {
            try (final Jedis connection = this.jedisPool.getResource()) {
                return connection.get(this.getKey(key));
            }
        } catch (Exception e) {
            LOGGER.error("Error while reading string from Redis with key: " + key, e);
        }

        return null;
    }

    /**
     * Executes get for this storage contract.
     *
     * @param clazz Clazz supplied by the caller.
     * @param key Key supplied by the caller.
     * @return Value exposed by the contract.
     */
    public <T> T get(final Class<T> clazz, final String key) {
        try {
            try (final Jedis connection = this.jedisPool.getResource()) {
                final String data = connection.get(this.getKey(key));
                final T object = JsonUtil.getInstance().fromJson(data, clazz);

                if (object != null) {
                    return object;
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while reading object from Redis with key: " + key + ", type: " + clazz.getSimpleName(), e);
        }

        return null;
    }

    /**
     * Executes exists for this storage contract.
     *
     * @param key Key supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean exists(final String key) {
        try {
            try (final Jedis connection = this.jedisPool.getResource()) {
                return connection.exists(getKey(key));
            }
        } catch (Exception e) {
            LOGGER.error("Error while reading EXISTS from redis, key: " + key, e);
        }

        return false;
    }

    private String getKey(final String key) {
        return this.keyPrefix + "." + key;
    }

    /**
     * Returns a prefixed Redis key using the configured cache namespace.
     *
     * @param key The logical key suffix.
     * @return The prefixed Redis key.
     */
    public String qualifyKey(final String key) {
        return this.getKey(key);
    }

    /**
     * Returns the shared Jedis connection pool.
     *
     * @return The shared Jedis pool.
     */
    public JedisPool getJedisPool() {
        return this.jedisPool;
    }
}
