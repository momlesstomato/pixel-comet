-- Renames feature-owned currency columns to protocol-neutral names for development installs.

ALTER TABLE `player_rewards`
  CHANGE COLUMN `vip_points` `primary_currency_amount` int(11) NOT NULL DEFAULT 0;

ALTER TABLE `player_rewards`
  CHANGE COLUMN `seasonal_points` `secondary_currency_amount` int(11) NOT NULL DEFAULT 0;

ALTER TABLE `room_bundles`
  CHANGE COLUMN `cost_seasonal` `protocol_103_cost` int(11) DEFAULT 0;

ALTER TABLE `room_bundles`
  CHANGE COLUMN `cost_vip` `protocol_5_cost` int(11) DEFAULT 0;

ALTER TABLE `room_bundles`
  CHANGE COLUMN `cost_activity_points` `protocol_0_cost` int(11) DEFAULT 0;
