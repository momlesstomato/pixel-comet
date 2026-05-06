package com.cometproject.server.game.sso.storage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import com.cometproject.api.game.sso.SsoTicket;

import redis.clients.jedis.JedisPool;

class RedisSsoTicketRepositoryTest {
    private static final DockerImageName REDIS_IMAGE = DockerImageName.parse("redis:7.4-alpine");
    private static GenericContainer<?> redis;

    @BeforeAll
    static void startRedis() {
        Assumptions.assumeTrue(
                DockerClientFactory.instance().isDockerAvailable(),
                "Docker is required for the Redis SSO repository integration test.");

        redis = new GenericContainer<>(REDIS_IMAGE).withExposedPorts(6379);
        redis.start();
    }

    @AfterAll
    static void stopRedis() {
        if (redis != null) {
            redis.stop();
        }
    }

    @Test
    void save_andConsume_roundTripsTicket() {
        try (JedisPool jedisPool = new JedisPool(redis.getHost(), redis.getMappedPort(6379))) {
            final RedisSsoTicketRepository repository = new RedisSsoTicketRepository(jedisPool, "comet", "sso");
            final SsoTicket ticket = new SsoTicket("0123456789abcdef", 42, Instant.now().plusSeconds(60));

            repository.save(ticket);

            assertTrue(repository.exists(ticket.ticket()));
            assertTrue(repository.consume(ticket.ticket()).isPresent());
            assertFalse(repository.exists(ticket.ticket()));
            assertFalse(repository.consume(ticket.ticket()).isPresent());
        }
    }

    @Test
    void consume_isAtomic_acrossConcurrentRequests() throws InterruptedException, ExecutionException {
        try (JedisPool jedisPool = new JedisPool(redis.getHost(), redis.getMappedPort(6379))) {
            final RedisSsoTicketRepository repository = new RedisSsoTicketRepository(jedisPool, "comet", "sso");
            final SsoTicket ticket = new SsoTicket("abcdef0123456789", 42, Instant.now().plusSeconds(60));

            repository.save(ticket);

            final ExecutorService executorService = Executors.newFixedThreadPool(2);
            try {
                final List<Callable<Boolean>> tasks = List.of(
                        () -> repository.consume(ticket.ticket()).isPresent(),
                        () -> repository.consume(ticket.ticket()).isPresent());
                final List<Future<Boolean>> results = new ArrayList<>(executorService.invokeAll(tasks));

                int successCount = 0;
                for (Future<Boolean> result : results) {
                    if (result.get()) {
                        successCount++;
                    }
                }

                assertEquals(1, successCount);
            } finally {
                executorService.shutdownNow();
            }
        }
    }
}