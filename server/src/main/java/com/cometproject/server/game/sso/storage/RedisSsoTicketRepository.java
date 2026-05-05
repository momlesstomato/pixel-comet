package com.cometproject.server.game.sso.storage;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.cache.RedisConfiguration;
import com.cometproject.api.config.sso.SsoConfiguration;
import com.cometproject.api.game.sso.ISsoTicketRepository;
import com.cometproject.api.game.sso.SsoTicket;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.game.sso.exceptions.SsoBackendUnavailableException;
import com.cometproject.server.storage.cache.CacheManager;
import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Stores pending SSO tickets as Redis string values with native expiration.
 */
public final class RedisSsoTicketRepository implements ISsoTicketRepository {
    private static final String ATOMIC_CONSUME_SCRIPT = """
            local val = redis.call('GET', KEYS[1])
            if val then
              redis.call('DEL', KEYS[1])
              return val
            end
            return nil
            """;

    private final CacheManager cacheManager;
    private final JedisPool jedisPool;
    private final String cachePrefix;
    private final String ticketKeyPrefix;

    /**
     * Creates a Redis-backed ticket repository using the shared cache manager.
     *
     * @param cacheManager The shared Redis cache manager.
     */
    @Inject
    public RedisSsoTicketRepository(final CacheManager cacheManager) {
        this(
                cacheManager,
                cacheManager.getJedisPool(),
                Configuration.currentConfig().getOrDefault(
                        RedisConfiguration.PREFIX,
                        RedisConfiguration.defaults().get(RedisConfiguration.PREFIX)),
                Configuration.currentConfig().getOrDefault(
                        SsoConfiguration.TICKET_KEY_PREFIX,
                        SsoConfiguration.defaults().get(SsoConfiguration.TICKET_KEY_PREFIX))
        );
    }

    RedisSsoTicketRepository(
            final CacheManager cacheManager,
            final JedisPool jedisPool,
            final String cachePrefix,
            final String ticketKeyPrefix) {
        this.cacheManager = cacheManager;
        this.jedisPool = jedisPool;
        this.cachePrefix = cachePrefix;
        this.ticketKeyPrefix = ticketKeyPrefix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final SsoTicket ticket) {
        final long ttlSeconds = Math.max(1L, Duration.between(Instant.now(), ticket.expiresAt()).toSeconds());

        try (Jedis jedis = this.requireJedisPool().getResource()) {
            jedis.setex(this.redisKey(ticket.ticket()), ttlSeconds, StoredTicketPayload.from(ticket).toJson());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SsoTicket> consume(final String token) {
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        }

        final Object rawValue;
        try (Jedis jedis = this.requireJedisPool().getResource()) {
            rawValue = jedis.eval(ATOMIC_CONSUME_SCRIPT, List.of(this.redisKey(token)), List.of());
        }

        if (!(rawValue instanceof String jsonValue)) {
            return Optional.empty();
        }

        return StoredTicketPayload.fromJson(jsonValue).flatMap(payload -> payload.toTicket(token));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(final String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        try (Jedis jedis = this.requireJedisPool().getResource()) {
            return jedis.exists(this.redisKey(token));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void revoke(final String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }

        try (Jedis jedis = this.requireJedisPool().getResource()) {
            jedis.del(this.redisKey(token));
        }
    }

    private JedisPool requireJedisPool() {
        if (!this.cacheManager.isEnabled() || this.jedisPool == null) {
            throw new SsoBackendUnavailableException();
        }

        return this.jedisPool;
    }

    private String redisKey(final String token) {
        return this.cachePrefix + "." + this.ticketKeyPrefix + "." + token;
    }

    private static final class StoredTicketPayload {
        @SerializedName("player_id")
        private final int playerId;

        @SerializedName("expires_at")
        private final long expiresAt;

        private StoredTicketPayload(final int playerId, final long expiresAt) {
            this.playerId = playerId;
            this.expiresAt = expiresAt;
        }

        private static StoredTicketPayload from(final SsoTicket ticket) {
            return new StoredTicketPayload(ticket.playerId(), ticket.expiresAt().toEpochMilli());
        }

        private static Optional<StoredTicketPayload> fromJson(final String json) {
            try {
                return Optional.ofNullable(JsonUtil.getInstance().fromJson(json, StoredTicketPayload.class));
            } catch (Exception exception) {
                return Optional.empty();
            }
        }

        private Optional<SsoTicket> toTicket(final String token) {
            try {
                return Optional.of(new SsoTicket(token, this.playerId, Instant.ofEpochMilli(this.expiresAt)));
            } catch (IllegalArgumentException exception) {
                return Optional.empty();
            }
        }

        private String toJson() {
            return JsonUtil.getInstance().toJson(this);
        }
    }
}