-- Adds the runtime tables that are still part of Comet's current schema but were
-- absent from the original baseline snapshot.

CREATE TABLE IF NOT EXISTS `furniture` (
  `id` int(10) unsigned NOT NULL,
  `public_name` varchar(56) NOT NULL DEFAULT '',
  `item_name` varchar(70) NOT NULL,
  `type` varchar(3) NOT NULL DEFAULT 's',
  `width` int(11) NOT NULL DEFAULT 1,
  `length` int(11) NOT NULL DEFAULT 1,
  `stack_height` double(4,2) NOT NULL DEFAULT 0.00,
  `sprite_id` int(11) NOT NULL DEFAULT 0,
  `can_stack` tinyint(1) NOT NULL DEFAULT 1,
  `can_sit` tinyint(1) NOT NULL DEFAULT 0,
  `is_walkable` tinyint(1) NOT NULL DEFAULT 0,
  `allow_trade` tinyint(1) NOT NULL DEFAULT 1,
  `allow_inventory_stack` tinyint(1) NOT NULL DEFAULT 1,
  `flat_id` int(11) NOT NULL DEFAULT -1,
  `allow_gift` tinyint(1) NOT NULL DEFAULT 1,
  `effect_id` int(11) NOT NULL DEFAULT 0,
  `interaction_type` varchar(500) NOT NULL DEFAULT 'default',
  `interaction_modes_count` int(11) NOT NULL DEFAULT 1,
  `vending_ids` varchar(255) NOT NULL DEFAULT '0',
  `requires_rights` tinyint(1) NOT NULL DEFAULT 0,
  `song_id` int(11) NOT NULL DEFAULT 0,
  `variable_heights` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_furniture_item_name` (`item_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `catalog_pages` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) NOT NULL DEFAULT -1,
  `caption` varchar(128) NOT NULL DEFAULT '',
  `page_layout` varchar(64) NOT NULL DEFAULT 'default_3x3',
  `icon_image` int(11) NOT NULL DEFAULT 1,
  `min_rank` int(11) NOT NULL DEFAULT 1,
  `order_num` int(11) NOT NULL DEFAULT 1,
  `visible` enum('0','1') NOT NULL DEFAULT '1',
  `enabled` enum('0','1') NOT NULL DEFAULT '1',
  `link` varchar(128) NOT NULL DEFAULT '',
  `type` varchar(32) NOT NULL DEFAULT 'NORMAL',
  `extra_data` varchar(255) NOT NULL DEFAULT '',
  `page_headline` varchar(1024) NOT NULL DEFAULT '',
  `page_teaser` varchar(255) NOT NULL DEFAULT '',
  `page_special` text DEFAULT NULL,
  `page_text_1` text DEFAULT NULL,
  `page_text_2` text DEFAULT NULL,
  `page_text_details` text DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_catalog_pages_parent_id` (`parent_id`),
  KEY `idx_catalog_pages_link` (`link`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `furniture_music` (
  `id` int(11) NOT NULL,
  `name` varchar(64) NOT NULL,
  `title` varchar(100) NOT NULL,
  `artist` varchar(50) NOT NULL,
  `song_data` text NOT NULL,
  `length` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `cms_offers` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL DEFAULT '',
  `diamonds` int(11) NOT NULL DEFAULT 0,
  `pixels` int(11) NOT NULL DEFAULT 0,
  `time_vip_days` int(11) NOT NULL DEFAULT 0,
  `item_id` int(11) NOT NULL DEFAULT 0,
  `amount_item` int(11) NOT NULL DEFAULT 1,
  `enabled` enum('0','1') NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `cms_offers_claim` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `offer_id` int(11) NOT NULL,
  `claimed` enum('0','1') NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_cms_offers_claim_player` (`player_id`, `claimed`),
  KEY `idx_cms_offers_claim_offer` (`offer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;