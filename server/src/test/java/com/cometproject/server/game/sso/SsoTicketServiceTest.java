package com.cometproject.server.game.sso;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.config.ConfigurationSource;
import com.cometproject.api.game.sso.ISsoTicketRepository;
import com.cometproject.api.game.sso.SsoTicket;
import com.cometproject.server.game.sso.exceptions.SsoBackendUnavailableException;
import com.cometproject.server.storage.cache.CacheManager;

import redis.clients.jedis.JedisPool;

class SsoTicketServiceTest {
    private ConfigurationSource previousConfiguration;

    @BeforeEach
    void setUp() {
        try {
            this.previousConfiguration = Configuration.currentConfig();
        } catch (IllegalStateException exception) {
            this.previousConfiguration = null;
        }

        Configuration.setConfiguration(new MapConfigurationSource(Map.of()));
    }

    @AfterEach
    void tearDown() {
        Configuration.setConfiguration(this.previousConfiguration);
    }

    @Test
    void issue_persistsGeneratedTicket() {
        final ISsoTicketRepository repository = Mockito.mock(ISsoTicketRepository.class);
        final CacheManager cacheManager = Mockito.mock(CacheManager.class);
        final Clock clock = Clock.fixed(Instant.now().plusSeconds(600), ZoneOffset.UTC);
        final SecureRandom secureRandom = new SecureRandom(new byte[]{1, 2, 3, 4});

        when(cacheManager.getJedisPool()).thenReturn(Mockito.mock(JedisPool.class));

        final SsoTicketService service = new SsoTicketService(repository, cacheManager, clock, secureRandom, 3600, "session");
        final SsoTicket ticket = service.issue(42, 60);

        final ArgumentCaptor<SsoTicket> ticketCaptor = ArgumentCaptor.forClass(SsoTicket.class);
        verify(repository).save(ticketCaptor.capture());

        assertEquals(42, ticket.playerId());
        assertEquals(42, ticketCaptor.getValue().playerId());
        assertEquals(clock.instant().plusSeconds(60), ticket.expiresAt());
        assertEquals(clock.instant().plusSeconds(60), ticketCaptor.getValue().expiresAt());
        assertEquals(64, ticket.ticket().length());
    }

    @Test
    void issue_throwsWhenBackendIsDisabled() {
        final ISsoTicketRepository repository = Mockito.mock(ISsoTicketRepository.class);
        final CacheManager cacheManager = Mockito.mock(CacheManager.class);

        when(cacheManager.getJedisPool()).thenReturn(null);

        final SsoTicketService service = new SsoTicketService(
                repository,
                cacheManager,
                Clock.systemUTC(),
                new SecureRandom(),
                3600,
                "session");

        assertThrows(SsoBackendUnavailableException.class, () -> service.issue(42, 60));
        verify(repository, never()).save(any());
    }

    @Test
    void consume_returnsTicketWhenPlayerMatches() {
        final ISsoTicketRepository repository = Mockito.mock(ISsoTicketRepository.class);
        final CacheManager cacheManager = Mockito.mock(CacheManager.class);
        final SsoTicket storedTicket = new SsoTicket("0123456789abcdef", 42, Instant.now().plusSeconds(60));

        when(repository.consume(eq("0123456789abcdef"))).thenReturn(Optional.of(storedTicket));

        final SsoTicketService service = new SsoTicketService(
                repository,
                cacheManager,
                Clock.systemUTC(),
                new SecureRandom(),
                3600,
                "session");

        final Optional<SsoTicket> consumed = service.consume("0123456789abcdef", 42);

        assertTrue(consumed.isPresent());
        assertEquals(42, consumed.get().playerId());
    }

    @Test
    void consume_returnsEmptyWhenPlayerDoesNotMatch() {
        final ISsoTicketRepository repository = Mockito.mock(ISsoTicketRepository.class);
        final CacheManager cacheManager = Mockito.mock(CacheManager.class);
        final SsoTicket storedTicket = new SsoTicket("fedcba9876543210", 99, Instant.now().plusSeconds(60));

        when(repository.consume(eq("fedcba9876543210"))).thenReturn(Optional.of(storedTicket));

        final SsoTicketService service = new SsoTicketService(
                repository,
                cacheManager,
                Clock.systemUTC(),
                new SecureRandom(),
                3600,
                "session");

        assertFalse(service.consume("fedcba9876543210", 42).isPresent());
    }

    @Test
    void createSessionToken_persistsPlayerBindingInRedis() {
        final ISsoTicketRepository repository = Mockito.mock(ISsoTicketRepository.class);
        final CacheManager cacheManager = Mockito.mock(CacheManager.class);
        final redis.clients.jedis.JedisPool jedisPool = Mockito.mock(redis.clients.jedis.JedisPool.class);
        final redis.clients.jedis.Jedis jedis = Mockito.mock(redis.clients.jedis.Jedis.class);
        final Clock clock = Clock.fixed(Instant.now().plusSeconds(600), ZoneOffset.UTC);

        when(cacheManager.getJedisPool()).thenReturn(jedisPool);
        when(cacheManager.qualifyKey(any())).thenAnswer(invocation -> "comet." + invocation.getArgument(0, String.class));
        when(jedisPool.getResource()).thenReturn(jedis);

        final SsoTicketService service = new SsoTicketService(
                repository,
                cacheManager,
                clock,
                new SecureRandom(new byte[]{9, 8, 7, 6}),
                120,
                "session");

        final SsoTicket sessionToken = service.createSessionToken(42);

        assertNotNull(sessionToken);
        assertEquals(42, sessionToken.playerId());
        verify(jedis).setex(eq("comet.session." + sessionToken.ticket()), eq(120L), any());
    }

    private record MapConfigurationSource(Map<String, String> values) implements ConfigurationSource {
        @Override
        public String get(final String key) {
            return this.values.get(key);
        }
    }
}