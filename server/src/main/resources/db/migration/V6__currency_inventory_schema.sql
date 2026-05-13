-- Defines the canonical player currency inventory.
-- Legacy player balance columns are used only for this development backfill and then removed.

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
  `role_policy` enum('ALL','ALLOW_LIST','DENY_LIST') NOT NULL DEFAULT 'ALL',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_currencies_code` (`code`),
  UNIQUE KEY `uq_currencies_protocol_currency_id` (`protocol_currency_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_currency_balances` (
  `player_id` int NOT NULL,
  `currency_id` bigint unsigned NOT NULL,
  `balance` bigint NOT NULL DEFAULT 0,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`player_id`, `currency_id`),
  KEY `idx_player_currency_balances_currency` (`currency_id`),
  CONSTRAINT `fk_player_currency_balances_currency`
    FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_currency_movements` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL,
  `currency_id` bigint unsigned NOT NULL,
  `operation` enum('ADD','REMOVE','SET') NOT NULL,
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
  KEY `idx_currency_movements_source` (`source_type`, `source_ref`),
  CONSTRAINT `fk_currency_movements_currency`
    FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

INSERT INTO `currencies` (
  `code`,
  `display_name`,
  `protocol_currency_id`,
  `is_credits`,
  `visible_in_purse`,
  `enabled`,
  `sort_order`,
  `icon_key`,
  `noun_singular`,
  `noun_plural`,
  `description`,
  `role_policy`
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
  `role_policy` = VALUES(`role_policy`);

INSERT INTO `player_currency_balances` (`player_id`, `currency_id`, `balance`)
SELECT p.`id`, c.`id`, COALESCE(p.`credits`, 0)
FROM `players` p
JOIN `currencies` c ON c.`code` = 'credits'
UNION ALL
SELECT p.`id`, c.`id`, COALESCE(p.`activity_points`, 0)
FROM `players` p
JOIN `currencies` c ON c.`code` = 'currency_0'
UNION ALL
SELECT p.`id`, c.`id`, COALESCE(p.`vip_points`, 0)
FROM `players` p
JOIN `currencies` c ON c.`code` = 'currency_5'
UNION ALL
SELECT p.`id`, c.`id`, COALESCE(p.`seasonal_points`, 0)
FROM `players` p
JOIN `currencies` c ON c.`code` = 'currency_103'
UNION ALL
SELECT p.`id`, c.`id`, COALESCE(p.`black_money`, 0)
FROM `players` p
JOIN `currencies` c ON c.`code` = 'currency_105'
ON DUPLICATE KEY UPDATE
  `balance` = VALUES(`balance`);

ALTER TABLE `players` DROP COLUMN `activity_points`;
ALTER TABLE `players` DROP COLUMN `vip_points`;
ALTER TABLE `players` DROP COLUMN `seasonal_points`;
ALTER TABLE `players` DROP COLUMN `black_money`;
