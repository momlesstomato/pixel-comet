package com.cometproject.server.game.currencies;

import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.storage.api.data.currency.CurrencyAdjustment;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencyOperation;
import com.cometproject.storage.api.data.currency.CurrencyRolePolicy;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.data.currency.ICurrencyRoleRule;
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

    /**
     * Creates the currency service.
     *
     * @param currencyRepository the currency repository.
     */
    @Inject
    public CurrencyService(final ICurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
        this.messageDispatcher = new CurrencyMessageDispatcher(this);
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
        return this.adjust(playerId, this.resolveCurrencyCode(currencyCode), CurrencyOperation.ADD, amount, source);
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
        return this.adjust(playerId, this.resolveCurrencyCode(currencyCode), CurrencyOperation.REMOVE, amount, source);
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
        return this.adjust(playerId, this.resolveCurrencyCode(currencyCode), CurrencyOperation.SET, amount, source);
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

    private CurrencyMovementResult adjust(
            final int playerId,
            final String currencyCode,
            final CurrencyOperation operation,
            final long amount,
            final CurrencySource source) {
        final AtomicReference<CurrencyMovementResult> result = new AtomicReference<>();
        final int rankId = this.playerRank(playerId);
        final ICurrencyDefinition definition = this.definitionByCode(currencyCode);

        if (!this.sourceBypassesRoleRules(source) && !this.allowedForOperation(definition, rankId, operation)) {
            throw new IllegalStateException("Rank " + rankId + " cannot " + operation.name().toLowerCase() + " " + currencyCode);
        }

        this.currencyRepository.adjust(new CurrencyAdjustment(playerId, definition.getId(), currencyCode, operation, amount, source), result::set);
        this.applyRuntimeSnapshot(result.get());
        return result.get();
    }

    private void applyRuntimeSnapshot(final CurrencyMovementResult result) {
        if (result == null || !PlayerManager.getInstance().isOnline(result.getPlayerId())) {
            return;
        }

        final Session session = NetworkManager.getInstance().getSessions().getByPlayerId(result.getPlayerId());
        if (session == null || session.getPlayer() == null) {
            return;
        }

        this.messageDispatcher.applySnapshot(session.getPlayer().getData(), result);
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
