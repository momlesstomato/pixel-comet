-- Repairs catalog entries that were visible and purchasable without a persisted currency price.

UPDATE `catalog_items`
SET `cost_credits` = 4
WHERE `id` = 9774
  AND `cost_credits` = 0;

INSERT INTO `catalog_item_prices` (`catalog_item_id`, `currency_id`, `amount`, `display_order`, `client_visible`)
SELECT 9774, `currencies`.`id`, 4, 0, '1'
FROM `currencies`
WHERE `currencies`.`code` = 'credits'
ON DUPLICATE KEY UPDATE
  `amount` = VALUES(`amount`),
  `display_order` = VALUES(`display_order`),
  `client_visible` = VALUES(`client_visible`);
