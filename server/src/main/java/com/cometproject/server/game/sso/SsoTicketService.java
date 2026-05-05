package com.cometproject.server.game.sso;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.HexFormat;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.sso.SsoConfiguration;
import com.cometproject.api.game.sso.ISsoTicketRepository;
import com.cometproject.api.game.sso.ISsoTicketService;
import com.cometproject.api.game.sso.SsoTicket;
import com.cometproject.api.utilities.JsonUtil;
import com.cometproject.server.game.sso.exceptions.SsoBackendUnavailableException;
import com.cometproject.server.storage.cache.CacheManager;
import com.google.gson.annotations.SerializedName;
import com.google.inject.Inject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Coordinates Redis-backed SSO tickets and longer-lived session tokens.
 */
public final class SsoTicketService implements ISsoTicketService {
    private final ISsoTicketRepository repository;
    private final CacheManager cacheManager;
    private final Clock clock;
    private final SecureRandom secureRandom;
    private final int sessionTtlSeconds;
    private final String sessionKeyPrefix;

    /**
     * Creates a new SSO ticket service using the shared Redis cache.
     *
     * @param repository The pending SSO ticket repository.
     * @param cacheManager The shared Redis cache manager.
     */
    @Inject
    public SsoTicketService(final ISsoTicketRepository repository, final CacheManager cacheManager) {
        this(
                repository,
                cacheManager,
                Clock.systemUTC(),
                new SecureRandom(),
                Integer.parseInt(Configuration.currentConfig().getOrDefault(
                        SsoConfiguration.SESSION_TTL_SECONDS,
                        SsoConfiguration.defaults().get(SsoConfiguration.SESSION_TTL_SECONDS))),
                Configuration.currentConfig().getOrDefault(
                        SsoConfiguration.SESSION_KEY_PREFIX,
                        SsoConfiguration.defaults().get(SsoConfiguration.SESSION_KEY_PREFIX))
        );
    }

    SsoTicketService(
            final ISsoTicketRepository repository,
            final CacheManager cacheManager,
            final Clock clock,
            final SecureRandom secureRandom,
            final int sessionTtlSeconds,
            final String sessionKeyPrefix) {
        this.repository = repository;
        this.cacheManager = cacheManager;
        this.clock = clock;
        this.secureRandom = secureRandom;
        this.sessionTtlSeconds = sessionTtlSeconds;
        this.sessionKeyPrefix = sessionKeyPrefix;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsoTicket issue(final int playerId, final int ttlSeconds) {
        this.validateTicketRequest(playerId, ttlSeconds);
        this.assertBackendAvailable();

        final SsoTicket ticket = new SsoTicket(this.generateToken(), playerId, this.clock.instant().plusSeconds(ttlSeconds));
        this.repository.save(ticket);
        return ticket;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SsoTicket> consume(final String token) {
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        }

        return this.repository.consume(token.trim());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<SsoTicket> consume(final String token, final int playerId) {
        return this.consume(token).filter(ticket -> ticket.playerId() == playerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void revoke(final String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }

        final String normalizedToken = token.trim();
        this.repository.revoke(normalizedToken);

        if (!this.cacheManager.isEnabled() || this.cacheManager.getJedisPool() == null) {
            return;
        }

        try (Jedis jedis = this.requireJedisPool().getResource()) {
            jedis.del(this.sessionRedisKey(normalizedToken));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsoTicket createSessionToken(final int playerId) {
        return this.createSessionToken(playerId, this.generateToken());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SsoTicket createSessionToken(final int playerId, final String token) {
        if (playerId <= 0) {
            throw new IllegalArgumentException("playerId must be greater than zero");
        }

        if (this.sessionTtlSeconds <= 0) {
            throw new IllegalArgumentException("sessionTtlSeconds must be greater than zero");
        }

        if (StringUtils.isBlank(token)) {
            throw new IllegalArgumentException("token must not be blank");
        }

        this.assertBackendAvailable();

        final SsoTicket ticket = new SsoTicket(token.trim(), playerId, this.clock.instant().plusSeconds(this.sessionTtlSeconds));

        try (Jedis jedis = this.requireJedisPool().getResource()) {
            jedis.setex(this.sessionRedisKey(ticket.ticket()), this.sessionTtlSeconds, new SessionTokenPayload(playerId, ticket.expiresAt().toEpochMilli()).toJson());
        }

        return ticket;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> resolvePlayerId(final String token) {
        if (StringUtils.isBlank(token)) {
            return Optional.empty();
        }

        this.assertBackendAvailable();

        final String jsonValue;
        try (Jedis jedis = this.requireJedisPool().getResource()) {
            jsonValue = jedis.get(this.sessionRedisKey(token.trim()));
        }

        return SessionTokenPayload.fromJson(jsonValue)
                .filter(payload -> payload.expiresAt > this.clock.instant().toEpochMilli())
                .map(payload -> payload.playerId);
    }

    private void validateTicketRequest(final int playerId, final int ttlSeconds) {
        if (playerId <= 0) {
            throw new IllegalArgumentException("playerId must be greater than zero");
        }

        if (ttlSeconds <= 0) {
            throw new IllegalArgumentException("ttlSeconds must be greater than zero");
        }
    }

    private void assertBackendAvailable() {
        if (!this.cacheManager.isEnabled() || this.cacheManager.getJedisPool() == null) {
            throw new SsoBackendUnavailableException();
        }
    }

    private JedisPool requireJedisPool() {
        this.assertBackendAvailable();
        return this.cacheManager.getJedisPool();
    }

    private String generateToken() {
        final byte[] randomBytes = new byte[32];
        this.secureRandom.nextBytes(randomBytes);
        return HexFormat.of().formatHex(randomBytes);
    }

    private String sessionRedisKey(final String token) {
        return this.cacheManager.qualifyKey(this.sessionKeyPrefix + "." + token);
    }

    private static final class SessionTokenPayload {
        @SerializedName("player_id")
        private final int playerId;

        @SerializedName("expires_at")
        private final long expiresAt;

        private SessionTokenPayload(final int playerId, final long expiresAt) {
            this.playerId = playerId;
            this.expiresAt = expiresAt;
        }

        private static Optional<SessionTokenPayload> fromJson(final String json) {
            if (json == null) {
                return Optional.empty();
            }

            try {
                return Optional.ofNullable(JsonUtil.getInstance().fromJson(json, SessionTokenPayload.class));
            } catch (Exception exception) {
                return Optional.empty();
            }
        }

        private String toJson() {
            return JsonUtil.getInstance().toJson(this);
        }
    }
}