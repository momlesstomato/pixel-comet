package com.cometproject.server.storage.jooq.repositories;

import com.cometproject.server.storage.jooq.IJooqDslProvider;
import com.cometproject.server.storage.jooq.JooqRepository;
import com.cometproject.storage.api.data.currency.CurrencyAdjustment;
import com.cometproject.storage.api.data.currency.CurrencyAlias;
import com.cometproject.storage.api.data.currency.CurrencyDefinition;
import com.cometproject.storage.api.data.currency.CurrencyDefinitionMutation;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencyOperation;
import com.cometproject.storage.api.data.currency.CurrencyRolePolicy;
import com.cometproject.storage.api.data.currency.CurrencyRoleRule;
import com.cometproject.storage.api.data.currency.CurrencyRoleRuleMutation;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.data.currency.ICurrencyMovement;
import com.cometproject.storage.api.data.currency.ICurrencyRoleRule;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyAdjustmentException;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyDisabledException;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyNotFoundException;
import com.cometproject.storage.api.data.currency.exceptions.InsufficientCurrencyBalanceException;
import com.cometproject.storage.api.repositories.ICurrencyRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

/**
 * JOOQ-backed currency repository with atomic balance and movement writes.
 */
@Singleton
public final class JooqCurrencyRepository extends JooqRepository implements ICurrencyRepository {
    private static final int MOVEMENT_QUERY_LIMIT_MIN = 1;
    private static final int MOVEMENT_QUERY_LIMIT_MAX = 500;
    private static final long CREDITS_COLUMN_MAX_VALUE = Integer.MAX_VALUE;

    private static final Table<Record> CURRENCIES = DSL.table(DSL.name("currencies"));
    private static final Field<Long> CURRENCY_ID = DSL.field(DSL.name("currencies", "id"), Long.class);
    private static final Field<String> CURRENCY_CODE = DSL.field(DSL.name("currencies", "code"), String.class);
    private static final Field<String> CURRENCY_DISPLAY_NAME = DSL.field(DSL.name("currencies", "display_name"), String.class);
    private static final Field<Integer> CURRENCY_PROTOCOL_ID =
            DSL.field(DSL.name("currencies", "protocol_currency_id"), Integer.class);
    private static final Field<String> CURRENCY_IS_CREDITS = DSL.field(DSL.name("currencies", "is_credits"), String.class);
    private static final Field<String> CURRENCY_VISIBLE_IN_PURSE =
            DSL.field(DSL.name("currencies", "visible_in_purse"), String.class);
    private static final Field<String> CURRENCY_ENABLED = DSL.field(DSL.name("currencies", "enabled"), String.class);
    private static final Field<Integer> CURRENCY_SORT_ORDER = DSL.field(DSL.name("currencies", "sort_order"), Integer.class);
    private static final Field<String> CURRENCY_ICON_KEY = DSL.field(DSL.name("currencies", "icon_key"), String.class);
    private static final Field<String> CURRENCY_NOUN_SINGULAR =
            DSL.field(DSL.name("currencies", "noun_singular"), String.class);
    private static final Field<String> CURRENCY_NOUN_PLURAL =
            DSL.field(DSL.name("currencies", "noun_plural"), String.class);
    private static final Field<String> CURRENCY_DESCRIPTION =
            DSL.field(DSL.name("currencies", "description"), String.class);
    private static final Field<String> CURRENCY_ROLE_POLICY =
            DSL.field(DSL.name("currencies", "role_policy"), String.class);

    private static final Table<Record> ROLE_RULES = DSL.table(DSL.name("currency_role_rules"));
    private static final Field<Long> ROLE_RULE_CURRENCY_ID =
            DSL.field(DSL.name("currency_role_rules", "currency_id"), Long.class);
    private static final Field<Integer> ROLE_RULE_RANK_ID =
            DSL.field(DSL.name("currency_role_rules", "rank_id"), Integer.class);
    private static final Field<String> ROLE_RULE_CAN_VIEW =
            DSL.field(DSL.name("currency_role_rules", "can_view"), String.class);
    private static final Field<String> ROLE_RULE_CAN_EARN =
            DSL.field(DSL.name("currency_role_rules", "can_earn"), String.class);
    private static final Field<String> ROLE_RULE_CAN_SPEND =
            DSL.field(DSL.name("currency_role_rules", "can_spend"), String.class);
    private static final Field<String> ROLE_RULE_CAN_MANAGE =
            DSL.field(DSL.name("currency_role_rules", "can_manage"), String.class);

    private static final Table<Record> ALIASES = DSL.table(DSL.name("currency_aliases"));
    private static final Field<String> ALIAS_NAME = DSL.field(DSL.name("currency_aliases", "alias"), String.class);
    private static final Field<Long> ALIAS_CURRENCY_ID =
            DSL.field(DSL.name("currency_aliases", "currency_id"), Long.class);

    private static final Table<Record> USE_CASES = DSL.table(DSL.name("currency_use_cases"));
    private static final Field<String> USE_CASE_NAME = DSL.field(DSL.name("currency_use_cases", "use_case"), String.class);
    private static final Field<Long> USE_CASE_CURRENCY_ID =
            DSL.field(DSL.name("currency_use_cases", "currency_id"), Long.class);
    private static final Field<String> USE_CASE_ENABLED = DSL.field(DSL.name("currency_use_cases", "enabled"), String.class);

    private static final Table<Record> BALANCES = DSL.table(DSL.name("player_currency_balances"));
    private static final Field<Integer> BALANCE_PLAYER_ID =
            DSL.field(DSL.name("player_currency_balances", "player_id"), Integer.class);
    private static final Field<Long> BALANCE_CURRENCY_ID =
            DSL.field(DSL.name("player_currency_balances", "currency_id"), Long.class);
    private static final Field<Long> BALANCE_AMOUNT = DSL.field(DSL.name("player_currency_balances", "balance"), Long.class);

    private static final Table<Record> PLAYERS = DSL.table(DSL.name("players"));
    private static final Field<Integer> PLAYER_ID = DSL.field(DSL.name("players", "id"), Integer.class);
    private static final Field<Integer> PLAYER_RANK = DSL.field(DSL.name("players", "rank"), Integer.class);

    private static final Table<Record> MOVEMENTS = DSL.table(DSL.name("player_currency_movements"));
    private static final Field<Long> MOVEMENT_ID = DSL.field(DSL.name("player_currency_movements", "id"), Long.class);
    private static final Field<Integer> MOVEMENT_PLAYER_ID =
            DSL.field(DSL.name("player_currency_movements", "player_id"), Integer.class);
    private static final Field<Long> MOVEMENT_CURRENCY_ID =
            DSL.field(DSL.name("player_currency_movements", "currency_id"), Long.class);
    private static final Field<String> MOVEMENT_OPERATION =
            DSL.field(DSL.name("player_currency_movements", "operation"), String.class);
    private static final Field<Long> MOVEMENT_DELTA = DSL.field(DSL.name("player_currency_movements", "delta"), Long.class);
    private static final Field<Long> MOVEMENT_OLD_BALANCE =
            DSL.field(DSL.name("player_currency_movements", "old_balance"), Long.class);
    private static final Field<Long> MOVEMENT_NEW_BALANCE =
            DSL.field(DSL.name("player_currency_movements", "new_balance"), Long.class);
    private static final Field<String> MOVEMENT_ACTOR_TYPE =
            DSL.field(DSL.name("player_currency_movements", "actor_type"), String.class);
    private static final Field<String> MOVEMENT_ACTOR_ID =
            DSL.field(DSL.name("player_currency_movements", "actor_id"), String.class);
    private static final Field<String> MOVEMENT_SOURCE_TYPE =
            DSL.field(DSL.name("player_currency_movements", "source_type"), String.class);
    private static final Field<String> MOVEMENT_SOURCE_REF =
            DSL.field(DSL.name("player_currency_movements", "source_ref"), String.class);
    private static final Field<String> MOVEMENT_REASON =
            DSL.field(DSL.name("player_currency_movements", "reason"), String.class);
    private static final Field<Timestamp> MOVEMENT_CREATED_AT =
            DSL.field(DSL.name("player_currency_movements", "created_at"), Timestamp.class);
    private static final Field<Long> LAST_INSERT_ID = DSL.field("last_insert_id()", Long.class);

    /**
     * Creates the currency repository.
     *
     * @param dslProvider the shared JOOQ DSL provider.
     */
    @Inject
    public JooqCurrencyRepository(final IJooqDslProvider dslProvider) {
        super(dslProvider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getDefinitions(final Consumer<List<ICurrencyDefinition>> consumer) {
        consumer.accept(this.dsl()
                .select(
                        CURRENCY_ID,
                        CURRENCY_CODE,
                        CURRENCY_DISPLAY_NAME,
                        CURRENCY_PROTOCOL_ID,
                        CURRENCY_IS_CREDITS,
                        CURRENCY_VISIBLE_IN_PURSE,
                        CURRENCY_ENABLED,
                        CURRENCY_SORT_ORDER,
                        CURRENCY_ICON_KEY,
                        CURRENCY_NOUN_SINGULAR,
                        CURRENCY_NOUN_PLURAL,
                        CURRENCY_DESCRIPTION,
                        CURRENCY_ROLE_POLICY)
                .from(CURRENCIES)
                .orderBy(CURRENCY_SORT_ORDER.asc(), CURRENCY_CODE.asc())
                .fetch(this::mapCurrencyDefinition));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getDefinition(final String currencyCode, final Consumer<ICurrencyDefinition> consumer) {
        consumer.accept(this.dsl()
                .select(
                        CURRENCY_ID,
                        CURRENCY_CODE,
                        CURRENCY_DISPLAY_NAME,
                        CURRENCY_PROTOCOL_ID,
                        CURRENCY_IS_CREDITS,
                        CURRENCY_VISIBLE_IN_PURSE,
                        CURRENCY_ENABLED,
                        CURRENCY_SORT_ORDER,
                        CURRENCY_ICON_KEY,
                        CURRENCY_NOUN_SINGULAR,
                        CURRENCY_NOUN_PLURAL,
                        CURRENCY_DESCRIPTION,
                        CURRENCY_ROLE_POLICY)
                .from(CURRENCIES)
                .where(CURRENCY_CODE.eq(currencyCode))
                .fetchOne(this::mapCurrencyDefinition));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getBalances(final int playerId, final Consumer<Map<String, Long>> consumer) {
        final Map<String, Long> balances = new LinkedHashMap<>();

        this.dsl()
                .select(CURRENCY_CODE, BALANCE_AMOUNT)
                .from(BALANCES)
                .join(CURRENCIES).on(CURRENCY_ID.eq(BALANCE_CURRENCY_ID))
                .where(BALANCE_PLAYER_ID.eq(playerId))
                .orderBy(CURRENCY_CODE.asc())
                .forEach(record -> balances.put(record.get(CURRENCY_CODE), record.get(BALANCE_AMOUNT)));

        consumer.accept(balances);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getPlayerRank(final int playerId, final Consumer<Integer> consumer) {
        final Integer rank = this.dsl()
                .select(PLAYER_RANK)
                .from(PLAYERS)
                .where(PLAYER_ID.eq(playerId))
                .limit(1)
                .fetchOne(PLAYER_RANK);

        consumer.accept(rank == null ? 0 : rank);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void adjust(final CurrencyAdjustment adjustment, final Consumer<CurrencyMovementResult> consumer) {
        final CurrencyMovementResult result = this.dsl().transactionResult(configuration -> {
            final DSLContext transaction = DSL.using(configuration);

            this.validateAdjustment(adjustment);

            final CurrencyDefinition definition = this.loadCurrency(transaction, adjustment.getCurrencyCode());
            if (!definition.isEnabled()) {
                throw new CurrencyDisabledException(adjustment.getCurrencyCode());
            }

            final CurrencyAdjustment persistedAdjustment = new CurrencyAdjustment(
                    adjustment.getPlayerId(),
                    definition.getId(),
                    adjustment.getCurrencyCode(),
                    adjustment.getOperation(),
                    adjustment.getAmount(),
                    adjustment.getSource());
            final long oldBalance = this.lockOrCreateBalance(
                    transaction,
                    persistedAdjustment.getPlayerId(),
                    definition.getId());
            final BalanceChange balanceChange = this.calculateBalanceChange(persistedAdjustment, oldBalance);

            this.validateCreditsMirror(definition, balanceChange.newBalance());
            this.updateBalance(transaction, persistedAdjustment, balanceChange.newBalance());
            this.mirrorCreditsPlayerColumn(transaction, persistedAdjustment, definition, balanceChange.newBalance());

            return this.insertMovement(transaction, persistedAdjustment, balanceChange);
        });

        consumer.accept(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upsertDefinition(
            final CurrencyDefinitionMutation mutation,
            final Consumer<ICurrencyDefinition> consumer) {
        this.validateDefinitionMutation(mutation);

        this.dsl()
                .insertInto(CURRENCIES)
                .columns(
                        CURRENCY_CODE,
                        CURRENCY_DISPLAY_NAME,
                        CURRENCY_PROTOCOL_ID,
                        CURRENCY_IS_CREDITS,
                        CURRENCY_VISIBLE_IN_PURSE,
                        CURRENCY_ENABLED,
                        CURRENCY_SORT_ORDER,
                        CURRENCY_ICON_KEY,
                        CURRENCY_NOUN_SINGULAR,
                        CURRENCY_NOUN_PLURAL,
                        CURRENCY_DESCRIPTION,
                        CURRENCY_ROLE_POLICY)
                .values(
                        mutation.getCode(),
                        mutation.getDisplayName(),
                        mutation.getProtocolCurrencyId(),
                        "0",
                        databaseBoolean(mutation.isVisibleInPurse()),
                        databaseBoolean(mutation.isEnabled()),
                        mutation.getSortOrder(),
                        emptyToNull(mutation.getIconKey()),
                        emptyToNull(mutation.getNounSingular()),
                        emptyToNull(mutation.getNounPlural()),
                        emptyToNull(mutation.getDescription()),
                        mutation.getRolePolicy().name())
                .onDuplicateKeyUpdate()
                .set(CURRENCY_DISPLAY_NAME, mutation.getDisplayName())
                .set(CURRENCY_PROTOCOL_ID, mutation.getProtocolCurrencyId())
                .set(CURRENCY_VISIBLE_IN_PURSE, databaseBoolean(mutation.isVisibleInPurse()))
                .set(CURRENCY_ENABLED, databaseBoolean(mutation.isEnabled()))
                .set(CURRENCY_SORT_ORDER, mutation.getSortOrder())
                .set(CURRENCY_ICON_KEY, emptyToNull(mutation.getIconKey()))
                .set(CURRENCY_NOUN_SINGULAR, emptyToNull(mutation.getNounSingular()))
                .set(CURRENCY_NOUN_PLURAL, emptyToNull(mutation.getNounPlural()))
                .set(CURRENCY_DESCRIPTION, emptyToNull(mutation.getDescription()))
                .set(CURRENCY_ROLE_POLICY, mutation.getRolePolicy().name())
                .execute();

        this.getDefinition(mutation.getCode(), consumer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableDefinition(final String currencyCode) {
        if (isBlank(currencyCode)) {
            throw new CurrencyAdjustmentException("Currency code is required");
        }

        this.dsl()
                .update(CURRENCIES)
                .set(CURRENCY_ENABLED, "0")
                .where(CURRENCY_CODE.eq(currencyCode).and(CURRENCY_IS_CREDITS.ne("1")))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getRoleRules(
            final String currencyCode,
            final Consumer<List<ICurrencyRoleRule>> consumer) {
        consumer.accept(this.dsl()
                .select(
                        CURRENCY_CODE,
                        ROLE_RULE_RANK_ID,
                        ROLE_RULE_CAN_VIEW,
                        ROLE_RULE_CAN_EARN,
                        ROLE_RULE_CAN_SPEND,
                        ROLE_RULE_CAN_MANAGE)
                .from(ROLE_RULES)
                .join(CURRENCIES).on(CURRENCY_ID.eq(ROLE_RULE_CURRENCY_ID))
                .where(CURRENCY_CODE.eq(currencyCode))
                .orderBy(ROLE_RULE_RANK_ID.asc())
                .fetch(this::mapCurrencyRoleRule));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upsertRoleRule(
            final CurrencyRoleRuleMutation mutation,
            final Consumer<ICurrencyRoleRule> consumer) {
        this.validateRoleRuleMutation(mutation);
        final CurrencyDefinition definition = this.loadCurrency(this.dsl(), mutation.getCurrencyCode());

        if (definition.isCredits()) {
            throw new CurrencyAdjustmentException("Credits cannot be restricted by rank");
        }

        this.dsl().transaction(configuration -> {
            final DSLContext transaction = DSL.using(configuration);

            transaction
                    .deleteFrom(ROLE_RULES)
                    .where(ROLE_RULE_CURRENCY_ID.eq(definition.getId())
                            .and(ROLE_RULE_RANK_ID.eq(mutation.getRankId())))
                    .execute();
            transaction
                    .insertInto(ROLE_RULES)
                    .columns(
                            ROLE_RULE_CURRENCY_ID,
                            ROLE_RULE_RANK_ID,
                            ROLE_RULE_CAN_VIEW,
                            ROLE_RULE_CAN_EARN,
                            ROLE_RULE_CAN_SPEND,
                            ROLE_RULE_CAN_MANAGE)
                    .values(
                            definition.getId(),
                            mutation.getRankId(),
                            databaseBoolean(mutation.canView()),
                            databaseBoolean(mutation.canEarn()),
                            databaseBoolean(mutation.canSpend()),
                            databaseBoolean(mutation.canManage()))
                    .execute();
        });

        consumer.accept(new CurrencyRoleRule(
                mutation.getCurrencyCode(),
                mutation.getRankId(),
                mutation.canView(),
                mutation.canEarn(),
                mutation.canSpend(),
                mutation.canManage()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteRoleRule(final String currencyCode, final int rankId) {
        this.dsl()
                .deleteFrom(ROLE_RULES)
                .where(ROLE_RULE_CURRENCY_ID.eq(this.loadCurrency(this.dsl(), currencyCode).getId())
                        .and(ROLE_RULE_RANK_ID.eq(rankId)))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getAliases(final String currencyCode, final Consumer<List<CurrencyAlias>> consumer) {
        consumer.accept(this.dsl()
                .select(ALIAS_NAME, CURRENCY_CODE)
                .from(ALIASES)
                .join(CURRENCIES).on(CURRENCY_ID.eq(ALIAS_CURRENCY_ID))
                .where(CURRENCY_CODE.eq(currencyCode))
                .orderBy(ALIAS_NAME.asc())
                .fetch(record -> new CurrencyAlias(record.get(ALIAS_NAME), record.get(CURRENCY_CODE))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resolveAlias(final String alias, final Consumer<String> consumer) {
        consumer.accept(this.dsl()
                .select(CURRENCY_CODE)
                .from(ALIASES)
                .join(CURRENCIES).on(CURRENCY_ID.eq(ALIAS_CURRENCY_ID))
                .where(ALIAS_NAME.eq(alias))
                .fetchOne(CURRENCY_CODE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resolveUseCase(final String useCase, final Consumer<String> consumer) {
        consumer.accept(this.dsl()
                .select(CURRENCY_CODE)
                .from(USE_CASES)
                .join(CURRENCIES).on(CURRENCY_ID.eq(USE_CASE_CURRENCY_ID))
                .where(USE_CASE_NAME.eq(useCase)
                        .and(USE_CASE_ENABLED.eq("1"))
                        .and(CURRENCY_ENABLED.eq("1")))
                .fetchOne(CURRENCY_CODE));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upsertAlias(final String alias, final String currencyCode) {
        if (isBlank(alias) || isBlank(currencyCode)) {
            throw new CurrencyAdjustmentException("Currency alias and currency code are required");
        }

        final CurrencyDefinition definition = this.loadCurrency(this.dsl(), currencyCode);
        this.dsl().transaction(configuration -> {
            final DSLContext transaction = DSL.using(configuration);

            transaction
                    .deleteFrom(ALIASES)
                    .where(ALIAS_NAME.eq(alias))
                    .execute();
            transaction
                    .insertInto(ALIASES)
                    .columns(ALIAS_NAME, ALIAS_CURRENCY_ID)
                    .values(alias, definition.getId())
                    .execute();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAlias(final String alias) {
        this.dsl()
                .deleteFrom(ALIASES)
                .where(ALIAS_NAME.eq(alias))
                .execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void getMovements(
            final int playerId,
            final int limit,
            final Consumer<List<ICurrencyMovement>> consumer) {
        final int sanitizedLimit = Math.max(MOVEMENT_QUERY_LIMIT_MIN, Math.min(MOVEMENT_QUERY_LIMIT_MAX, limit));

        consumer.accept(this.dsl()
                .select(
                        MOVEMENT_ID,
                        MOVEMENT_PLAYER_ID,
                        MOVEMENT_CURRENCY_ID,
                        CURRENCY_CODE,
                        MOVEMENT_OPERATION,
                        MOVEMENT_DELTA,
                        MOVEMENT_OLD_BALANCE,
                        MOVEMENT_NEW_BALANCE,
                        MOVEMENT_ACTOR_TYPE,
                        MOVEMENT_ACTOR_ID,
                        MOVEMENT_SOURCE_TYPE,
                        MOVEMENT_SOURCE_REF,
                        MOVEMENT_REASON,
                        MOVEMENT_CREATED_AT)
                .from(MOVEMENTS)
                .join(CURRENCIES).on(CURRENCY_ID.eq(MOVEMENT_CURRENCY_ID))
                .where(MOVEMENT_PLAYER_ID.eq(playerId))
                .orderBy(MOVEMENT_CREATED_AT.desc(), MOVEMENT_ID.desc())
                .limit(sanitizedLimit)
                .fetch(this::mapCurrencyMovement));
    }

    private CurrencyDefinition loadCurrency(final DSLContext transaction, final String currencyCode) {
        final CurrencyDefinition definition = transaction
                .select(
                        CURRENCY_ID,
                        CURRENCY_CODE,
                        CURRENCY_DISPLAY_NAME,
                        CURRENCY_PROTOCOL_ID,
                        CURRENCY_IS_CREDITS,
                        CURRENCY_VISIBLE_IN_PURSE,
                        CURRENCY_ENABLED,
                        CURRENCY_SORT_ORDER,
                        CURRENCY_ICON_KEY,
                        CURRENCY_NOUN_SINGULAR,
                        CURRENCY_NOUN_PLURAL,
                        CURRENCY_DESCRIPTION,
                        CURRENCY_ROLE_POLICY)
                .from(CURRENCIES)
                .where(CURRENCY_CODE.eq(currencyCode))
                .fetchOne(this::mapCurrencyDefinition);

        if (definition == null) {
            throw new CurrencyNotFoundException(currencyCode);
        }

        return definition;
    }

    private long lockOrCreateBalance(
            final DSLContext transaction,
            final int playerId,
            final long currencyId) {
        final Long balance = transaction
                .select(BALANCE_AMOUNT)
                .from(BALANCES)
                .where(BALANCE_PLAYER_ID.eq(playerId).and(BALANCE_CURRENCY_ID.eq(currencyId)))
                .forUpdate()
                .fetchOne(BALANCE_AMOUNT);

        if (balance != null) {
            return balance;
        }

        transaction
                .insertInto(BALANCES)
                .columns(BALANCE_PLAYER_ID, BALANCE_CURRENCY_ID, BALANCE_AMOUNT)
                .values(playerId, currencyId, 0L)
                .onDuplicateKeyIgnore()
                .execute();

        final Long insertedBalance = transaction
                .select(BALANCE_AMOUNT)
                .from(BALANCES)
                .where(BALANCE_PLAYER_ID.eq(playerId).and(BALANCE_CURRENCY_ID.eq(currencyId)))
                .forUpdate()
                .fetchOne(BALANCE_AMOUNT);

        if (insertedBalance == null) {
            throw new CurrencyAdjustmentException("Currency balance could not be locked");
        }

        return insertedBalance;
    }

    private void updateBalance(
            final DSLContext transaction,
            final CurrencyAdjustment adjustment,
            final long newBalance) {
        transaction
                .update(BALANCES)
                .set(BALANCE_AMOUNT, newBalance)
                .where(BALANCE_PLAYER_ID.eq(adjustment.getPlayerId())
                        .and(BALANCE_CURRENCY_ID.eq(adjustment.getCurrencyId())))
                .execute();
    }

    private void mirrorCreditsPlayerColumn(
            final DSLContext transaction,
            final CurrencyAdjustment adjustment,
            final CurrencyDefinition definition,
            final long newBalance) {
        if (!definition.isCredits()) {
            return;
        }

        transaction
                .update(DSL.table(DSL.name("players")))
                .set(DSL.field(DSL.name("players", "credits"), Integer.class), Math.toIntExact(newBalance))
                .where(DSL.field(DSL.name("players", "id"), Integer.class).eq(adjustment.getPlayerId()))
                .execute();
    }

    private CurrencyMovementResult insertMovement(
            final DSLContext transaction,
            final CurrencyAdjustment adjustment,
            final BalanceChange balanceChange) {
        final CurrencySource source = adjustment.getSource();
        final int affectedRows = transaction
                .insertInto(MOVEMENTS)
                .columns(
                        MOVEMENT_PLAYER_ID,
                        MOVEMENT_CURRENCY_ID,
                        MOVEMENT_OPERATION,
                        MOVEMENT_DELTA,
                        MOVEMENT_OLD_BALANCE,
                        MOVEMENT_NEW_BALANCE,
                        MOVEMENT_ACTOR_TYPE,
                        MOVEMENT_ACTOR_ID,
                        MOVEMENT_SOURCE_TYPE,
                        MOVEMENT_SOURCE_REF,
                        MOVEMENT_REASON)
                .values(
                        adjustment.getPlayerId(),
                        adjustment.getCurrencyId(),
                        adjustment.getOperation().name(),
                        balanceChange.delta(),
                        balanceChange.oldBalance(),
                        balanceChange.newBalance(),
                        source.getActorType(),
                        emptyToNull(source.getActorId()),
                        source.getSourceType(),
                        emptyToNull(source.getSourceRef()),
                        source.getReason())
                .execute();

        if (affectedRows != 1) {
            throw new CurrencyAdjustmentException("Currency movement could not be recorded");
        }

        final Long movementId = transaction.select(LAST_INSERT_ID).fetchOne(LAST_INSERT_ID);
        if (movementId == null || movementId == 0L) {
            throw new CurrencyAdjustmentException("Currency movement id could not be resolved");
        }

        final Timestamp createdAt = transaction
                .select(MOVEMENT_CREATED_AT)
                .from(MOVEMENTS)
                .where(MOVEMENT_ID.eq(movementId))
                .fetchOne(MOVEMENT_CREATED_AT);

        return new CurrencyMovementResult(
                movementId,
                adjustment.getPlayerId(),
                adjustment.getCurrencyId(),
                adjustment.getCurrencyCode(),
                adjustment.getOperation(),
                balanceChange.delta(),
                balanceChange.oldBalance(),
                balanceChange.newBalance(),
                source.getActorType(),
                source.getActorId(),
                source.getSourceType(),
                source.getSourceRef(),
                source.getReason(),
                createdAt == null ? Instant.now() : createdAt.toInstant());
    }

    private BalanceChange calculateBalanceChange(final CurrencyAdjustment adjustment, final long oldBalance) {
        final long amount = adjustment.getAmount();
        final CurrencyOperation operation = adjustment.getOperation();

        if (operation == CurrencyOperation.ADD) {
            final long newBalance = Math.addExact(oldBalance, amount);
            return new BalanceChange(oldBalance, amount, newBalance);
        }

        if (operation == CurrencyOperation.REMOVE) {
            if (oldBalance < amount) {
                throw new InsufficientCurrencyBalanceException(
                        adjustment.getCurrencyCode(),
                        oldBalance,
                        amount);
            }

            return new BalanceChange(oldBalance, -amount, oldBalance - amount);
        }

        if (operation == CurrencyOperation.SET) {
            return new BalanceChange(oldBalance, amount - oldBalance, amount);
        }

        throw new CurrencyAdjustmentException("Unsupported currency operation: " + operation);
    }

    private void validateAdjustment(final CurrencyAdjustment adjustment) {
        if (adjustment == null) {
            throw new CurrencyAdjustmentException("Currency adjustment is required");
        }

        if (adjustment.getPlayerId() <= 0) {
            throw new CurrencyAdjustmentException("Player id must be positive");
        }

        if (isBlank(adjustment.getCurrencyCode())) {
            throw new CurrencyAdjustmentException("Currency code is required");
        }

        if (!adjustment.getCurrencyCode().equals(adjustment.getCurrencyCode().toLowerCase(Locale.ROOT))) {
            throw new CurrencyAdjustmentException("Currency code must be lowercase");
        }

        if (adjustment.getOperation() == null) {
            throw new CurrencyAdjustmentException("Currency operation is required");
        }

        if (adjustment.getAmount() < 0) {
            throw new CurrencyAdjustmentException("Currency amount must be non-negative");
        }

        this.validateSource(adjustment.getSource());
    }

    private void validateDefinitionMutation(final CurrencyDefinitionMutation mutation) {
        if (mutation == null) {
            throw new CurrencyAdjustmentException("Currency definition mutation is required");
        }

        if (isBlank(mutation.getCode())) {
            throw new CurrencyAdjustmentException("Currency code is required");
        }

        if (!mutation.getCode().matches("[a-z][a-z0-9_]{1,63}")) {
            throw new CurrencyAdjustmentException("Currency code must be lowercase snake_case");
        }

        if (isBlank(mutation.getDisplayName())) {
            throw new CurrencyAdjustmentException("Currency display name is required");
        }
    }

    private void validateRoleRuleMutation(final CurrencyRoleRuleMutation mutation) {
        if (mutation == null) {
            throw new CurrencyAdjustmentException("Currency role rule mutation is required");
        }

        if (isBlank(mutation.getCurrencyCode())) {
            throw new CurrencyAdjustmentException("Currency code is required");
        }

        if (mutation.getRankId() < 0) {
            throw new CurrencyAdjustmentException("Rank id must be non-negative");
        }
    }

    private void validateSource(final CurrencySource source) {
        if (source == null) {
            throw new CurrencyAdjustmentException("Currency source is required");
        }

        if (isBlank(source.getActorType())) {
            throw new CurrencyAdjustmentException("Currency source actor type is required");
        }

        if (isBlank(source.getSourceType())) {
            throw new CurrencyAdjustmentException("Currency source type is required");
        }

        if (isBlank(source.getReason())) {
            throw new CurrencyAdjustmentException("Currency source reason is required");
        }
    }

    private void validateCreditsMirror(final CurrencyDefinition definition, final long newBalance) {
        if (definition.isCredits() && newBalance > CREDITS_COLUMN_MAX_VALUE) {
            throw new CurrencyAdjustmentException("Credits balance exceeds player column capacity");
        }
    }

    private CurrencyDefinition mapCurrencyDefinition(final Record record) {
        return new CurrencyDefinition(
                record.get(CURRENCY_ID),
                record.get(CURRENCY_CODE),
                record.get(CURRENCY_DISPLAY_NAME),
                record.get(CURRENCY_PROTOCOL_ID),
                isDatabaseTrue(record.get(CURRENCY_IS_CREDITS)),
                isDatabaseTrue(record.get(CURRENCY_VISIBLE_IN_PURSE)),
                isDatabaseTrue(record.get(CURRENCY_ENABLED)),
                record.get(CURRENCY_SORT_ORDER),
                record.get(CURRENCY_ICON_KEY),
                record.get(CURRENCY_NOUN_SINGULAR),
                record.get(CURRENCY_NOUN_PLURAL),
                record.get(CURRENCY_DESCRIPTION),
                rolePolicy(record.get(CURRENCY_ROLE_POLICY)));
    }

    private CurrencyMovementResult mapCurrencyMovement(final Record record) {
        final Timestamp createdAt = record.get(MOVEMENT_CREATED_AT);

        return new CurrencyMovementResult(
                record.get(MOVEMENT_ID),
                record.get(MOVEMENT_PLAYER_ID),
                record.get(MOVEMENT_CURRENCY_ID),
                record.get(CURRENCY_CODE),
                CurrencyOperation.valueOf(record.get(MOVEMENT_OPERATION)),
                record.get(MOVEMENT_DELTA),
                record.get(MOVEMENT_OLD_BALANCE),
                record.get(MOVEMENT_NEW_BALANCE),
                record.get(MOVEMENT_ACTOR_TYPE),
                record.get(MOVEMENT_ACTOR_ID),
                record.get(MOVEMENT_SOURCE_TYPE),
                record.get(MOVEMENT_SOURCE_REF),
                record.get(MOVEMENT_REASON),
                createdAt == null ? Instant.EPOCH : createdAt.toInstant());
    }

    private CurrencyRoleRule mapCurrencyRoleRule(final Record record) {
        return new CurrencyRoleRule(
                record.get(CURRENCY_CODE),
                record.get(ROLE_RULE_RANK_ID),
                isDatabaseTrue(record.get(ROLE_RULE_CAN_VIEW)),
                isDatabaseTrue(record.get(ROLE_RULE_CAN_EARN)),
                isDatabaseTrue(record.get(ROLE_RULE_CAN_SPEND)),
                isDatabaseTrue(record.get(ROLE_RULE_CAN_MANAGE)));
    }

    private static boolean isDatabaseTrue(final String value) {
        return "1".equals(value) || "true".equalsIgnoreCase(value);
    }

    private static boolean isBlank(final String value) {
        return value == null || value.isBlank();
    }

    private static String emptyToNull(final String value) {
        return isBlank(value) ? null : value;
    }

    private static String databaseBoolean(final boolean value) {
        return value ? "1" : "0";
    }

    private static CurrencyRolePolicy rolePolicy(final String value) {
        if (isBlank(value)) {
            return CurrencyRolePolicy.ALL;
        }

        return CurrencyRolePolicy.valueOf(value);
    }

    private record BalanceChange(long oldBalance, long delta, long newBalance) {
    }
}
