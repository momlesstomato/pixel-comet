package com.cometproject.server.storage.jooq.repositories;

import com.cometproject.server.storage.jooq.IJooqDslProvider;
import com.cometproject.storage.api.data.currency.CurrencyAdjustment;
import com.cometproject.storage.api.data.currency.CurrencyAlias;
import com.cometproject.storage.api.data.currency.CurrencyMovementResult;
import com.cometproject.storage.api.data.currency.CurrencyOperation;
import com.cometproject.storage.api.data.currency.CurrencyRoleRuleMutation;
import com.cometproject.storage.api.data.currency.CurrencySource;
import com.cometproject.storage.api.data.currency.ICurrencyDefinition;
import com.cometproject.storage.api.data.currency.ICurrencyMovement;
import com.cometproject.storage.api.data.currency.ICurrencyRoleRule;
import com.cometproject.storage.api.data.currency.exceptions.CurrencyDisabledException;
import com.cometproject.storage.api.data.currency.exceptions.InsufficientCurrencyBalanceException;
import org.h2.jdbcx.JdbcDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JooqCurrencyRepositoryTest {
    private DSLContext dsl;
    private JooqCurrencyRepository repository;

    @BeforeEach
    void setUp() {
        this.dsl = DSL.using(this.createDataSource(), SQLDialect.H2);
        this.repository = new JooqCurrencyRepository(new TestJooqDslProvider(this.dsl));

        this.createSchema();
        this.seedPlayers();
        this.seedCurrencies();
    }

    @Test
    void getDefinitions_returnsSeededCurrenciesInSortOrder() {
        final AtomicReference<List<ICurrencyDefinition>> definitions = new AtomicReference<>();

        this.repository.getDefinitions(definitions::set);

        assertEquals(3, definitions.get().size());
        assertEquals("credits", definitions.get().get(0).getCode());
        assertEquals("currency_0", definitions.get().get(1).getCode());
        assertEquals("disabled", definitions.get().get(2).getCode());
    }

    @Test
    void adjust_addsBalanceAndRecordsMovement() {
        final CurrencyMovementResult result = this.adjust(1, "currency_0", CurrencyOperation.ADD, 15);

        assertEquals(25, result.getOldBalance());
        assertEquals(15, result.getDelta());
        assertEquals(40, result.getNewBalance());
        assertEquals(40, this.balance(1, "currency_0"));
        assertEquals(1, this.movementCount());
    }

    @Test
    void adjust_removesBalanceAndRecordsMovement() {
        final CurrencyMovementResult result = this.adjust(1, "currency_0", CurrencyOperation.REMOVE, 10);

        assertEquals(25, result.getOldBalance());
        assertEquals(-10, result.getDelta());
        assertEquals(15, result.getNewBalance());
        assertEquals(15, this.balance(1, "currency_0"));
        assertEquals(1, this.movementCount());
    }

    @Test
    void adjust_setsBalanceAndRecordsMovement() {
        final CurrencyMovementResult result = this.adjust(1, "credits", CurrencyOperation.SET, 125);

        assertEquals(100, result.getOldBalance());
        assertEquals(25, result.getDelta());
        assertEquals(125, result.getNewBalance());
        assertEquals(125, this.balance(1, "credits"));
        assertEquals(125, this.playerColumn(1, "credits"));
        assertEquals(1, this.movementCount());
    }

    @Test
    void adjust_rejectsInsufficientFundsAndRollsBack() {
        assertThrows(InsufficientCurrencyBalanceException.class,
                () -> this.adjust(1, "currency_0", CurrencyOperation.REMOVE, 26));

        assertEquals(25, this.balance(1, "currency_0"));
        assertEquals(0, this.movementCount());
    }

    @Test
    void adjust_rejectsDisabledCurrencyAndDoesNotCreateBalance() {
        assertThrows(CurrencyDisabledException.class,
                () -> this.adjust(1, "disabled", CurrencyOperation.ADD, 1));

        assertEquals(0, this.dsl.fetchCount(DSL.table("player_currency_balances"),
                DSL.field("currency_id").eq(3)));
        assertEquals(0, this.movementCount());
    }

    @Test
    void adjust_rollsBackBalanceWhenMovementCannotBeMirroredToLegacyColumn() {
        assertThrows(RuntimeException.class,
                () -> this.adjust(1, "credits", CurrencyOperation.SET, (long) Integer.MAX_VALUE + 1));

        assertEquals(100, this.balance(1, "credits"));
        assertEquals(100, this.playerColumn(1, "credits"));
        assertEquals(0, this.movementCount());
    }

    @Test
    void getBalances_returnsPlayerInventorySnapshot() {
        final AtomicReference<Map<String, Long>> balances = new AtomicReference<>();

        this.repository.getBalances(1, balances::set);

        assertEquals(2, balances.get().size());
        assertEquals(100, balances.get().get("credits"));
        assertEquals(25, balances.get().get("currency_0"));
    }

    @Test
    void getMovements_returnsLatestMovementsFirst() {
        this.adjust(1, "currency_0", CurrencyOperation.ADD, 15);
        this.adjust(1, "credits", CurrencyOperation.SET, 125);

        final AtomicReference<List<ICurrencyMovement>> movements = new AtomicReference<>();
        this.repository.getMovements(1, 10, movements::set);

        assertEquals(2, movements.get().size());
        assertTrue(movements.get().get(0).getId() > movements.get().get(1).getId());
        assertEquals("credits", movements.get().get(0).getCurrencyCode());
    }

    @Test
    void roleRules_canBeCreatedAndDeletedForNonCreditCurrencies() {
        final AtomicReference<ICurrencyRoleRule> roleRule = new AtomicReference<>();

        this.repository.upsertRoleRule(new CurrencyRoleRuleMutation(
                "currency_0", 5, true, false, true, false), roleRule::set);

        assertEquals("currency_0", roleRule.get().getCurrencyCode());
        assertEquals(5, roleRule.get().getRankId());
        assertTrue(roleRule.get().canView());
        assertTrue(roleRule.get().canSpend());

        final AtomicReference<List<ICurrencyRoleRule>> rules = new AtomicReference<>();
        this.repository.getRoleRules("currency_0", rules::set);

        assertEquals(1, rules.get().size());

        this.repository.deleteRoleRule("currency_0", 5);
        this.repository.getRoleRules("currency_0", rules::set);

        assertTrue(rules.get().isEmpty());
    }

    @Test
    void aliases_resolveConfiguredNamesToCanonicalCurrencyCodes() {
        this.repository.upsertAlias("protocol_0", "currency_0");

        final AtomicReference<String> resolvedCode = new AtomicReference<>();
        final AtomicReference<List<CurrencyAlias>> aliases = new AtomicReference<>();

        this.repository.resolveAlias("protocol_0", resolvedCode::set);
        this.repository.getAliases("currency_0", aliases::set);

        assertEquals("currency_0", resolvedCode.get());
        assertEquals(1, aliases.get().size());
        assertEquals("protocol_0", aliases.get().get(0).getAlias());

        this.repository.deleteAlias("protocol_0");
        this.repository.resolveAlias("protocol_0", resolvedCode::set);

        assertNull(resolvedCode.get());
    }

    private CurrencyMovementResult adjust(
            final int playerId,
            final String currencyCode,
            final CurrencyOperation operation,
            final long amount) {
        final AtomicReference<CurrencyMovementResult> result = new AtomicReference<>();

        this.repository.adjust(new CurrencyAdjustment(
                playerId,
                this.currencyId(currencyCode),
                currencyCode,
                operation,
                amount,
                new CurrencySource("system", "", "test", "", "repository contract test")), result::set);

        return result.get();
    }

    private long balance(final int playerId, final String currencyCode) {
        return this.dsl.select(DSL.field("balance", Long.class))
                .from("player_currency_balances")
                .join("currencies")
                .on(DSL.field("currencies.id").eq(DSL.field("player_currency_balances.currency_id")))
                .where(DSL.field("player_id", Integer.class).eq(playerId)
                        .and(DSL.field("code", String.class).eq(currencyCode)))
                .fetchOne(DSL.field("balance", Long.class));
    }

    private long currencyId(final String currencyCode) {
        return this.dsl.select(DSL.field("id", Long.class))
                .from("currencies")
                .where(DSL.field("code", String.class).eq(currencyCode))
                .fetchOne(DSL.field("id", Long.class));
    }

    private int playerColumn(final int playerId, final String column) {
        return this.dsl.select(DSL.field(DSL.name(column), Integer.class))
                .from("players")
                .where(DSL.field("id", Integer.class).eq(playerId))
                .fetchOne(DSL.field(DSL.name(column), Integer.class));
    }

    private int movementCount() {
        return this.dsl.fetchCount(DSL.table("player_currency_movements"));
    }

    private DataSource createDataSource() {
        final JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:currency_repository_" + UUID.randomUUID()
                + ";MODE=MySQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    private void createSchema() {
        this.dsl.execute("""
                CREATE TABLE players (
                  id int NOT NULL,
                  rank int NOT NULL DEFAULT 1,
                  credits int NOT NULL DEFAULT 0,
                  PRIMARY KEY (id)
                )
                """);
        this.dsl.execute("""
                CREATE TABLE currencies (
                  id bigint NOT NULL AUTO_INCREMENT,
                  code varchar(64) NOT NULL,
                  display_name varchar(100) NOT NULL,
                  protocol_currency_id int NULL,
                  is_credits varchar(1) NOT NULL DEFAULT '0',
                  visible_in_purse varchar(1) NOT NULL DEFAULT '1',
                  enabled varchar(1) NOT NULL DEFAULT '1',
                  sort_order int NOT NULL DEFAULT 0,
                  icon_key varchar(100) NULL,
                  noun_singular varchar(100) NULL,
                  noun_plural varchar(100) NULL,
                  description varchar(255) NULL,
                  role_policy varchar(16) NOT NULL DEFAULT 'ALL',
                  PRIMARY KEY (id),
                  UNIQUE KEY uq_currencies_code (code)
                )
                """);
        this.dsl.execute("""
                CREATE TABLE currency_role_rules (
                  currency_id bigint NOT NULL,
                  rank_id int NOT NULL,
                  can_view varchar(1) NOT NULL DEFAULT '1',
                  can_earn varchar(1) NOT NULL DEFAULT '1',
                  can_spend varchar(1) NOT NULL DEFAULT '1',
                  can_manage varchar(1) NOT NULL DEFAULT '0',
                  PRIMARY KEY (currency_id, rank_id)
                )
                """);
        this.dsl.execute("""
                CREATE TABLE currency_aliases (
                  alias varchar(64) NOT NULL,
                  currency_id bigint NOT NULL,
                  PRIMARY KEY (alias)
                )
                """);
        this.dsl.execute("""
                CREATE TABLE player_currency_balances (
                  player_id int NOT NULL,
                  currency_id bigint NOT NULL,
                  balance bigint NOT NULL DEFAULT 0,
                  PRIMARY KEY (player_id, currency_id)
                )
                """);
        this.dsl.execute("""
                CREATE TABLE player_currency_movements (
                  id bigint NOT NULL AUTO_INCREMENT,
                  player_id int NOT NULL,
                  currency_id bigint NOT NULL,
                  operation varchar(16) NOT NULL,
                  delta bigint NOT NULL,
                  old_balance bigint NOT NULL,
                  new_balance bigint NOT NULL,
                  actor_type varchar(32) NOT NULL,
                  actor_id varchar(64) NULL,
                  source_type varchar(64) NOT NULL,
                  source_ref varchar(128) NULL,
                  reason varchar(255) NOT NULL,
                  created_at timestamp NOT NULL DEFAULT current_timestamp(),
                  PRIMARY KEY (id)
                )
                """);
    }

    private void seedPlayers() {
        this.dsl.execute("""
                INSERT INTO players (id, credits)
                VALUES (1, 100), (2, 200)
                """);
    }

    private void seedCurrencies() {
        this.dsl.execute("""
                INSERT INTO currencies (
                  id,
                  code,
                  display_name,
                  protocol_currency_id,
                  is_credits,
                  visible_in_purse,
                  enabled,
                  sort_order,
                  icon_key
                ) VALUES
                  (1, 'credits', 'Credits', NULL, '1', '1', '1', 0, 'credits'),
                  (2, 'currency_0', 'Currency 0', 0, '0', '1', '1', 10, 'currency_0'),
                  (3, 'disabled', 'Disabled', 99, '0', '1', '0', 20, 'disabled')
                """);
        this.dsl.execute("""
                INSERT INTO player_currency_balances (player_id, currency_id, balance)
                VALUES (1, 1, 100), (1, 2, 25), (2, 1, 200), (2, 2, 50)
                """);
    }

    private static final class TestJooqDslProvider implements IJooqDslProvider {
        private final DSLContext dsl;

        private TestJooqDslProvider(final DSLContext dsl) {
            this.dsl = dsl;
        }

        @Override
        public DSLContext dsl() {
            return this.dsl;
        }
    }
}
