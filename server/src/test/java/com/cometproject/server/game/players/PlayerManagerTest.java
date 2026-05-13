package com.cometproject.server.game.players;

import com.cometproject.api.game.sso.ISsoTicketService;
import com.cometproject.api.game.sso.SsoTicket;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.repositories.IPlayerRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.concurrent.AbstractExecutorService;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Verifies that player manager login orchestration uses the injected player repository.
 */
final class PlayerManagerTest {
    @Test
    void submitLoginRequestLoadsPlayerThroughInjectedRepository() throws Exception {
        final ISsoTicketService ssoTicketService = mock(ISsoTicketService.class);
        final IPlayerRepository playerRepository = mock(IPlayerRepository.class);
        final Session session = mock(Session.class);
        final PlayerManager playerManager = new PlayerManager(ssoTicketService, playerRepository);

        this.setLoginExecutor(playerManager, new DirectExecutorService());

        when(session.getLogger()).thenReturn(LoggerFactory.getLogger("test-session"));
        when(ssoTicketService.consume("ticket-123")).thenReturn(Optional.of(
                new SsoTicket("ticket-123", 42, Instant.now().plusSeconds(60))));

        playerManager.submitLoginRequest(session, "ticket-123");

        verify(playerRepository).findById(eq(42), any());
    }

    private void setLoginExecutor(
            final PlayerManager playerManager,
            final DirectExecutorService executorService) throws ReflectiveOperationException {
        final Field field = PlayerManager.class.getDeclaredField("playerLoginService");

        field.setAccessible(true);
        field.set(playerManager, executorService);
    }

    private static final class DirectExecutorService extends AbstractExecutorService {
        private boolean shutdown;

        @Override
        public void shutdown() {
            this.shutdown = true;
        }

        @Override
        public List<Runnable> shutdownNow() {
            this.shutdown = true;
            return List.of();
        }

        @Override
        public boolean isShutdown() {
            return this.shutdown;
        }

        @Override
        public boolean isTerminated() {
            return this.shutdown;
        }

        @Override
        public boolean awaitTermination(final long timeout, final TimeUnit unit) {
            return this.shutdown;
        }

        @Override
        public void execute(final Runnable command) {
            command.run();
        }
    }
}
