-- Adds the runtime use-case assignments now consumed by gameplay services.

INSERT INTO `currency_use_cases` (`use_case`, `currency_id`)
SELECT 'catalog.promotion.primary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'catalog.promotion.secondary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'camera.share', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'fireworks.purchase', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'casino.payout.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'casino.payout.secondary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'bank.transfer.primary', `id` FROM `currencies` WHERE `code` = 'currency_5'
UNION ALL
SELECT 'bank.transfer.secondary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'room.purchase', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'quest.reward.primary', `id` FROM `currencies` WHERE `code` = 'currency_0'
UNION ALL
SELECT 'quest.reward.secondary', `id` FROM `currencies` WHERE `code` = 'currency_103'
UNION ALL
SELECT 'quest.reward.special', `id` FROM `currencies` WHERE `code` = 'currency_105'
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
