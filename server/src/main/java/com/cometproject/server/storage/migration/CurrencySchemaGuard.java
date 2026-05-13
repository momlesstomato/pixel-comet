package com.cometproject.server.storage.migration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Ensures the runtime currency schema exists even when a development database has stale Flyway history.
 *
 * <p>This guard is intentionally idempotent. It repairs the currency inventory tables that are now
 * mandatory for login, while preserving existing balances when the tables already contain data.
 */
public final class CurrencySchemaGuard {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencySchemaGuard.class);

    private final DataSource dataSource;

    /**
     * Creates the currency schema guard.
     *
     * @param dataSource the active database data source.
     */
    public CurrencySchemaGuard(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Creates and seeds missing currency runtime tables.
     */
    public void ensure() {
        try (Connection connection = this.dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            final DatabaseMetaData metaData = connection.getMetaData();
            final String catalog = connection.getCatalog();
            final String schema = connection.getSchema();

            final boolean missingCurrencies = !this.hasTable(metaData, catalog, schema, "currencies");

            this.createTables(statement);
            this.ensureColumns(metaData, catalog, schema, statement);
            this.seedCurrencies(statement);
            this.backfillBalances(metaData, catalog, schema, statement);
            this.seedAliases(statement);
            this.seedUseCases(statement);
            this.backfillCatalogPrices(metaData, catalog, schema, statement);

            if (missingCurrencies) {
                LOGGER.warn("Currency schema was missing after Flyway; runtime currency tables were repaired.");
            }
        } catch (SQLException exception) {
            throw new IllegalStateException("Failed to ensure currency runtime schema.", exception);
        }
    }

    private void createTables(final Statement statement) throws SQLException {
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `currencies` (
                  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                  `code` varchar(64) NOT NULL,
                  `display_name` varchar(100) NOT NULL,
                  `protocol_currency_id` int NULL,
                  `is_credits` enum('1','0') NOT NULL DEFAULT '0',
                  `visible_in_purse` enum('1','0') NOT NULL DEFAULT '1',
                  `enabled` enum('1','0') NOT NULL DEFAULT '1',
                  `sort_order` int NOT NULL DEFAULT 0,
                  `icon_key` varchar(100) NULL,
                  `noun_singular` varchar(100) NULL,
                  `noun_plural` varchar(100) NULL,
                  `description` varchar(255) NULL,
                  `role_policy` varchar(16) NOT NULL DEFAULT 'ALL',
                  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                  PRIMARY KEY (`id`),
                  UNIQUE KEY `uq_currencies_code` (`code`),
                  UNIQUE KEY `uq_currencies_protocol_currency_id` (`protocol_currency_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC
                """);
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `player_currency_balances` (
                  `player_id` int NOT NULL,
                  `currency_id` bigint unsigned NOT NULL,
                  `balance` bigint NOT NULL DEFAULT 0,
                  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                  PRIMARY KEY (`player_id`, `currency_id`),
                  KEY `idx_player_currency_balances_currency` (`currency_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC
                """);
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `player_currency_movements` (
                  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                  `player_id` int NOT NULL,
                  `currency_id` bigint unsigned NOT NULL,
                  `operation` varchar(16) NOT NULL,
                  `delta` bigint NOT NULL,
                  `old_balance` bigint NOT NULL,
                  `new_balance` bigint NOT NULL,
                  `actor_type` varchar(32) NOT NULL,
                  `actor_id` varchar(64) NULL,
                  `source_type` varchar(64) NOT NULL,
                  `source_ref` varchar(128) NULL,
                  `reason` varchar(255) NOT NULL,
                  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
                  PRIMARY KEY (`id`),
                  KEY `idx_currency_movements_player_created` (`player_id`, `created_at`),
                  KEY `idx_currency_movements_currency_created` (`currency_id`, `created_at`),
                  KEY `idx_currency_movements_source` (`source_type`, `source_ref`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC
                """);
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `currency_role_rules` (
                  `currency_id` bigint unsigned NOT NULL,
                  `rank_id` int NOT NULL,
                  `can_view` enum('1','0') NOT NULL DEFAULT '1',
                  `can_earn` enum('1','0') NOT NULL DEFAULT '1',
                  `can_spend` enum('1','0') NOT NULL DEFAULT '1',
                  `can_manage` enum('1','0') NOT NULL DEFAULT '0',
                  PRIMARY KEY (`currency_id`, `rank_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC
                """);
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `currency_aliases` (
                  `alias` varchar(64) NOT NULL,
                  `currency_id` bigint unsigned NOT NULL,
                  PRIMARY KEY (`alias`),
                  KEY `idx_currency_aliases_currency` (`currency_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC
                """);
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `currency_use_cases` (
                  `use_case` varchar(128) NOT NULL,
                  `currency_id` bigint unsigned NOT NULL,
                  `required_rank` int NULL,
                  `enabled` enum('1','0') NOT NULL DEFAULT '1',
                  `metadata_json` text NULL,
                  PRIMARY KEY (`use_case`),
                  KEY `idx_currency_use_cases_currency` (`currency_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC
                """);
        statement.execute("""
                CREATE TABLE IF NOT EXISTS `catalog_item_prices` (
                  `catalog_item_id` int unsigned NOT NULL,
                  `currency_id` bigint unsigned NOT NULL,
                  `amount` bigint NOT NULL DEFAULT 0,
                  `display_order` int NOT NULL DEFAULT 0,
                  `client_visible` enum('1','0') NOT NULL DEFAULT '1',
                  `required_rank` int NULL,
                  PRIMARY KEY (`catalog_item_id`, `currency_id`),
                  KEY `idx_catalog_item_prices_currency` (`currency_id`)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC
                """);
    }

    private void ensureColumns(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final Statement statement) throws SQLException {
        this.ensureColumn(metaData, catalog, schema, statement, "currencies", "noun_singular", "varchar(100) NULL");
        this.ensureColumn(metaData, catalog, schema, statement, "currencies", "noun_plural", "varchar(100) NULL");
        this.ensureColumn(metaData, catalog, schema, statement, "currencies", "description", "varchar(255) NULL");
        this.ensureColumn(metaData, catalog, schema, statement, "currencies", "role_policy", "varchar(16) NOT NULL DEFAULT 'ALL'");
        this.ensureColumn(metaData, catalog, schema, statement, "currency_use_cases", "required_rank", "int NULL");
        this.ensureColumn(metaData, catalog, schema, statement, "currency_use_cases", "enabled", "enum('1','0') NOT NULL DEFAULT '1'");
        this.ensureColumn(metaData, catalog, schema, statement, "currency_use_cases", "metadata_json", "text NULL");
        this.ensureColumn(metaData, catalog, schema, statement, "catalog_item_prices", "client_visible", "enum('1','0') NOT NULL DEFAULT '1'");
        this.ensureColumn(metaData, catalog, schema, statement, "catalog_item_prices", "required_rank", "int NULL");
    }

    private void seedCurrencies(final Statement statement) throws SQLException {
        statement.execute("""
                INSERT INTO `currencies` (
                  `code`, `display_name`, `protocol_currency_id`, `is_credits`, `visible_in_purse`,
                  `enabled`, `sort_order`, `icon_key`, `noun_singular`, `noun_plural`, `description`, `role_policy`
                ) VALUES
                  ('credits', 'Credits', NULL, '1', '1', '1', 0, 'credits', 'Credit', 'Credits', 'Protocol credits balance.', 'ALL'),
                  ('currency_0', 'Pixels', 0, '0', '1', '1', 10, 'pixels', 'Pixel', 'Pixels', 'Legacy protocol currency 0 balance.', 'ALL'),
                  ('currency_5', 'Diamonds', 5, '0', '1', '1', 20, 'diamonds', 'Diamond', 'Diamonds', 'Legacy protocol currency 5 balance.', 'ALL'),
                  ('currency_103', 'Duckets', 103, '0', '1', '1', 30, 'duckets', 'Ducket', 'Duckets', 'Legacy protocol currency 103 balance.', 'ALL'),
                  ('currency_105', 'Tokens', 105, '0', '1', '1', 40, 'tokens', 'Token', 'Tokens', 'Legacy protocol currency 105 balance.', 'ALL')
                ON DUPLICATE KEY UPDATE
                  `display_name` = VALUES(`display_name`),
                  `protocol_currency_id` = VALUES(`protocol_currency_id`),
                  `is_credits` = VALUES(`is_credits`),
                  `visible_in_purse` = VALUES(`visible_in_purse`),
                  `enabled` = VALUES(`enabled`),
                  `sort_order` = VALUES(`sort_order`),
                  `icon_key` = VALUES(`icon_key`),
                  `noun_singular` = VALUES(`noun_singular`),
                  `noun_plural` = VALUES(`noun_plural`),
                  `description` = VALUES(`description`),
                  `role_policy` = VALUES(`role_policy`)
                """);
    }

    private void backfillBalances(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final Statement statement) throws SQLException {
        if (!this.hasTable(metaData, catalog, schema, "players")) {
            return;
        }

        this.backfillBalance(statement, "credits", "credits");
        this.backfillBalance(statement, "currency_0", this.balanceExpression(metaData, catalog, schema, "activity_points"));
        this.backfillBalance(statement, "currency_5", this.balanceExpression(metaData, catalog, schema, "vip_points"));
        this.backfillBalance(statement, "currency_103", this.balanceExpression(metaData, catalog, schema, "seasonal_points"));
        this.backfillBalance(statement, "currency_105", this.balanceExpression(metaData, catalog, schema, "black_money"));
    }

    private void backfillBalance(
            final Statement statement,
            final String currencyCode,
            final String balanceExpression) throws SQLException {
        statement.execute("""
                INSERT INTO `player_currency_balances` (`player_id`, `currency_id`, `balance`)
                SELECT p.`id`, c.`id`, COALESCE(%s, 0)
                FROM `players` p
                JOIN `currencies` c ON c.`code` = '%s'
                ON DUPLICATE KEY UPDATE
                  `balance` = `player_currency_balances`.`balance`
                """.formatted(balanceExpression, currencyCode));
    }

    private void seedAliases(final Statement statement) throws SQLException {
        statement.execute("""
                INSERT INTO `currency_aliases` (`alias`, `currency_id`)
                SELECT 'credits', `id` FROM `currencies` WHERE `code` = 'credits'
                UNION ALL SELECT 'pixels', `id` FROM `currencies` WHERE `code` = 'currency_0'
                UNION ALL SELECT 'protocol_0', `id` FROM `currencies` WHERE `code` = 'currency_0'
                UNION ALL SELECT 'diamonds', `id` FROM `currencies` WHERE `code` = 'currency_5'
                UNION ALL SELECT 'protocol_5', `id` FROM `currencies` WHERE `code` = 'currency_5'
                UNION ALL SELECT 'duckets', `id` FROM `currencies` WHERE `code` = 'currency_103'
                UNION ALL SELECT 'protocol_103', `id` FROM `currencies` WHERE `code` = 'currency_103'
                UNION ALL SELECT 'tokens', `id` FROM `currencies` WHERE `code` = 'currency_105'
                UNION ALL SELECT 'protocol_105', `id` FROM `currencies` WHERE `code` = 'currency_105'
                ON DUPLICATE KEY UPDATE
                  `currency_id` = VALUES(`currency_id`)
                """);
    }

    private void seedUseCases(final Statement statement) throws SQLException {
        final List<String[]> useCases = List.of(
                new String[]{"online_reward.primary", "currency_0"},
                new String[]{"online_reward.club", "currency_5"},
                new String[]{"catalog.promotion.primary", "currency_5"},
                new String[]{"catalog.promotion.secondary", "currency_0"},
                new String[]{"camera.share", "currency_0"},
                new String[]{"fireworks.purchase", "currency_0"},
                new String[]{"casino.bet", "currency_105"},
                new String[]{"casino.payout", "currency_105"},
                new String[]{"casino.payout.primary", "currency_0"},
                new String[]{"casino.payout.secondary", "currency_5"},
                new String[]{"bank.transfer.primary", "currency_5"},
                new String[]{"bank.transfer.secondary", "currency_0"},
                new String[]{"bank.deposit", "currency_5"},
                new String[]{"bank.withdraw", "currency_5"},
                new String[]{"room.purchase", "currency_0"},
                new String[]{"quest.reward.primary", "currency_0"},
                new String[]{"quest.reward.secondary", "currency_103"},
                new String[]{"quest.reward.special", "currency_105"},
                new String[]{"quest.reward.default", "currency_0"},
                new String[]{"calendar.reward.primary", "currency_0"},
                new String[]{"calendar.reward.secondary", "currency_5"},
                new String[]{"calendar.reward.special", "currency_103"},
                new String[]{"crackable.reward.primary", "currency_0"},
                new String[]{"crackable.reward.secondary", "currency_5"},
                new String[]{"crackable.reward.default", "currency_0"},
                new String[]{"reward_command.primary", "currency_5"},
                new String[]{"reward_command.secondary", "currency_103"},
                new String[]{"poll.reward.primary", "currency_0"},
                new String[]{"poll.reward.secondary", "currency_5"},
                new String[]{"subscription.reward.primary", "currency_0"},
                new String[]{"subscription.reward.secondary", "currency_5"},
                new String[]{"exchange.primary", "currency_0"},
                new String[]{"exchange.secondary", "currency_5"},
                new String[]{"battle_pass.reward", "currency_5"},
                new String[]{"staff_bundle.primary", "currency_5"},
                new String[]{"staff_event.primary", "currency_0"},
                new String[]{"staff_event.secondary", "currency_5"},
                new String[]{"staff_event.special", "currency_103"},
                new String[]{"wired.condition.primary", "currency_0"},
                new String[]{"wired.condition.secondary", "currency_5"});

        final StringBuilder sql = new StringBuilder("INSERT INTO `currency_use_cases` (`use_case`, `currency_id`) ");
        for (int i = 0; i < useCases.size(); i++) {
            if (i > 0) {
                sql.append(" UNION ALL ");
            }
            sql.append("SELECT '")
                    .append(useCases.get(i)[0])
                    .append("', `id` FROM `currencies` WHERE `code` = '")
                    .append(useCases.get(i)[1])
                    .append("'");
        }
        sql.append(" ON DUPLICATE KEY UPDATE `currency_id` = VALUES(`currency_id`)");
        statement.execute(sql.toString());
    }

    private void backfillCatalogPrices(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final Statement statement) throws SQLException {
        if (!this.hasTable(metaData, catalog, schema, "catalog_items")) {
            return;
        }

        final List<String> selects = new ArrayList<>();
        this.addCatalogPriceSelect(metaData, catalog, schema, selects, "credits", "cost_credits", 0);
        this.addCatalogPriceSelect(metaData, catalog, schema, selects, "currency_0", "cost_pixels", 10);
        this.addCatalogPriceSelect(metaData, catalog, schema, selects, "currency_5", "cost_diamonds", 20);
        this.addCatalogPriceSelect(metaData, catalog, schema, selects, "currency_103", "cost_seasonal", 30);
        this.addCatalogPriceSelect(metaData, catalog, schema, selects, "currency_105", "cost_tokens", 40);

        if (selects.isEmpty()) {
            return;
        }

        statement.execute("""
                INSERT INTO `catalog_item_prices` (`catalog_item_id`, `currency_id`, `amount`, `display_order`, `client_visible`)
                %s
                ON DUPLICATE KEY UPDATE
                  `amount` = VALUES(`amount`),
                  `display_order` = VALUES(`display_order`),
                  `client_visible` = VALUES(`client_visible`)
                """.formatted(String.join(" UNION ALL ", selects)));
    }

    private void addCatalogPriceSelect(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final List<String> selects,
            final String currencyCode,
            final String column,
            final int displayOrder) throws SQLException {
        if (!this.hasColumn(metaData, catalog, schema, "catalog_items", column)) {
            return;
        }

        selects.add("""
                SELECT ci.`id`, c.`id`, ci.`%s`, %d, '1'
                FROM `catalog_items` ci
                JOIN `currencies` c ON c.`code` = '%s'
                WHERE ci.`%s` > 0
                """.formatted(column, displayOrder, currencyCode, column));
    }

    private String balanceExpression(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final String column) throws SQLException {
        return this.hasColumn(metaData, catalog, schema, "players", column) ? "p.`" + column + "`" : "0";
    }

    private void ensureColumn(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final Statement statement,
            final String tableName,
            final String columnName,
            final String definition) throws SQLException {
        if (!this.hasColumn(metaData, catalog, schema, tableName, columnName)) {
            statement.execute("ALTER TABLE `" + tableName + "` ADD COLUMN `" + columnName + "` " + definition);
        }
    }

    private boolean hasTable(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final String tableName) throws SQLException {
        try (ResultSet resultSet = metaData.getTables(catalog, schema, tableName, new String[]{"TABLE"})) {
            if (resultSet.next()) {
                return true;
            }
        }

        try (ResultSet resultSet = metaData.getTables(catalog, schema, tableName.toUpperCase(), new String[]{"TABLE"})) {
            return resultSet.next();
        }
    }

    private boolean hasColumn(
            final DatabaseMetaData metaData,
            final String catalog,
            final String schema,
            final String tableName,
            final String columnName) throws SQLException {
        try (ResultSet resultSet = metaData.getColumns(catalog, schema, tableName, columnName)) {
            if (resultSet.next()) {
                return true;
            }
        }

        try (ResultSet resultSet = metaData.getColumns(catalog, schema, tableName.toUpperCase(), columnName.toUpperCase())) {
            return resultSet.next();
        }
    }
}
