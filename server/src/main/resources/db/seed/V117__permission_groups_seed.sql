INSERT IGNORE INTO `permission_groups`
  (`code`, `display_name`, `priority`, `legacy_rank_id`, `staff`, `default_group`, `enabled`)
VALUES
  ('player', 'Player', 0, 1, '0', '1', '1'),
  ('vip', 'VIP', 1000, 2, '0', '0', '1'),
  ('rank_3', 'Legacy Rank 3', 3000, 3, '0', '0', '1'),
  ('rank_4', 'Legacy Rank 4', 4000, 4, '0', '0', '1'),
  ('rank_5', 'Legacy Rank 5', 5000, 5, '0', '0', '1'),
  ('rank_6', 'Legacy Rank 6', 6000, 6, '1', '0', '1'),
  ('rank_7', 'Legacy Rank 7', 7000, 7, '1', '0', '1'),
  ('rank_8', 'Legacy Rank 8', 8000, 8, '1', '0', '1'),
  ('rank_9', 'Legacy Rank 9', 9000, 9, '1', '0', '1'),
  ('rank_10', 'Legacy Rank 10', 10000, 10, '1', '0', '1'),
  ('rank_11', 'Legacy Rank 11', 11000, 11, '1', '0', '1'),
  ('rank_12', 'Legacy Rank 12', 12000, 12, '1', '0', '1'),
  ('rank_13', 'Legacy Rank 13', 13000, 13, '1', '0', '1'),
  ('rank_14', 'Legacy Rank 14', 14000, 14, '1', '0', '1'),
  ('rank_15', 'Legacy Rank 15', 15000, 15, '1', '0', '1'),
  ('rank_16', 'Legacy Rank 16', 16000, 16, '1', '0', '1'),
  ('rank_17', 'Legacy Rank 17', 17000, 17, '1', '0', '1'),
  ('rank_18', 'Legacy Rank 18', 18000, 18, '1', '0', '1'),
  ('rank_19', 'Legacy Rank 19', 19000, 19, '1', '0', '1'),
  ('rank_20', 'Legacy Rank 20', 20000, 20, '1', '0', '1'),
  ('rank_21', 'Legacy Rank 21', 21000, 21, '1', '0', '1'),
  ('rank_22', 'Legacy Rank 22', 22000, 22, '1', '0', '1'),
  ('rank_23', 'Legacy Rank 23', 23000, 23, '1', '0', '1'),
  ('mod', 'Moderator', 50000, 6, '1', '0', '1'),
  ('admin', 'Administrator', 100000, 7, '1', '0', '1'),
  ('allowance_mention', 'Mention Allowance', 100, 1, '0', '0', '1');

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'commands.commands', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'player';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'commands.about', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'player';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'commands.buyroom', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'player';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'perks.trade', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'player';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'commands.prefix', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'vip';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'commands.push', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'vip';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'commands.pull', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'vip';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'modtool.open', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'mod';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'modtool.ban', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'mod';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'modtool.mute', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'mod';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'modtool.kick', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'mod';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'commands.ban', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'mod';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'commands.kick', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'mod';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'room.filter_bypass', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'mod';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'messenger.mod_chat', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'mod';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, '*', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'admin';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `id`, 'allowance.mention', 'ALLOW', 'global' FROM `permission_groups` WHERE `code` = 'allowance_mention';

INSERT IGNORE INTO `permission_group_nodes` (`group_id`, `node`, `effect`, `context`)
SELECT `permission_groups`.`id`, `permission_nodes`.`node`, 'ALLOW', 'global'
FROM `permission_groups`
JOIN (
  SELECT 'chat_bubbles.0' AS `node`, 1 AS `minimum_rank` UNION ALL
  SELECT 'chat_bubbles.1', 1 UNION ALL
  SELECT 'chat_bubbles.2', 2 UNION ALL
  SELECT 'chat_bubbles.3', 2 UNION ALL
  SELECT 'chat_bubbles.4', 1 UNION ALL
  SELECT 'chat_bubbles.5', 1 UNION ALL
  SELECT 'chat_bubbles.6', 1 UNION ALL
  SELECT 'chat_bubbles.7', 4 UNION ALL
  SELECT 'chat_bubbles.8', 4 UNION ALL
  SELECT 'chat_bubbles.9', 1 UNION ALL
  SELECT 'chat_bubbles.10', 1 UNION ALL
  SELECT 'chat_bubbles.11', 1 UNION ALL
  SELECT 'chat_bubbles.12', 3 UNION ALL
  SELECT 'chat_bubbles.13', 1 UNION ALL
  SELECT 'chat_bubbles.14', 1 UNION ALL
  SELECT 'chat_bubbles.15', 1 UNION ALL
  SELECT 'chat_bubbles.16', 3 UNION ALL
  SELECT 'chat_bubbles.17', 6 UNION ALL
  SELECT 'chat_bubbles.19', 3 UNION ALL
  SELECT 'chat_bubbles.20', 3 UNION ALL
  SELECT 'chat_bubbles.21', 3 UNION ALL
  SELECT 'chat_bubbles.22', 3 UNION ALL
  SELECT 'chat_bubbles.23', 5 UNION ALL
  SELECT 'chat_bubbles.24', 2 UNION ALL
  SELECT 'chat_bubbles.25', 6 UNION ALL
  SELECT 'chat_bubbles.26', 4 UNION ALL
  SELECT 'chat_bubbles.27', 4 UNION ALL
  SELECT 'chat_bubbles.28', 4 UNION ALL
  SELECT 'chat_bubbles.29', 2 UNION ALL
  SELECT 'chat_bubbles.30', 2 UNION ALL
  SELECT 'chat_bubbles.31', 3 UNION ALL
  SELECT 'chat_bubbles.32', 3 UNION ALL
  SELECT 'chat_bubbles.33', 3 UNION ALL
  SELECT 'chat_bubbles.34', 3 UNION ALL
  SELECT 'chat_bubbles.35', 3 UNION ALL
  SELECT 'chat_bubbles.36', 4 UNION ALL
  SELECT 'chat_bubbles.37', 4 UNION ALL
  SELECT 'chat_bubbles.38', 4 UNION ALL
  SELECT 'effects.0', 1 UNION ALL
  SELECT 'effects.102', 13 UNION ALL
  SELECT 'effects.178', 10 UNION ALL
  SELECT 'effects.187', 7 UNION ALL
  SELECT 'effects.600', 23 UNION ALL
  SELECT 'effects.766', 17 UNION ALL
  SELECT 'effects.767', 11 UNION ALL
  SELECT 'effects.768', 6 UNION ALL
  SELECT 'effects.769', 12 UNION ALL
  SELECT 'effects.770', 14 UNION ALL
  SELECT 'effects.772', 15 UNION ALL
  SELECT 'effects.773', 16 UNION ALL
  SELECT 'effects.774', 19 UNION ALL
  SELECT 'effects.775', 18 UNION ALL
  SELECT 'effects.776', 20 UNION ALL
  SELECT 'effects.779', 21 UNION ALL
  SELECT 'effects.780', 23 UNION ALL
  SELECT 'effects.781', 8 UNION ALL
  SELECT 'effects.782', 5 UNION ALL
  SELECT 'effects.783', 9 UNION ALL
  SELECT 'effects.786', 2
) AS `permission_nodes`
WHERE `permission_groups`.`legacy_rank_id` >= `permission_nodes`.`minimum_rank`;

INSERT IGNORE INTO `player_permission_groups` (`player_id`, `group_id`, `granted_by`, `reason`)
SELECT `id`, (SELECT `id` FROM `permission_groups` WHERE `code` = 'allowance_mention'), 'seed', 'Migrated legacy mention allowance'
FROM `players`
WHERE `id` IN (1, 2, 3);

INSERT IGNORE INTO `player_permission_groups` (`player_id`, `group_id`, `granted_by`, `reason`)
SELECT `id`, (SELECT `id` FROM `permission_groups` WHERE `code` = 'player'), 'seed', 'Default permission group'
FROM `players`;

INSERT IGNORE INTO `player_permission_groups` (`player_id`, `group_id`, `granted_by`, `reason`)
SELECT `id`, (SELECT `id` FROM `permission_groups` WHERE `code` = 'vip'), 'seed', 'Migrated from players.rank'
FROM `players`
WHERE `rank` >= 2;

INSERT IGNORE INTO `player_permission_groups` (`player_id`, `group_id`, `granted_by`, `reason`)
SELECT `id`, (SELECT `id` FROM `permission_groups` WHERE `code` = 'mod'), 'seed', 'Migrated from players.rank'
FROM `players`
WHERE `rank` >= 6;

INSERT IGNORE INTO `player_permission_groups` (`player_id`, `group_id`, `granted_by`, `reason`)
SELECT `id`, (SELECT `id` FROM `permission_groups` WHERE `code` = 'admin'), 'seed', 'Migrated from players.rank'
FROM `players`
WHERE `rank` >= 7;

INSERT IGNORE INTO `player_permission_groups` (`player_id`, `group_id`, `granted_by`, `reason`)
SELECT `players`.`id`, `permission_groups`.`id`, 'seed', 'Migrated from players.rank'
FROM `players`
JOIN `permission_groups`
  ON `permission_groups`.`code` LIKE 'rank\_%'
  AND CAST(SUBSTRING(`permission_groups`.`code`, 6) AS UNSIGNED) <= `players`.`rank`;

INSERT IGNORE INTO `player_permission_groups` (`player_id`, `group_id`, `granted_by`, `reason`)
SELECT `id`, (SELECT `id` FROM `permission_groups` WHERE `code` = 'admin'), 'seed', 'Test admin membership'
FROM `players`
WHERE `id` = 1;

INSERT IGNORE INTO `player_permission_groups` (`player_id`, `group_id`, `granted_by`, `reason`)
SELECT `id`, (SELECT `id` FROM `permission_groups` WHERE `code` = 'mod'), 'seed', 'Test moderator membership'
FROM `players`
WHERE `id` = 2;
