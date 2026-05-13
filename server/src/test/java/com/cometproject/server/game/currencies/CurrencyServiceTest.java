package com.cometproject.server.game.currencies;

import com.cometproject.api.events.EventHandler;
import com.cometproject.api.events.EventListenerContainer;
import com.cometproject.api.events.EventSubscribe;
import com.cometproject.api.events.currency.CurrencyAdjustmentRequestedEvent;
import com.cometproject.api.events.currency.CurrencyBalanceChangedEvent;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.modules.events.EventHandlerService;
import com.cometproject.storage.api.data.currency.CurrencyAdjustment;
import com.cometproject.storage.api.data.currency.CurrencyAdjustmentRequest;
import com.cometproject.storage.api.data.currency.CurrencyAlias;
import com.cometproject.storage.api.data.currency.CurrencyDefinition;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencyOperation;
import com.cometproject.storage.api.data.currency.CurrencyOperationResult;
import com.cometproject.storage.api.data.currency.CurrencyRoleRuleMutation;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.data.currency.ICurrencyMovement;
import com.cometproject.storage.api.data.currency.ICurrencyRoleRule;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyOperationCancelledException;
import com.cometproject.storage.api.data.currency.exceptions.InsufficientCurrencyBalanceException;
import com.cometproject.storage.api.repositories.ICurrencyRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CurrencyServiceTest {
    @Test
    void requestEventCancellationPreventsPersistence() {
        final InMemoryCurrencyRepository repository = new InMemoryCurrencyRepository();
        final EventHandlerService eventHandler = new EventHandlerService();
        eventHandler.registerListeners(new EventListenerContainer() {
            @EventSubscribe
            public void onCurrencyAdjustmentRequested(final CurrencyAdjustmentRequestedEvent event) {
                event.cancel("currency_adjustment_cancelled", "Blocked by plugin.");
            }
        });
        final CurrencyService service = service(repository, eventHandler);

        final CurrencyAdjustmentRequest request = request(CurrencyOperation.ADD, 10);

        final CurrencyOperationCancelledException exception = assertThrows(
                CurrencyOperationCancelledException.class,
                () -> service.add(request));

        assertEquals("currency_adjustment_cancelled", exception.getCancellationCode());
        assertEquals(25, repository.balance("currency_0"));
        assertEquals(0, repository.movements.size());
    }

    @Test
    void requestEventCanModifyAmountBeforePersistence() {
        final InMemoryCurrencyRepository repository = new InMemoryCurrencyRepository();
        final EventHandlerService eventHandler = new EventHandlerService();
        eventHandler.registerListeners(new EventListenerContainer() {
            @EventSubscribe
            public void onCurrencyAdjustmentRequested(final CurrencyAdjustmentRequestedEvent event) {
                event.setAmount(7);
            }
        });
        final CurrencyService service = service(repository, eventHandler);

        final CurrencyOperationResult result = service.add(request(CurrencyOperation.ADD, 10));

        assertEquals(7, result.getMovement().getDelta());
        assertEquals(32, result.getMovement().getNewBalance());
        assertEquals(32, repository.balance("currency_0"));
    }

    @Test
    void postCommitEventRunsAfterPersistence() {
        final InMemoryCurrencyRepository repository = new InMemoryCurrencyRepository();
        final EventHandlerService eventHandler = new EventHandlerService();
        final AtomicInteger changedEvents = new AtomicInteger();
        eventHandler.registerListeners(new EventListenerContainer() {
            @EventSubscribe
            public void onCurrencyBalanceChanged(final CurrencyBalanceChangedEvent event) {
                assertEquals(30, event.getMovement().getNewBalance());
                changedEvents.incrementAndGet();
            }
        });
        final CurrencyService service = service(repository, eventHandler);

        service.add(request(CurrencyOperation.ADD, 5));

        assertEquals(1, changedEvents.get());
        assertEquals(30, repository.balance("currency_0"));
    }

    @Test
    void removeStillRejectsNegativeBalanceAfterEvents() {
        final InMemoryCurrencyRepository repository = new InMemoryCurrencyRepository();
        final CurrencyService service = service(repository, new EventHandlerService());

        assertThrows(InsufficientCurrencyBalanceException.class,
                () -> service.remove(request(CurrencyOperation.REMOVE, 26)));

        assertEquals(25, repository.balance("currency_0"));
        assertTrue(repository.movements.isEmpty());
    }

    private static CurrencyService service(
            final InMemoryCurrencyRepository repository,
            final EventHandler eventHandler) {
        final PlayerManager playerManager = mock(PlayerManager.class);
        when(playerManager.isOnline(1)).thenReturn(false);
        return new CurrencyService(repository, eventHandler, playerManager, null);
    }

    private static CurrencyAdjustmentRequest request(final CurrencyOperation operation, final long amount) {
        return new CurrencyAdjustmentRequest(
                1,
                "currency_0",
                operation,
                amount,
                CurrencySource.plugin("test_plugin", "test", "Unit test adjustment"),
                false,
                Map.of());
    }

    private static final class InMemoryCurrencyRepository implements ICurrencyRepository {
        private final Map<String, ICurrencyDefinition> definitions = new LinkedHashMap<>();
        private final Map<String, Long> balances = new LinkedHashMap<>();
        private final List<CurrencyMovementResult> movements = new ArrayList<>();

        private InMemoryCurrencyRepository() {
            this.definitions.put("currency_0", new CurrencyDefinition(
                    2,
                    "currency_0",
                    "Currency 0",
                    0,
                    false,
                    true,
                    true,
                    10,
                    "currency_0"));
            this.balances.put("currency_0", 25L);
        }

        @Override
        public void getDefinitions(final Consumer<List<ICurrencyDefinition>> consumer) {
            consumer.accept(List.copyOf(this.definitions.values()));
        }

        @Override
        public void getDefinition(final String currencyCode, final Consumer<ICurrencyDefinition> consumer) {
            consumer.accept(this.definitions.get(currencyCode));
        }

        @Override
        public void getBalances(final int playerId, final Consumer<Map<String, Long>> consumer) {
            consumer.accept(Map.copyOf(this.balances));
        }

        @Override
        public void getPlayerRank(final int playerId, final Consumer<Integer> consumer) {
            consumer.accept(1);
        }

        @Override
        public void adjust(final CurrencyAdjustment adjustment, final Consumer<CurrencyMovementResult> consumer) {
            final long oldBalance = this.balance(adjustment.getCurrencyCode());
            final long newBalance = switch (adjustment.getOperation()) {
                case ADD -> oldBalance + adjustment.getAmount();
                case REMOVE -> {
                    if (oldBalance < adjustment.getAmount()) {
                        throw new InsufficientCurrencyBalanceException(
                                adjustment.getCurrencyCode(),
                                oldBalance,
                                adjustment.getAmount());
                    }
                    yield oldBalance - adjustment.getAmount();
                }
                case SET -> adjustment.getAmount();
            };
            final CurrencyMovementResult result = new CurrencyMovementResult(
                    this.movements.size() + 1L,
                    adjustment.getPlayerId(),
                    adjustment.getCurrencyId(),
                    adjustment.getCurrencyCode(),
                    adjustment.getOperation(),
                    newBalance - oldBalance,
                    oldBalance,
                    newBalance,
                    adjustment.getSource().getActorType(),
                    adjustment.getSource().getActorId(),
                    adjustment.getSource().getSourceType(),
                    adjustment.getSource().getSourceRef(),
                    adjustment.getSource().getReason(),
                    Instant.now());
            this.balances.put(adjustment.getCurrencyCode(), newBalance);
            this.movements.add(result);
            consumer.accept(result);
        }

        @Override
        public void upsertDefinition(
                final com.cometproject.storage.api.data.currency.CurrencyDefinitionMutation mutation,
                final Consumer<ICurrencyDefinition> consumer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void disableDefinition(final String currencyCode) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getRoleRules(final String currencyCode, final Consumer<List<ICurrencyRoleRule>> consumer) {
            consumer.accept(List.of());
        }

        @Override
        public void upsertRoleRule(
                final CurrencyRoleRuleMutation mutation,
                final Consumer<ICurrencyRoleRule> consumer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteRoleRule(final String currencyCode, final int rankId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getAliases(final String currencyCode, final Consumer<List<CurrencyAlias>> consumer) {
            consumer.accept(List.of());
        }

        @Override
        public void resolveAlias(final String alias, final Consumer<String> consumer) {
            consumer.accept(null);
        }

        @Override
        public void resolveUseCase(final String useCase, final Consumer<String> consumer) {
            consumer.accept(null);
        }

        @Override
        public void upsertAlias(final String alias, final String currencyCode) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void deleteAlias(final String alias) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void getMovements(final int playerId, final int limit, final Consumer<List<ICurrencyMovement>> consumer) {
            consumer.accept(List.copyOf(this.movements));
        }

        private long balance(final String currencyCode) {
            return this.balances.getOrDefault(currencyCode, 0L);
        }
    }
}
