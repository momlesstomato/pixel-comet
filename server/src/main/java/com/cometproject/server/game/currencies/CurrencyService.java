package com.cometproject.server.game.currencies;

import com.cometproject.api.events.Event;
import com.cometproject.api.events.EventHandler;
import com.cometproject.api.events.currency.CurrencyAdjustmentRequestedEvent;
import com.cometproject.api.events.currency.CurrencyBalanceChangedEvent;
import com.cometproject.api.events.currency.CurrencyBalanceChangingEvent;
import com.cometproject.api.events.currency.CurrencyBalanceSyncRequestedEvent;
import com.cometproject.api.events.currency.CurrencyBalanceSyncedEvent;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.currency.CurrencyAdjustment;
import com.cometproject.storage.api.data.currency.CurrencyAdjustmentRequest;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencyOperation;
import com.cometproject.storage.api.data.currency.CurrencyOperationResult;
import com.cometproject.storage.api.data.currency.CurrencyRolePolicy;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.data.currency.ICurrencyRoleRule;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyOperationCancelledException;
import com.cometproject.storage.api.repositories.ICurrencyRepository;
import com.cometproject.storage.api.services.ICurrencyService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Default currency service coordinating player inventory persistence and runtime snapshots.
 */
@Singleton
public final class CurrencyService implements ICurrencyService {
    private final ICurrencyRepository currencyRepository;
    private final CurrencyMessageDispatcher messageDispatcher;
    private final EventHandler eventHandler;
    private final PlayerManager playerManager;
    private final NetworkManager networkManager;

    /**
     * Creates the currency service.
     *
     * @param currencyRepository the currency repository.
     * @param eventHandler       the module event handler.
     * @param playerManager      the player manager.
     * @param networkManager     the network manager.
     */
    @Inject
    public CurrencyService(
            final ICurrencyRepository currencyRepository,
            final EventHandler eventHandler,
            final PlayerManager playerManager,
            final NetworkManager networkManager) {
        this.currencyRepository = currencyRepository;
        this.eventHandler = eventHandler;
        this.playerManager = playerManager;
        this.networkManager = networkManager;
        this.messageDispatcher = new CurrencyMessageDispatcher(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrencyOperationResult add(final CurrencyAdjustmentRequest request) {
        return this.adjust(request.withOperation(CurrencyOperation.ADD));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrencyMovementResult add(
            final int playerId,
            final String currencyCode,
            final long amount,
            final CurrencySource source) {
        return this.add(this.compatibilityRequest(playerId, currencyCode, CurrencyOperation.ADD, amount, source))
                .getMovement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrencyOperationResult remove(final CurrencyAdjustmentRequest request) {
        return this.adjust(request.withOperation(CurrencyOperation.REMOVE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrencyMovementResult remove(
            final int playerId,
            final String currencyCode,
            final long amount,
            final CurrencySource source) {
        return this.remove(this.compatibilityRequest(playerId, currencyCode, CurrencyOperation.REMOVE, amount, source))
                .getMovement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrencyOperationResult set(final CurrencyAdjustmentRequest request) {
        return this.adjust(request.withOperation(CurrencyOperation.SET));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CurrencyMovementResult set(
            final int playerId,
            final String currencyCode,
            final long amount,
            final CurrencySource source) {
        return this.set(this.compatibilityRequest(playerId, currencyCode, CurrencyOperation.SET, amount, source))
                .getMovement();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, Long> balances(final int playerId) {
        final AtomicReference<Map<String, Long>> balances = new AtomicReference<>(Map.of());
        this.currencyRepository.getBalances(playerId, balances::set);
        return balances.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long balance(final int playerId, final String currencyCode) {
        return this.balances(playerId).getOrDefault(this.resolveCurrencyCode(currencyCode), 0L);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ICurrencyDefinition> definitions() {
        final AtomicReference<List<ICurrencyDefinition>> definitions = new AtomicReference<>(List.of());
        this.currencyRepository.getDefinitions(definitions::set);
        return definitions.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ICurrencyDefinition> definitionsForRank(final int rankId) {
        return this.definitions().stream()
                .filter(definition -> this.allowed(definition, this.roleRule(definition.getCode(), rankId), Capability.VIEW))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Integer, Integer> protocolVisibleBalances(final int playerId) {
        final Map<String, Long> balances = this.balances(playerId);

        final int rankId = this.playerRank(playerId);

        return this.definitionsForRank(rankId).stream()
                .filter(ICurrencyDefinition::isEnabled)
                .filter(ICurrencyDefinition::isVisibleInPurse)
                .filter(definition -> definition.getProtocolCurrencyId().isPresent())
                .filter(definition -> balances.containsKey(definition.getCode()))
                .collect(Collectors.toMap(
                        definition -> definition.getProtocolCurrencyId().getAsInt(),
                        definition -> Math.toIntExact(balances.get(definition.getCode()))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canView(final int rankId, final String currencyCode) {
        return this.can(rankId, currencyCode, Capability.VIEW);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canEarn(final int rankId, final String currencyCode) {
        return this.can(rankId, currencyCode, Capability.EARN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canSpend(final int rankId, final String currencyCode) {
        return this.can(rankId, currencyCode, Capability.SPEND);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canManage(final int rankId, final String currencyCode) {
        return this.can(rankId, currencyCode, Capability.MANAGE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String resolveCurrencyCode(final String currencyCodeOrAlias) {
        final AtomicReference<String> resolved = new AtomicReference<>();
        this.currencyRepository.resolveAlias(currencyCodeOrAlias, resolved::set);
        return resolved.get() == null ? currencyCodeOrAlias : resolved.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String currencyCodeForProtocolId(final int protocolCurrencyId) {
        return this.definitions().stream()
                .filter(ICurrencyDefinition::isEnabled)
                .filter(definition -> definition.getProtocolCurrencyId().isPresent())
                .filter(definition -> definition.getProtocolCurrencyId().getAsInt() == protocolCurrencyId)
                .map(ICurrencyDefinition::getCode)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Unknown protocol currency id: " + protocolCurrencyId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String currencyCodeForUseCase(final String useCase) {
        final AtomicReference<String> currencyCode = new AtomicReference<>();
        this.currencyRepository.resolveUseCase(useCase, currencyCode::set);

        if (currencyCode.get() == null) {
            throw new IllegalStateException("Unknown currency use case: " + useCase);
        }

        return currencyCode.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String firstNonCreditCurrencyCode() {
        return this.definitions().stream()
                .filter(ICurrencyDefinition::isEnabled)
                .filter(definition -> !definition.isCredits())
                .map(ICurrencyDefinition::getCode)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No non-credit currency is configured"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ICurrencyDefinition definition(final String currencyCodeOrAlias) {
        return this.definitionByCode(this.resolveCurrencyCode(currencyCodeOrAlias));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean readFromInventory() {
        return true;
    }

    /**
     * Debits several currencies atomically from the caller perspective.
     *
     * <p>The current repository contract records one movement per currency. If a later debit fails,
     * this method compensates previous debits so callers do not keep partial catalog charges.
     *
     * @param playerId the target player id.
     * @param debits   the requested debits.
     * @param source   the audit source metadata.
     * @return the recorded debit movements.
     */
    public List<CurrencyMovementResult> tryDebitAll(
            final int playerId,
            final Map<String, Long> debits,
            final CurrencySource source) {
        final List<CurrencyMovementResult> results = new ArrayList<>();

        try {
            for (Map.Entry<String, Long> debit : debits.entrySet()) {
                if (debit.getValue() > 0) {
                    results.add(this.remove(playerId, this.resolveCurrencyCode(debit.getKey()), debit.getValue(), source));
                }
            }
        } catch (RuntimeException exception) {
            for (CurrencyMovementResult result : results) {
                this.add(playerId, result.getCurrencyCode(), -result.getDelta(), CurrencySource.system(
                        "Compensating failed multi-currency debit: " + source.getReason()));
            }

            throw exception;
        }

        return results;
    }

    /**
     * Returns the dispatcher used for runtime purse updates.
     *
     * @return the currency message dispatcher.
     */
    public CurrencyMessageDispatcher getMessageDispatcher() {
        return this.messageDispatcher;
    }

    private CurrencyOperationResult adjust(final CurrencyAdjustmentRequest request) {
        final CurrencyAdjustmentRequest requested = this.publishAdjustmentRequested(request);
        final String currencyCode = this.resolveCurrencyCode(requested.getCurrencyCodeOrAlias());
        CurrencyAdjustmentRequest resolvedRequest = requested.withCurrencyCodeOrAlias(currencyCode);
        final AtomicReference<CurrencyMovementResult> result = new AtomicReference<>();
        final int rankId = this.playerRank(resolvedRequest.getPlayerId());
        final ICurrencyDefinition definition = this.definitionByCode(currencyCode);

        if (!this.sourceBypassesRoleRules(resolvedRequest.getSource())
                && !this.allowedForOperation(definition, rankId, resolvedRequest.getOperation())) {
            throw new IllegalStateException("Rank " + rankId + " cannot "
                    + resolvedRequest.getOperation().name().toLowerCase() + " " + currencyCode);
        }

        resolvedRequest = this.publishBalanceChanging(resolvedRequest);

        this.currencyRepository.adjust(new CurrencyAdjustment(
                resolvedRequest.getPlayerId(),
                definition.getId(),
                currencyCode,
                resolvedRequest.getOperation(),
                resolvedRequest.getAmount(),
                resolvedRequest.getSource()), result::set);

        final boolean onlinePlayerUpdated = this.applyRuntimeSnapshot(result.get());
        this.publish(new CurrencyBalanceChangedEvent(resolvedRequest, result.get()));
        final boolean playerNotified = this.syncRuntimeBalance(resolvedRequest, result.get(), onlinePlayerUpdated);

        return new CurrencyOperationResult(result.get(), onlinePlayerUpdated, playerNotified);
    }

    private CurrencyAdjustmentRequest publishAdjustmentRequested(final CurrencyAdjustmentRequest request) {
        final CurrencyAdjustmentRequestedEvent event = new CurrencyAdjustmentRequestedEvent(request);
        if (this.publish(event)) {
            throw new CurrencyOperationCancelledException(event.getCancellationCode(), event.getCancellationMessage());
        }

        return event.toRequest();
    }

    private CurrencyAdjustmentRequest publishBalanceChanging(final CurrencyAdjustmentRequest request) {
        final long oldBalance = this.balance(request.getPlayerId(), request.getCurrencyCodeOrAlias());
        final long proposedNewBalance = this.proposedNewBalance(request, oldBalance);
        final CurrencyBalanceChangingEvent event = new CurrencyBalanceChangingEvent(
                request,
                oldBalance,
                proposedNewBalance);

        if (this.publish(event)) {
            throw new CurrencyOperationCancelledException(event.getCancellationCode(), event.getCancellationMessage());
        }

        if (event.getNewBalance() != proposedNewBalance) {
            return request.withOperation(CurrencyOperation.SET).withAmount(event.getNewBalance());
        }

        return request;
    }

    private long proposedNewBalance(final CurrencyAdjustmentRequest request, final long oldBalance) {
        return switch (request.getOperation()) {
            case ADD -> Math.addExact(oldBalance, request.getAmount());
            case REMOVE -> oldBalance - request.getAmount();
            case SET -> request.getAmount();
        };
    }

    private boolean applyRuntimeSnapshot(final CurrencyMovementResult result) {
        if (result == null || !this.playerManager.isOnline(result.getPlayerId())) {
            return false;
        }

        final Session session = this.networkManager.getSessions().getByPlayerId(result.getPlayerId());
        if (session == null || session.getPlayer() == null) {
            return false;
        }

        this.messageDispatcher.applySnapshot(session.getPlayer().getData(), result);
        return true;
    }

    private boolean syncRuntimeBalance(
            final CurrencyAdjustmentRequest request,
            final CurrencyMovementResult result,
        final boolean playerOnline) {
        if (!request.shouldNotifyPlayer()) {
            this.publish(new CurrencyBalanceSyncRequestedEvent(result, playerOnline, false));
            this.publish(new CurrencyBalanceSyncedEvent(result, playerOnline, false));
            return false;
        }

        this.publish(new CurrencyBalanceSyncRequestedEvent(result, playerOnline, false));

        if (!playerOnline || this.networkManager == null) {
            this.publish(new CurrencyBalanceSyncedEvent(result, playerOnline, false));
            return false;
        }

        final Session session = this.networkManager.getSessions().getByPlayerId(result.getPlayerId());
        final boolean playerNotified = session != null && this.messageDispatcher.sendBalanceChange(session, result);

        this.publish(new CurrencyBalanceSyncedEvent(result, playerOnline, playerNotified));
        return playerNotified;
    }

    private CurrencyAdjustmentRequest compatibilityRequest(
            final int playerId,
            final String currencyCode,
            final CurrencyOperation operation,
            final long amount,
            final CurrencySource source) {
        return new CurrencyAdjustmentRequest(playerId, currencyCode, operation, amount, source, false, Map.of());
    }

    private boolean publish(final Event event) {
        if (this.eventHandler == null) {
            return false;
        }

        return this.eventHandler.handleEvent(event);
    }

    private int playerRank(final int playerId) {
        final AtomicReference<Integer> rankId = new AtomicReference<>(0);
        this.currencyRepository.getPlayerRank(playerId, rankId::set);
        return rankId.get() == null ? 0 : rankId.get();
    }

    private boolean can(final int rankId, final String currencyCodeOrAlias, final Capability capability) {
        final String currencyCode = this.resolveCurrencyCode(currencyCodeOrAlias);
        return this.allowed(this.definitionByCode(currencyCode), this.roleRule(currencyCode, rankId), capability);
    }

    private ICurrencyDefinition definitionByCode(final String currencyCode) {
        final AtomicReference<ICurrencyDefinition> definition = new AtomicReference<>();
        this.currencyRepository.getDefinition(currencyCode, definition::set);

        if (definition.get() == null) {
            throw new IllegalStateException("Unknown currency: " + currencyCode);
        }

        return definition.get();
    }

    private ICurrencyRoleRule roleRule(final String currencyCode, final int rankId) {
        final AtomicReference<List<ICurrencyRoleRule>> rules = new AtomicReference<>(List.of());
        this.currencyRepository.getRoleRules(currencyCode, rules::set);

        return rules.get().stream()
                .filter(rule -> rule.getRankId() == rankId)
                .findFirst()
                .orElse(null);
    }

    private boolean allowedForOperation(
            final ICurrencyDefinition definition,
            final int rankId,
            final CurrencyOperation operation) {
        return switch (operation) {
            case ADD -> this.allowed(definition, this.roleRule(definition.getCode(), rankId), Capability.EARN);
            case REMOVE -> this.allowed(definition, this.roleRule(definition.getCode(), rankId), Capability.SPEND);
            case SET -> this.allowed(definition, this.roleRule(definition.getCode(), rankId), Capability.MANAGE);
        };
    }

    private boolean sourceBypassesRoleRules(final CurrencySource source) {
        return source != null
                && ("api".equals(source.getActorType())
                || ("system".equals(source.getActorType()) && "system".equals(source.getSourceType())));
    }

    private boolean allowed(
            final ICurrencyDefinition definition,
            final ICurrencyRoleRule roleRule,
            final Capability capability) {
        if (definition.isCredits()) {
            return true;
        }

        if (definition.getRolePolicy() == CurrencyRolePolicy.ALL) {
            return true;
        }

        final boolean ruleAllows = roleRule != null && switch (capability) {
            case VIEW -> roleRule.canView();
            case EARN -> roleRule.canEarn();
            case SPEND -> roleRule.canSpend();
            case MANAGE -> roleRule.canManage();
        };

        if (definition.getRolePolicy() == CurrencyRolePolicy.ALLOW_LIST) {
            return ruleAllows;
        }

        return roleRule == null || ruleAllows;
    }

    private enum Capability {
        VIEW,
        EARN,
        SPEND,
        MANAGE
    }
}
