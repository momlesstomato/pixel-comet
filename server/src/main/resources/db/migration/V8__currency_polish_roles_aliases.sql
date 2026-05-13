-- Adds rank-scoped currency rules, boundary aliases, and feature use-case assignments.

CREATE TABLE IF NOT EXISTS `currency_role_rules` (
  `currency_id` bigint unsigned NOT NULL,
  `rank_id` int NOT NULL,
  `can_view` enum('1','0') NOT NULL DEFAULT '1',
  `can_earn` enum('1','0') NOT NULL DEFAULT '1',
  `can_spend` enum('1','0') NOT NULL DEFAULT '1',
  `can_manage` enum('1','0') NOT NULL DEFAULT '0',
  PRIMARY KEY (`currency_id`, `rank_id`),
  CONSTRAINT `fk_currency_role_rules_currency`
    FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `currency_aliases` (
  `alias` varchar(64) NOT NULL,
  `currency_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`alias`),
  KEY `idx_currency_aliases_currency` (`currency_id`),
  CONSTRAINT `fk_currency_aliases_currency`
    FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `currency_use_cases` (
  `use_case` varchar(128) NOT NULL,
  `currency_id` bigint unsigned NOT NULL,
  `required_rank` int NULL,
  `enabled` enum('1','0') NOT NULL DEFAULT '1',
  `metadata_json` text NULL,
  PRIMARY KEY (`use_case`),
  KEY `idx_currency_use_cases_currency` (`currency_id`),
  CONSTRAINT `fk_currency_use_cases_currency`
    FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

INSERT INTO `currency_aliases` (`alias`, `currency_id`)
SELECT 'credits', `id` FROM `currencies` WHERE `code` = 'credits'
UNION ALL
SELECT 'protocol_0', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'protocol_5', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'protocol_103', `id` FROM `currencies` WHERE `code` = 'currency_103'
UNION ALL
SELECT 'protocol_105', `id` FROM `currencies` WHERE `code` = 'currency_105'
ON DUPLICATE KEY UPDATE
  `currency_id` = VALUES(`currency_id`);

INSERT INTO `currency_use_cases` (`use_case`, `currency_id`)
SELECT 'online_reward.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'online_reward.club', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'catalog.promotion.primary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'catalog.promotion.secondary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'camera.share', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'fireworks.purchase', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'casino.bet', `id` FROM `currencies` WHERE `code` = 'currency_105'
UNION ALL
SELECT 'casino.payout', `id` FROM `currencies` WHERE `code` = 'currency_105'
UNION ALL
SELECT 'casino.payout.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'casino.payout.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'bank.transfer.primary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'bank.transfer.secondary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'bank.deposit', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'bank.withdraw', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'room.purchase', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'quest.reward.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'quest.reward.secondary', `id` FROM `currencies` WHERE `code` = 'currency_103'
UNION ALL
SELECT 'quest.reward.special', `id` FROM `currencies` WHERE `code` = 'currency_105'
UNION ALL
SELECT 'quest.reward.default', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'calendar.reward.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'calendar.reward.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'calendar.reward.special', `id` FROM `currencies` WHERE `code` = 'currency_103'
UNION ALL
SELECT 'crackable.reward.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'crackable.reward.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'crackable.reward.default', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'reward_command.primary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'reward_command.secondary', `id` FROM `currencies` WHERE `code` = 'currency_103'
UNION ALL
SELECT 'poll.reward.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'poll.reward.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'subscription.reward.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'subscription.reward.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'exchange.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'exchange.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'battle_pass.reward', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'staff_bundle.primary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'staff_event.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'staff_event.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'staff_event.special', `id` FROM `currencies` WHERE `code` = 'currency_103'
UNION ALL
SELECT 'wired.condition.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'wired.condition.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
ON DUPLICATE KEY UPDATE
  `currency_id` = VALUES(`currency_id`);
