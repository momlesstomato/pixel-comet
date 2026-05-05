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

public class CacheManager extends CachableObject implements Startable {
    private final Logger LOGGER = LoggerFactory.getLogger(CacheManager.class.getName());
    private final String keyPrefix;
    private final String host;
    private final int port;
    private boolean enabled;
    private JedisPool jedis;

    public CacheManager() {
        this.enabled = Boolean.parseBoolean(Configuration.currentConfig().getOrDefault(RedisConfiguration.ENABLED, RedisConfiguration.defaults().get(RedisConfiguration.ENABLED)));
        this.keyPrefix = Configuration.currentConfig().getOrDefault(RedisConfiguration.PREFIX, RedisConfiguration.defaults().get(RedisConfiguration.PREFIX));
        this.host = Configuration.currentConfig().getOrDefault(RedisConfiguration.CONNECTION_HOST, RedisConfiguration.defaults().get(RedisConfiguration.CONNECTION_HOST));
        this.port = Integer.parseInt(Configuration.currentConfig().getOrDefault(RedisConfiguration.CONNECTION_PORT, RedisConfiguration.defaults().get(RedisConfiguration.CONNECTION_PORT)));
    }

    public static CacheManager getInstance() {
        return CometBootstrap.resolve(CacheManager.class);
    }

    @Override
    public void start() {
        if (!this.enabled) {
            LOGGER.info("Redis cache is disabled.");
            return;
        }

        if (this.host.isEmpty()) {
            LOGGER.error("Invalid redis connection string");

            this.enabled = false;
            return;
        }

        if (!this.initializeJedis()) {
            LOGGER.error("Failed to initialize Redis cluster, disabling caching");

            this.enabled = false;
            return;
        }

        this.doSubscriptions(new ISubscriber[]{
                new RefreshDataSubscriber(),
                new GoToRoomSubscriber()
        });

        LOGGER.info("Redis caching is enabled");

    }

    @Override
    public void stop() {
        if (this.jedis != null) {
            this.jedis.close();
        }
    }

    private boolean initializeJedis() {
        try {
            final JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(1000);

            // Wait 100ms before we fall back to MySQL.
            poolConfig.setMaxWaitMillis(1000);

            this.jedis = new JedisPool(poolConfig, this.host, this.port, 3000);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void doSubscriptions(ISubscriber[] subscribers) {
        for (ISubscriber subscriber : subscribers) {
            subscriber.setJedis(this.jedis);

            CometThreadManager.getInstance().executeOnce(subscriber::subscribe);

            LOGGER.info("Subscriber " + subscriber.getClass().getSimpleName() + " initialized");
        }
    }

    public void put(final String key, CachableObject object) {
        if (this.jedis == null) {
            return;
        }

        try {
            try (final Jedis jedis = this.jedis.getResource()) {
                final long startTime = System.currentTimeMillis();

                // Build the String from the object
                final String objectData = object.toString();

                jedis.set(this.getKey(key), objectData);

                LOGGER.info("Data put to redis: " + object.getClass().getSimpleName() + " in " + new TimeSpan(startTime, System.currentTimeMillis()).toMilliseconds() + "ms");
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            LOGGER.error("Error while setting object in Redis with key: " + key + ", type: " +
                    object.getClass().getSimpleName(), e);
        }
    }

    public void publishString(final String key, final String value, boolean setter, String setterKey) {
        if (this.jedis == null) {
            return;
        }

        try {
            try (final Jedis jedis = this.jedis.getResource()) {
                final long startTime = System.currentTimeMillis();

                jedis.publish(this.getKey(key), value);

                if (setter && setterKey != null)
                    jedis.set(this.getKey(setterKey), value);

                LOGGER.info("Data published to redis channel: " + key + " in " + new TimeSpan(startTime, System.currentTimeMillis()).toMilliseconds() + "ms");
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            LOGGER.error("Error while setting string with key: " + key, e);
        }
    }

    public void putString(final String key, final String value) {
        if (this.jedis == null) {
            return;
        }

        try {
            try (final Jedis jedis = this.jedis.getResource()) {
                final long startTime = System.currentTimeMillis();

                jedis.set(this.getKey(key), value);

                LOGGER.info("Data put to redis with key: " + key + " in " + new TimeSpan(startTime, System.currentTimeMillis()).toMilliseconds() + "ms");
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            LOGGER.error("Error while setting string with key: " + key, e);
        }
    }

    public String getString(String key) {
        try {
            try (final Jedis jedis = this.jedis.getResource()) {
                return jedis.get(this.getKey(key));
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            LOGGER.error("Error while reading string from Redis with key: " + key, e);
        }

        return null;
    }

    public <T> T get(final Class<T> clazz, final String key) {
        try {
            try (final Jedis jedis = this.jedis.getResource()) {
                final String data = jedis.get(this.getKey(key));

                // Build the object from the String.
                final T object = JsonUtil.getInstance().fromJson(data, clazz);

                if (object != null) {
                    return object;
                }
            } catch (Exception e) {
                throw e;
            }
        } catch (Exception e) {
            LOGGER.error("Error while reading object from Redis with key: " + key + ", type: " + clazz.getSimpleName(), e);
        }

        return null;
    }

    public boolean exists(final String key) {
        try {
            try (final Jedis jedis = this.jedis.getResource()) {
                return jedis.exists(getKey(key));
            } catch (Exception e) {
                throw e;
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
     * Returns the shared Jedis pool when Redis has been initialised.
     *
     * @return The shared Jedis pool, or {@code null} when Redis is unavailable.
     */
    public JedisPool getJedisPool() {
        return this.jedis;
    }

    public boolean isEnabled() {
        return this.enabled;
    }
}
