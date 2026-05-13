-- Normalizes catalog prices into currency-id rows.
-- Legacy non-credit catalog columns are used only for this development backfill and then removed.

CREATE TABLE IF NOT EXISTS `catalog_item_prices` (
  `catalog_item_id` int unsigned NOT NULL,
  `currency_id` bigint unsigned NOT NULL,
  `amount` bigint NOT NULL DEFAULT 0,
  `display_order` int NOT NULL DEFAULT 0,
  `client_visible` enum('1','0') NOT NULL DEFAULT '1',
  `required_rank` int NULL,
  PRIMARY KEY (`catalog_item_id`, `currency_id`),
  KEY `idx_catalog_item_prices_currency` (`currency_id`),
  CONSTRAINT `fk_catalog_item_prices_currency`
    FOREIGN KEY (`currency_id`) REFERENCES `currencies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

INSERT INTO `catalog_item_prices` (`catalog_item_id`, `currency_id`, `amount`, `display_order`, `client_visible`)
SELECT ci.`id`, c.`id`, ci.`cost_credits`, 0, '1'
FROM `catalog_items` ci
JOIN `currencies` c ON c.`code` = 'credits'
WHERE ci.`cost_credits` > 0
UNION ALL
SELECT ci.`id`, c.`id`, ci.`cost_pixels`, 10, '1'
FROM `catalog_items` ci
JOIN `currencies` c ON c.`code` = 'currency_0'
WHERE ci.`cost_pixels` > 0
UNION ALL
SELECT ci.`id`, c.`id`, ci.`cost_diamonds`, 20, '1'
FROM `catalog_items` ci
JOIN `currencies` c ON c.`code` = 'currency_5'
WHERE ci.`cost_diamonds` > 0
UNION ALL
SELECT ci.`id`, c.`id`, ci.`cost_seasonal`, 30, '1'
FROM `catalog_items` ci
JOIN `currencies` c ON c.`code` = 'currency_103'
WHERE ci.`cost_seasonal` > 0
UNION ALL
SELECT ci.`id`, c.`id`, ci.`cost_tokens`, 40, '1'
FROM `catalog_items` ci
JOIN `currencies` c ON c.`code` = 'currency_105'
WHERE ci.`cost_tokens` > 0
ON DUPLICATE KEY UPDATE
  `amount` = VALUES(`amount`),
  `display_order` = VALUES(`display_order`),
  `client_visible` = VALUES(`client_visible`);

ALTER TABLE `catalog_items` DROP COLUMN `cost_pixels`;
ALTER TABLE `catalog_items` DROP COLUMN `cost_diamonds`;
ALTER TABLE `catalog_items` DROP COLUMN `cost_seasonal`;
ALTER TABLE `catalog_items` DROP COLUMN `cost_tokens`;
