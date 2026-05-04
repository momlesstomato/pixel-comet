-- Generated from SQL.sql. Structural DDL only.

CREATE TABLE IF NOT EXISTS `achievements` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `group_name` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL DEFAULT 'ACH_',
  `category` enum('identity','explore','music','social','games','room_builder','tools','commercial','survival_mode','pets') CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL DEFAULT 'identity',
  `level` int(11) NOT NULL DEFAULT 1,
  `reward_activity_points` int(11) NOT NULL DEFAULT 100,
  `reward_achievement_points` int(11) DEFAULT 10,
  `reward_type` int(11) DEFAULT 0,
  `progress_requirement` int(11) NOT NULL DEFAULT 1,
  `enabled` enum('1','0') CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL DEFAULT '1',
  `game_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=1119 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `achievements_talents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `level` int(11) NOT NULL DEFAULT 0,
  `achievement_ids` varchar(100) NOT NULL DEFAULT '',
  `reward_items` varchar(100) NOT NULL DEFAULT '',
  `reward_perks` varchar(100) NOT NULL DEFAULT '',
  `reward_badges` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `admin_pass` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `admin_password` int(6) NOT NULL,
  `assigned_to` varchar(250) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `badge_definitions` (
  `code` varchar(35) NOT NULL,
  `required_right` varchar(25) NOT NULL DEFAULT '',
  PRIMARY KEY (`code`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `badge_month` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `code` varchar(30) NOT NULL,
  `units` int(50) NOT NULL,
  `position` int(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `badge_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `badge_id` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `cost` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `badge_veri` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `username` varchar(250) NOT NULL,
  `badge_name` varchar(40) NOT NULL,
  `badge_desc` varchar(40) NOT NULL,
  `image` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `venta` enum('0','1') NOT NULL DEFAULT '0',
  `user_id` varchar(50) NOT NULL,
  `precio` int(99) NOT NULL DEFAULT 0,
  `upbadge` enum('0','1') NOT NULL DEFAULT '0',
  `group_name` text NOT NULL,
  `group_id` varchar(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `badge_verificado` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `image` varchar(255) CHARACTER SET latin1 COLLATE latin1_general_ci NOT NULL,
  `venta` enum('0','1') NOT NULL DEFAULT '0',
  `user_id` varchar(50) NOT NULL,
  `precio` int(99) NOT NULL DEFAULT 0,
  `ventas` int(99) NOT NULL DEFAULT 0,
  `ventasT` int(99) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `bans` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` enum('ip','machine','trade','mute','user') DEFAULT NULL,
  `expire` int(20) DEFAULT 0 COMMENT '0 = perminent',
  `data` varchar(100) DEFAULT '',
  `reason` varchar(100) DEFAULT '',
  `added_by` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `battlepass_active` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `player_id` int(250) NOT NULL,
  `level` int(3) NOT NULL,
  `exp` int(100) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `battlepass_completed` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `player_id` int(250) NOT NULL,
  `mission` int(3) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=102 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `bots` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) DEFAULT 0,
  `owner` varchar(100) DEFAULT '',
  `room_id` int(11) DEFAULT 0,
  `name` varchar(100) DEFAULT 'Placeholder',
  `figure` varchar(255) DEFAULT '',
  `gender` enum('m','f') DEFAULT 'm',
  `motto` varchar(100) DEFAULT NULL,
  `x` int(11) DEFAULT 0,
  `y` int(11) DEFAULT 0,
  `z` double DEFAULT 0,
  `messages` text DEFAULT NULL,
  `automatic_chat` enum('1','0') DEFAULT '1',
  `chat_delay` int(11) DEFAULT 7,
  `mode` enum('default','relaxed') DEFAULT 'default',
  `type` enum('generic','waiter','spy','test') DEFAULT 'generic',
  `data` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `catalog_clothing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(255) NOT NULL,
  `clothing_items` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `item_name` (`item_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `catalog_featured_pages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `caption` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL,
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL,
  `page_link` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_spanish_ci NOT NULL,
  `page_id` int(11) NOT NULL DEFAULT -1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `catalog_gift_nuxuser` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('seasonal','badge','item','sell','purchase','scissors','paper','rock','diamonds') NOT NULL DEFAULT 'item',
  `page_type` int(11) NOT NULL,
  `reward_icon` varchar(255) NOT NULL,
  `reward_name` varchar(255) NOT NULL,
  `reward_productdata` varchar(255) NOT NULL,
  `reward_data` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `catalog_gift_wrapping` (
  `type` enum('new','old') DEFAULT 'new',
  `sprite_id` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `catalog_items` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `page_id` int(11) NOT NULL,
  `item_ids` varchar(255) NOT NULL,
  `catalog_name` varchar(100) NOT NULL,
  `cost_credits` int(11) NOT NULL DEFAULT 1,
  `cost_pixels` int(11) NOT NULL DEFAULT 0,
  `cost_diamonds` int(11) NOT NULL DEFAULT 0,
  `cost_seasonal` int(11) NOT NULL DEFAULT 0,
  `amount` int(11) NOT NULL DEFAULT 1,
  `limited_sells` int(11) NOT NULL DEFAULT 0,
  `limited_stack` int(11) NOT NULL DEFAULT 0,
  `offer_active` enum('0','1') NOT NULL DEFAULT '0',
  `extradata` varchar(255) NOT NULL DEFAULT '',
  `badge_id` varchar(10) DEFAULT '',
  `vip` enum('0','1','2') NOT NULL DEFAULT '0',
  `achievement` int(4) unsigned NOT NULL DEFAULT 0,
  `song_id` int(11) unsigned NOT NULL DEFAULT 0,
  `flat_id` int(11) NOT NULL DEFAULT -1,
  `cost_tokens` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `item_ids` (`item_ids`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3000000953 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `catalog_products` (
  `id` int(3) unsigned NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL DEFAULT 0,
  `product_name` varchar(255) NOT NULL DEFAULT 'default',
  `cost` int(11) NOT NULL DEFAULT 5,
  `currency` int(11) NOT NULL DEFAULT 5,
  `type` varchar(255) NOT NULL DEFAULT 'normal',
  `image` varchar(255) NOT NULL DEFAULT 'throne',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `command_notifications` (
  `name` varchar(200) NOT NULL COMMENT 'The command for the player to type in (it does not override normal system commands)',
  PRIMARY KEY (`name`) USING BTREE,
  KEY `name` (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `contact` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gebruikersnaam` int(10) NOT NULL DEFAULT 0,
  `email` varchar(999) NOT NULL,
  `onderwerp` varchar(999) NOT NULL DEFAULT '',
  `bericht` longtext NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `dados_config` (
  `id` int(11) DEFAULT NULL,
  `is_open` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `dados_formulario` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `user_id` int(250) NOT NULL,
  `experiencia` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `disponibilidad` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `datos` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `type` enum('home_comment','forum_like','forum_like_tema','forum_comment','happyb','badge_veri','badge_deny','banco_dk','banco_di','recargas','buybadge','photo') NOT NULL DEFAULT 'home_comment',
  `mensaje` text NOT NULL,
  `estado` int(1) NOT NULL DEFAULT 0,
  `user_id` int(150) NOT NULL DEFAULT 1,
  `autor` varchar(150) NOT NULL DEFAULT '0',
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  `url` varchar(150) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `emojis` (
  `id` int(250) NOT NULL,
  `key` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `fastfood_user_data` (
  `player_id` int(11) NOT NULL,
  `parachutes` int(11) NOT NULL DEFAULT 10,
  `missiles` int(11) NOT NULL DEFAULT 10,
  `shields` int(11) NOT NULL DEFAULT 10,
  `games_played` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `forum_categories` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `num` int(2) NOT NULL,
  `title` text NOT NULL,
  `class` text NOT NULL,
  `about` text NOT NULL,
  `min_view_rank` int(2) NOT NULL DEFAULT 1,
  `min_post_rank` int(2) NOT NULL DEFAULT 1,
  `min_edit_rank` int(2) NOT NULL DEFAULT 1,
  `link` varchar(99) NOT NULL,
  `min_view_guia` int(2) NOT NULL DEFAULT 0,
  `min_post_guia` int(2) NOT NULL DEFAULT 0,
  `min_edit_guia` int(2) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `forum_comments` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `comment` text NOT NULL,
  `newsid` int(10) NOT NULL,
  `userid` int(10) NOT NULL,
  `date` varchar(200) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `forum_likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('Comment','Thread') COLLATE latin1_general_ci NOT NULL DEFAULT 'Comment',
  `type_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `forum_threads` (
  `id` int(11) NOT NULL,
  `title` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `longstory` text COLLATE latin1_general_ci NOT NULL,
  `published` timestamp NOT NULL DEFAULT current_timestamp(),
  `author` int(11) NOT NULL,
  `category` int(11) NOT NULL,
  `starred` int(1) NOT NULL DEFAULT 0,
  `locked` int(1) NOT NULL DEFAULT 0,
  `shortstory` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `image` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  `lastcom` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `perma` varchar(255) COLLATE latin1_general_ci NOT NULL,
  `sub` enum('0','1','2') COLLATE latin1_general_ci NOT NULL DEFAULT '0',
  `room` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `notificacion` enum('0','1') COLLATE latin1_general_ci NOT NULL DEFAULT '1',
  `badge` varchar(255) COLLATE latin1_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `furniture_crafting_items` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `itemName` varchar(255) NOT NULL,
  `itemId` int(10) NOT NULL,
  `machineBaseId` int(10) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `furniture_crafting_recipes` (
  `id` varchar(255) NOT NULL,
  `items` varchar(255) NOT NULL,
  `result` varchar(255) NOT NULL,
  `result_limit` int(10) NOT NULL,
  `result_crafted` int(10) NOT NULL,
  `machineBaseId` int(32) NOT NULL DEFAULT 1,
  `badge` varchar(255) NOT NULL,
  `achievement` varchar(255) NOT NULL,
  `mode` enum('public','secret') NOT NULL DEFAULT 'public'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `gamecenter_list` (
  `id` int(11) NOT NULL DEFAULT 0,
  `name` varchar(25) NOT NULL DEFAULT '',
  `roomId` int(11) NOT NULL,
  `path` varchar(125) NOT NULL,
  `visible` enum('0','1') DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `groups` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `badge` varchar(50) NOT NULL,
  `owner_id` int(11) unsigned NOT NULL,
  `room_id` int(10) unsigned NOT NULL DEFAULT 0,
  `created` int(50) NOT NULL,
  `type` enum('regular','exclusive','private') NOT NULL DEFAULT 'regular',
  `colour1` int(11) NOT NULL DEFAULT 242424,
  `colour2` int(11) NOT NULL DEFAULT 242424,
  `members_deco` enum('0','1') DEFAULT '0',
  `has_forum` enum('0','1') DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id` (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `group_forum_messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('THREAD','REPLY') DEFAULT 'THREAD',
  `thread_id` int(11) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  `title` varchar(120) DEFAULT '',
  `message` varchar(4000) NOT NULL DEFAULT '',
  `author_id` int(11) NOT NULL,
  `author_timestamp` int(11) NOT NULL,
  `state` int(11) NOT NULL DEFAULT 1,
  `locked` enum('1','0') DEFAULT '0',
  `pinned` enum('1','0') DEFAULT '0',
  `moderator_id` int(11) NOT NULL DEFAULT 0,
  `moderator_username` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `group_id` (`group_id`) USING BTREE,
  KEY `thread_id` (`thread_id`) USING BTREE,
  KEY `author_id` (`author_id`) USING BTREE,
  KEY `type_group_id` (`type`,`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `group_forum_settings` (
  `group_id` int(11) NOT NULL DEFAULT 0,
  `read_permission` enum('EVERYBODY','MEMBERS','ADMINISTRATORS') NOT NULL DEFAULT 'EVERYBODY',
  `post_permission` enum('EVERYBODY','MEMBERS','ADMINISTRATORS','OWNER') NOT NULL DEFAULT 'EVERYBODY',
  `thread_permission` enum('EVERYBODY','MEMBERS','ADMINISTRATORS','OWNER') NOT NULL DEFAULT 'EVERYBODY',
  `moderate_permission` enum('ADMINISTRATORS','OWNER') NOT NULL DEFAULT 'ADMINISTRATORS',
  PRIMARY KEY (`group_id`) USING BTREE,
  KEY `group_id` (`group_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `group_items` (
  `id` int(11) NOT NULL,
  `firstvalue` varchar(300) NOT NULL,
  `secondvalue` varchar(300) NOT NULL,
  `type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `group_memberships` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `group_id` int(11) unsigned NOT NULL,
  `player_id` int(11) unsigned NOT NULL,
  `access_level` enum('owner','admin','member') NOT NULL DEFAULT 'member',
  `date_joined` int(11) unsigned NOT NULL DEFAULT 0,
  `role` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `groupid` (`group_id`) USING BTREE,
  KEY `userid` (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `group_requests` (
  `group_id` int(11) unsigned NOT NULL,
  `player_id` int(11) unsigned NOT NULL,
  KEY `groupid` (`group_id`) USING BTREE,
  KEY `userid` (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hilloversie` (
  `id` varchar(255) NOT NULL,
  `versie` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `home_comments` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `comment` text NOT NULL,
  `newsid` int(10) NOT NULL,
  `userid` int(10) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `hp_logboek` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `items_crackable_rewards` (
  `item_id` int(11) NOT NULL,
  `hit_requirement` int(11) NOT NULL,
  `crackable_type` enum('PRIVATE','PUBLIC') NOT NULL DEFAULT 'PRIVATE',
  `reward_type` enum('BADGE','ITEM','COINS','VIP_POINTS','ACTIVITY_POINTS','EFFECT') NOT NULL DEFAULT 'BADGE',
  `reward_data` text NOT NULL,
  `reward_data_int` int(11) NOT NULL DEFAULT 11,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `items_limited_edition` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_id` int(11) NOT NULL DEFAULT 0,
  `limited_id` int(11) NOT NULL DEFAULT 0,
  `limited_total` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `item_id` (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `items_moodlight` (
  `item_id` int(10) NOT NULL,
  `enabled` enum('1','0') NOT NULL DEFAULT '1',
  `active_preset` enum('1','2','3') NOT NULL DEFAULT '1',
  `preset_1` varchar(500) CHARACTER SET utf8mb4 NOT NULL,
  `preset_2` varchar(500) CHARACTER SET utf8mb4 NOT NULL,
  `preset_3` varchar(500) CHARACTER SET utf8mb4 NOT NULL,
  PRIMARY KEY (`item_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `items_rentable` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `space_id` int(11) NOT NULL,
  `expiracy` int(11) NOT NULL,
  `cost` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `player_id_badge_code` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `items_teles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_one` bigint(20) DEFAULT NULL,
  `id_two` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `items_wired_rewards` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `reward_data` text NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('ROOM_VISIT','FURNI_PURCHASE','ROOM_CHATLOG','MESSENGER_CHATLOG','COMMAND','ALFA_CHATLOG','TRADE','OFFLINE_CHATLOG') DEFAULT 'ROOM_CHATLOG',
  `room_id` int(11) DEFAULT -1,
  `user_id` int(11) DEFAULT -1,
  `data` text CHARACTER SET utf8mb4 DEFAULT NULL,
  `timestamp` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6159 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `logs_namechange` (
  `user_id` int(50) NOT NULL,
  `new_name` varchar(250) COLLATE utf8mb4_spanish_ci NOT NULL,
  `old_name` varchar(250) COLLATE utf8mb4_spanish_ci NOT NULL,
  `timestamp` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `logs_trades` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `player_id` int(250) NOT NULL,
  `by_id` int(250) NOT NULL,
  `items_id` int(250) NOT NULL,
  `item_id` bigint(250) NOT NULL,
  `times` int(250) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `messenger_friendships` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_one_id` int(10) unsigned NOT NULL,
  `user_two_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `1` (`user_one_id`) USING BTREE,
  KEY `2` (`user_two_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `messenger_requests` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `from_id` int(10) unsigned NOT NULL,
  `to_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `from_id` (`from_id`) USING BTREE,
  KEY `to_id` (`to_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `moderation_actions` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `category_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT '',
  `message` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ban_hours` int(11) DEFAULT NULL,
  `avatar_ban_hours` int(11) DEFAULT NULL,
  `mute_hours` int(11) DEFAULT NULL,
  `trade_lock_hours` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `moderation_action_categories` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `moderation_help_tickets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `state` enum('CLOSED','IN_PROGRESS','OPEN','INVALID','ABUSIVE') NOT NULL DEFAULT 'OPEN',
  `submitter_id` int(11) NOT NULL DEFAULT 0,
  `reported_id` int(11) NOT NULL DEFAULT 0,
  `moderator_id` int(11) NOT NULL DEFAULT 0,
  `category_id` int(11) NOT NULL DEFAULT 0,
  `message` varchar(255) NOT NULL DEFAULT '',
  `chat_messages` text DEFAULT NULL,
  `room_id` int(11) NOT NULL DEFAULT 0,
  `timestamp_opened` int(11) NOT NULL DEFAULT 0,
  `timestamp_closed` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `moderation_presets` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` enum('user','room') NOT NULL DEFAULT 'user',
  `message` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `moderation_recommendations` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `guide_id` int(10) NOT NULL,
  `player_id` int(10) NOT NULL,
  `timestamp` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `navigator_categories` (
  `id` int(11) NOT NULL,
  `category` enum('official_view','hotel_view','myworld_view','roomads_view','query') NOT NULL DEFAULT 'hotel_view',
  `category_identifier` varchar(35) NOT NULL DEFAULT '',
  `public_name` varchar(50) NOT NULL DEFAULT '',
  `view_mode` enum('REGULAR','THUMBNAIL') NOT NULL DEFAULT 'REGULAR',
  `required_rank` int(11) NOT NULL DEFAULT 1,
  `category_type` varchar(25) NOT NULL DEFAULT 'category',
  `search_allowance` enum('NOTHING','SHOW_MORE') NOT NULL DEFAULT 'SHOW_MORE',
  `enabled` enum('0','1') NOT NULL DEFAULT '1',
  `visible` enum('0','1') NOT NULL DEFAULT '1',
  `order_id` int(11) NOT NULL DEFAULT 0,
  `room_count` int(11) NOT NULL DEFAULT 15,
  `room_count_expanded` int(11) NOT NULL DEFAULT 50,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `navigator_publics` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `caption` varchar(64) NOT NULL,
  `description` varchar(150) NOT NULL,
  `image_url` text NOT NULL,
  `order_num` int(11) NOT NULL DEFAULT 1,
  `enabled` enum('0','1') NOT NULL DEFAULT '1',
  `category` varchar(255) NOT NULL DEFAULT 'official-root-2',
  PRIMARY KEY (`room_id`) USING BTREE,
  KEY `ordernum` (`order_num`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7212 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `navigator_staff_picks` (
  `room_id` int(11) NOT NULL DEFAULT 0,
  `picker` int(11) DEFAULT NULL,
  PRIMARY KEY (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `news_likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `news_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `like` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `permission_chat_bubbles` (
  `bubble_id` int(11) NOT NULL,
  `minimum_rank` int(11) NOT NULL,
  PRIMARY KEY (`bubble_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `permission_commands` (
  `command_id` varchar(500) DEFAULT 'AboutCommand',
  `minimum_rank` int(11) DEFAULT 1,
  `vip_only` enum('1','0') DEFAULT NULL,
  `rights_only` enum('0','1') DEFAULT NULL,
  `rights_override` enum('NONE','RIGHTS','OWNER') DEFAULT 'NONE'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `permission_command_overrides` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `command_id` varchar(255) DEFAULT NULL,
  `player_id` int(11) DEFAULT NULL,
  `enabled` enum('1','0') DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `permission_effects` (
  `effect_id` int(11) NOT NULL,
  `minimum_rank` int(11) DEFAULT 7,
  PRIMARY KEY (`effect_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `permission_effects_overrides` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `effect_id` int(11) NOT NULL DEFAULT 1,
  `player_id` int(11) DEFAULT NULL,
  `enabled` enum('1','0') NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `permission_perks` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(75) DEFAULT 'FULL_CHAT',
  `data` varchar(255) DEFAULT '',
  `override_rank` enum('1','0') DEFAULT '0',
  `override_default` enum('1','0') DEFAULT '0',
  `min_rank` int(11) DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `permission_ranks` (
  `fuse` varchar(255) DEFAULT NULL,
  `min_rank` int(11) DEFAULT 1,
  `note` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `pet_breeds` (
  `pet_type` int(11) NOT NULL DEFAULT 1,
  `pallet_id` int(11) NOT NULL DEFAULT 0,
  `level` enum('EPIC','RARE','UNCOMMON','COMMON') DEFAULT 'COMMON',
  PRIMARY KEY (`pet_type`,`pallet_id`) USING BTREE,
  KEY `pet_type` (`pet_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `pet_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `owner_id` int(11) NOT NULL DEFAULT 0,
  `room_id` int(11) DEFAULT 0,
  `pet_name` varchar(50) DEFAULT NULL,
  `race_id` int(11) DEFAULT 11,
  `type` int(11) DEFAULT NULL,
  `colour` text DEFAULT NULL,
  `scratches` int(11) NOT NULL DEFAULT 0,
  `level` int(11) NOT NULL DEFAULT 0,
  `happiness` int(11) NOT NULL DEFAULT 0,
  `experience` int(11) NOT NULL DEFAULT 0,
  `energy` int(11) NOT NULL DEFAULT 0,
  `hunger` int(11) NOT NULL DEFAULT 0,
  `x` int(11) DEFAULT 0,
  `y` int(11) DEFAULT 0,
  `z` double NOT NULL DEFAULT 0,
  `saddled` enum('true','false') DEFAULT 'false',
  `any_rider` enum('true','false') DEFAULT 'false',
  `hair_style` int(11) DEFAULT -1,
  `hair_colour` int(11) DEFAULT 0,
  `birthday` int(11) DEFAULT 0,
  `extra_data` varchar(500) CHARACTER SET latin1 NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `pet_messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pet_type` int(11) NOT NULL DEFAULT 0,
  `message_type` enum('GENERIC','SCRATCHED','WELCOME_HOME','HUNGRY','TIRED','SLEEPING') NOT NULL DEFAULT 'GENERIC',
  `message_string` varchar(100) NOT NULL DEFAULT 'Hiya %username%!!!',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `pet_races` (
  `race_id` int(255) DEFAULT NULL,
  `colour1` int(255) DEFAULT NULL,
  `colour2` int(255) DEFAULT NULL,
  `has1colour` enum('1','0') DEFAULT NULL,
  `has2colour` enum('1','0') DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 ROW_FORMAT=FIXED;

CREATE TABLE IF NOT EXISTS `pet_transformable` (
  `name` varchar(50) NOT NULL,
  `data` varchar(50) NOT NULL COMMENT 'The first number is the pet ID, the rest is what determine the colours, hair etc.',
  PRIMARY KEY (`name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `players` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(75) DEFAULT 'Avatar',
  `figure` varchar(255) DEFAULT 'hr-828-31.ch-255-82.sh-3089-64.hd-180-10.lg-3058-64',
  `motto` varchar(100) DEFAULT '',
  `credits` int(11) DEFAULT 5000,
  `vip_points` int(11) DEFAULT 0,
  `activity_points` int(11) NOT NULL DEFAULT 0,
  `rank` int(3) DEFAULT 1,
  `auth_ticket` varchar(100) DEFAULT NULL,
  `gender` enum('M','F') NOT NULL DEFAULT 'M',
  `reg_timestamp` int(11) NOT NULL DEFAULT 0,
  `reg_date` varchar(12) DEFAULT '10/06/2013',
  `staff_function` varchar(255) DEFAULT NULL,
  `last_online` int(11) DEFAULT 0,
  `online` enum('1','0') DEFAULT '0',
  `password` varchar(72) DEFAULT '',
  `email` varchar(72) DEFAULT '',
  `last_ip` varchar(120) DEFAULT NULL,
  `reg_ip` varchar(120) DEFAULT NULL,
  `ip_last` varchar(120) DEFAULT NULL,
  `ip_reg` varchar(120) DEFAULT NULL,
  `vip` enum('1','0') DEFAULT '0',
  `fame_occult` enum('1','0') DEFAULT '0',
  `achievement_points` int(11) DEFAULT 0,
  `favourite_group` int(11) DEFAULT 0,
  `chat_ticket` varchar(50) DEFAULT '',
  `quest_id` int(11) DEFAULT 0,
  `time_muted` int(11) DEFAULT 0,
  `name_colour` varchar(50) DEFAULT '000000',
  `dob` int(11) DEFAULT 0,
  `hk_code` int(11) DEFAULT 0,
  `seckey` varchar(999) DEFAULT NULL,
  `seasonal_points` int(11) DEFAULT 0,
  `user_likes` int(11) DEFAULT 0,
  `vip_timestamp` int(11) DEFAULT 0,
  `pin` varchar(4) DEFAULT NULL,
  `teamrank` int(1) DEFAULT 0,
  `fbid` varchar(255) DEFAULT NULL,
  `fbenable` enum('0','1','2') DEFAULT '1',
  `google_secret_code` varchar(200) DEFAULT NULL,
  `2fa_status` int(1) NOT NULL DEFAULT 0,
  `rank_team` varchar(255) NOT NULL DEFAULT 'lid',
  `referer` varchar(15) DEFAULT NULL,
  `pin_panel` varchar(15) DEFAULT NULL,
  `account_disabled` enum('1','0') DEFAULT '0',
  `staff_access` enum('1','0') DEFAULT '0',
  `staff_occult` enum('1','0') DEFAULT '0',
  `birthday` int(11) DEFAULT NULL,
  `event_points` int(11) DEFAULT NULL,
  `promo_points` int(11) DEFAULT NULL,
  `black_money` int(11) DEFAULT 0,
  `tag` varchar(250) DEFAULT '',
  `job` varchar(250) DEFAULT '',
  `view_points` int(11) DEFAULT 0,
  `banner_id` int(11) DEFAULT 0,
  `color_primary` varchar(250) DEFAULT 'rgba(255, 255 ,255, .81)',
  `color_text` varchar(250) DEFAULT 'rgba(0,0,0)',
  `color_secondary` varchar(250) DEFAULT 'rgba(53, 196, 96)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `player_access` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL DEFAULT 0,
  `hardware_id` varchar(255) NOT NULL DEFAULT '0',
  `ip_address` varchar(50) NOT NULL DEFAULT '0',
  `timestamp` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id_hardware_id_ip_address` (`player_id`,`hardware_id`,`ip_address`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_achievements` (
  `player_id` int(11) NOT NULL DEFAULT 0,
  `group` varchar(50) NOT NULL DEFAULT '',
  `level` tinyint(4) NOT NULL DEFAULT 1,
  `progress` int(11) NOT NULL DEFAULT 0,
  UNIQUE KEY `player_id_group` (`player_id`,`group`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_badges` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `badge_code` varchar(50) DEFAULT '',
  `slot` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `player_id_badge_code` (`player_id`,`badge_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=142 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_calendar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `day` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `player_clothing` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `item_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_cms_event` (
  `user_id` int(250) NOT NULL,
  `get_diamonds` enum('0','1') NOT NULL DEFAULT '1',
  `get_duckets` enum('0','1') NOT NULL DEFAULT '1',
  `fecha` timestamp NOT NULL DEFAULT current_timestamp(),
  `tickets` int(11) NOT NULL DEFAULT 0,
  `tickets_duckets` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_effects` (
  `player_id` int(11) NOT NULL,
  `effect_id` int(11) NOT NULL,
  PRIMARY KEY (`player_id`,`effect_id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_events` (
  `player_id` int(11) NOT NULL,
  `events` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_favourite_rooms` (
  `player_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  PRIMARY KEY (`player_id`,`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_gamecenter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `game_id` int(11) NOT NULL,
  `current_points` int(11) NOT NULL,
  `last_points` int(11) NOT NULL,
  `last_game` varchar(255) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `player_mistery` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `mistery_key` varchar(50) DEFAULT '',
  `mistery_box` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `player_id_badge_code` (`player_id`,`mistery_key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `player_navigator_view_modes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `category` varchar(50) DEFAULT NULL,
  `view_mode` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `player_id_category` (`player_id`,`category`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_notifications` (
  `user_id` int(250) NOT NULL,
  `home_comment` enum('0','1') NOT NULL DEFAULT '1',
  `forum_like` enum('0','1') NOT NULL DEFAULT '1',
  `forum_like_tema` enum('0','1') NOT NULL DEFAULT '1',
  `forum_comment` enum('0','1') NOT NULL DEFAULT '1',
  `happyb` enum('0','1') NOT NULL DEFAULT '1',
  `badge_veri` enum('0','1') NOT NULL DEFAULT '1',
  `banco_dk` enum('0','1') NOT NULL DEFAULT '1',
  `recargas` enum('0','1') NOT NULL DEFAULT '1',
  `buybadge` enum('0','1') NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_photos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  `photo` varchar(255) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
  `fecha` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `photo` (`photo`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE,
  KEY `room_id` (`room_id`) USING BTREE,
  KEY `player_id_room_id` (`player_id`,`room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `player_quest_progression` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT 0,
  `quest_id` int(11) DEFAULT 0,
  `progress` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE,
  KEY `quest_id` (`quest_id`) USING BTREE,
  KEY `playerId_questId` (`player_id`,`quest_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_recent_purchases` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL DEFAULT 0,
  `catalog_item` int(11) NOT NULL DEFAULT 0,
  `amount` int(11) NOT NULL DEFAULT 0,
  `data` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=370 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_relationships` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `level` enum('poop','bobba','smile','heart') DEFAULT 'smile',
  `partner` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_rewards` (
  `code` varchar(255) NOT NULL,
  `badge` varchar(20) NOT NULL,
  `vip_points` int(11) NOT NULL DEFAULT 0,
  `seasonal_points` int(11) NOT NULL DEFAULT 0,
  `active` enum('1','0') NOT NULL DEFAULT '1',
  PRIMARY KEY (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_rewards_redeemed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT 0,
  `reward_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id_reward_code` (`player_id`,`reward_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_room_visits` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL DEFAULT 0,
  `room_id` int(11) NOT NULL DEFAULT 0,
  `time_enter` int(11) NOT NULL DEFAULT 0,
  `time_exit` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE,
  KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=741 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_saved_searches` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT 0,
  `view` varchar(50) DEFAULT 'myworld_view',
  `search_query` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_settings` (
  `player_id` int(10) NOT NULL DEFAULT 0,
  `allow_follow` enum('1','0') NOT NULL DEFAULT '1',
  `volume` text DEFAULT NULL,
  `profile_picture` text DEFAULT NULL,
  `profile_cover` text DEFAULT NULL,
  `hide_online` enum('1','0') DEFAULT '0',
  `hide_inroom` enum('1','0') DEFAULT '0',
  `allow_friend_requests` enum('1','0') DEFAULT '1',
  `allow_trade` enum('1','0') DEFAULT '1',
  `home_room` int(11) DEFAULT 0,
  `wardrobe` text DEFAULT NULL,
  `playlist` text DEFAULT NULL,
  `chat_oldstyle` enum('1','0') DEFAULT '0',
  `follow_friend_mode` enum('EVERYBODY','FRIENDS','NOBODY') DEFAULT 'EVERYBODY',
  `ignore_invites` enum('1','0') DEFAULT '0',
  `allow_mimic` enum('1','0') DEFAULT '1',
  `navigator_x` int(11) DEFAULT 68,
  `navigator_y` int(11) DEFAULT 42,
  `navigator_width` int(11) DEFAULT 425,
  `navigator_height` int(11) DEFAULT 592,
  `navigator_show_searches` enum('1','0') DEFAULT '0',
  `ignore_events` enum('1','0') DEFAULT '0',
  `disable_whisper` enum('0','1') DEFAULT '0',
  `send_login_notif` enum('0','1') DEFAULT '0',
  `mention_type` enum('ALL','NONE','FRIENDS') DEFAULT 'ALL',
  `personalstaff` enum('0','1') DEFAULT '1',
  `event_type` varchar(255) DEFAULT '1',
  `camera_follow` enum('0','1') DEFAULT '1',
  `karma` int(11) DEFAULT 0,
  `prestige` int(11) DEFAULT 0,
  `personal_pin` varchar(255) DEFAULT '2503',
  `nux` int(11) DEFAULT 6,
  `claimed_goal` enum('1','0') DEFAULT '0',
  `royale_xp` int(11) DEFAULT 0,
  `bubble_id` int(11) DEFAULT 0,
  `room_tool_state` int(11) DEFAULT 0,
  KEY `avatar_setting` (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_spec` (
  `id` int(250) NOT NULL,
  `email` varchar(250) NOT NULL,
  `birthday_1` varchar(2) NOT NULL,
  `birthday_2` varchar(2) NOT NULL,
  `birthday_3` varchar(4) NOT NULL,
  `happyB` int(10) NOT NULL DEFAULT 0,
  `happyBY` int(250) NOT NULL DEFAULT 0,
  `activated1` enum('0','1') NOT NULL DEFAULT '0',
  `username` varchar(250) NOT NULL,
  `userpo_referidos` int(99) NOT NULL DEFAULT 0,
  `country` text DEFAULT NULL,
  `radio` varchar(100) NOT NULL DEFAULT 'autoplay',
  `publi` enum('0','1','2','3') NOT NULL DEFAULT '0',
  `guia` enum('0','1','2','3','4') NOT NULL DEFAULT '0',
  `tarea` varchar(50) NOT NULL DEFAULT '0',
  `bancobloq` int(99) NOT NULL,
  `croupier` enum('0','1') NOT NULL DEFAULT '0',
  `mastertrade` enum('0','1','2','3','4') NOT NULL DEFAULT '0',
  `cms_signature` text DEFAULT NULL,
  `facebook` text NOT NULL,
  `twitter` text NOT NULL,
  `instagram` text NOT NULL,
  `youtube` text NOT NULL,
  `redes` enum('0','1','2') NOT NULL DEFAULT '1',
  `diamantes` enum('0','1','2') NOT NULL DEFAULT '1',
  `duckets` enum('0','1','2') NOT NULL DEFAULT '1',
  `puntoshonor` enum('0','1','2') NOT NULL DEFAULT '1',
  `placasperfil` enum('0','1','2') NOT NULL DEFAULT '1',
  `amigos` enum('0','1','2') NOT NULL DEFAULT '1',
  `salas` enum('0','1','2') NOT NULL DEFAULT '1',
  `libro` enum('0','1','2') NOT NULL DEFAULT '1',
  `perfil` enum('0','1','2') NOT NULL DEFAULT '1',
  `color` varchar(500) NOT NULL DEFAULT '#e6c873',
  `colorcinta` varchar(500) NOT NULL DEFAULT '#806f40',
  `cde` enum('0','1','2','3','4','5','6','7','8') NOT NULL DEFAULT '0',
  `pre` enum('0','1','2','3') NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_stats` (
  `player_id` int(10) NOT NULL DEFAULT 0,
  `achievement_score` int(10) DEFAULT 0,
  `total_respect_points` int(11) DEFAULT 0,
  `daily_respects` int(3) DEFAULT 3,
  `daily_scratches` int(3) DEFAULT 3,
  `help_tickets` int(11) DEFAULT 0,
  `cautions` int(11) DEFAULT 0,
  `help_tickets_abusive` int(11) DEFAULT 0,
  `bans` int(11) DEFAULT 0,
  `trade_lock` int(11) DEFAULT 0,
  `level` int(10) NOT NULL DEFAULT 0,
  `experience_points` int(10) NOT NULL DEFAULT 0,
  `fireworks` int(10) NOT NULL DEFAULT 0,
  `daily_rolls` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`player_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `player_subscriptions` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) DEFAULT 'habbo_vip',
  `start` int(255) DEFAULT 0,
  `expire` int(255) DEFAULT 0,
  `user_id` int(11) DEFAULT 0,
  `presents` int(11) DEFAULT 1,
  `allowed_items` int(11) DEFAULT 50,
  `borrowed_items` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `player_tags` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) DEFAULT NULL,
  `tag` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `player_id_badge_code` (`player_id`,`tag`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `polls` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL DEFAULT 'Untitled Poll',
  `thanks_message` varchar(100) NOT NULL DEFAULT 'Thanks!',
  `reward_badge` varchar(100) NOT NULL DEFAULT 'US8',
  `reward_credits` int(11) NOT NULL DEFAULT 0,
  `reward_vip_points` int(11) NOT NULL DEFAULT 0,
  `reward_activity_points` int(11) NOT NULL DEFAULT 0,
  `reward_achievement_points` int(11) NOT NULL DEFAULT 0,
  `room_id` int(11) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `polls_answers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poll_id` int(11) DEFAULT 0,
  `question_id` int(11) DEFAULT 0,
  `player_id` int(11) DEFAULT 0,
  `answer` varchar(1000) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `poll_id_question_id_player_id` (`poll_id`,`question_id`,`player_id`) USING BTREE,
  KEY `player_id` (`player_id`) USING BTREE,
  KEY `poll_id` (`poll_id`) USING BTREE,
  KEY `poll_id_question_id` (`poll_id`,`question_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `polls_questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `poll_id` int(11) NOT NULL,
  `question_type` enum('WORDED','MULTIPLE_CHOICE','SINGLE_CHOICE') NOT NULL DEFAULT 'WORDED',
  `question` varchar(100) NOT NULL DEFAULT 'What do you think of Comet Server?',
  `options` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `poll_id` (`poll_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `profile_wall` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text NOT NULL,
  `titel` tinytext NOT NULL,
  `message` text NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `quests` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(32) NOT NULL DEFAULT '',
  `series_number` int(11) NOT NULL DEFAULT 0,
  `goal_type` int(10) NOT NULL DEFAULT 0,
  `goal_data` int(10) unsigned NOT NULL DEFAULT 0,
  `name` varchar(32) NOT NULL DEFAULT '',
  `reward` varchar(50) NOT NULL DEFAULT '10',
  `badge_id` varchar(50) NOT NULL DEFAULT '',
  `reward_type` enum('ACHIEVEMENT_POINTS','VIP_POINTS','ACTIVITY_POINTS','CREDITS','SEASONAL_POINTS','GO_TO_ROOM','ITEM','CANDY_CHEST','BADGE','WEEN_ENDING') NOT NULL DEFAULT 'ACTIVITY_POINTS',
  `data_bit` varchar(2) NOT NULL DEFAULT '',
  `enabled` enum('0','1') DEFAULT '1',
  `timestamp` int(10) unsigned NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `nameUnique` (`name`) USING BTREE,
  KEY `nameKey` (`name`) USING BTREE,
  KEY `categoryKey` (`category`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `ranks` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET latin1 NOT NULL,
  `badgeid` varchar(10) CHARACTER SET latin1 DEFAULT NULL,
  `colour` varchar(50) CHARACTER SET latin1 DEFAULT 'blue',
  `staff_page` enum('1','0') CHARACTER SET latin1 DEFAULT '0',
  `description` varchar(100) CHARACTER SET latin1 DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf32 COLLATE=utf32_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `referrer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` decimal(10,0) DEFAULT NULL,
  `refid` decimal(10,0) DEFAULT NULL,
  `diamonds` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `referrerbank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) DEFAULT NULL,
  `diamonds` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `refers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `registered_id` int(250) NOT NULL,
  `refer_id` int(250) NOT NULL,
  `timestamp` int(250) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `resetpassword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `resetkey` varchar(255) DEFAULT NULL,
  `enable` enum('0','1') DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `rooms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('PUBLIC','PRIVATE') DEFAULT 'PRIVATE',
  `owner_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL DEFAULT 0,
  `owner` varchar(50) NOT NULL DEFAULT 'John',
  `name` varchar(64) CHARACTER SET utf8mb4 NOT NULL DEFAULT '',
  `description` varchar(255) NOT NULL DEFAULT '',
  `tags` varchar(65) NOT NULL DEFAULT '',
  `access_type` enum('password','doorbell','open','invisible') NOT NULL DEFAULT 'open',
  `password` varchar(64) NOT NULL DEFAULT '',
  `category` int(10) NOT NULL DEFAULT 15,
  `max_users` int(11) NOT NULL DEFAULT 25,
  `score` int(11) NOT NULL DEFAULT 0,
  `model` varchar(64) NOT NULL DEFAULT 'model_a',
  `allow_pets` enum('0','1') NOT NULL DEFAULT '1',
  `allow_walkthrough` enum('0','1') NOT NULL DEFAULT '1',
  `hide_walls` enum('0','1') NOT NULL DEFAULT '0',
  `thickness_wall` int(11) NOT NULL DEFAULT 1,
  `thickness_floor` int(11) NOT NULL DEFAULT 1,
  `decorations` varchar(128) NOT NULL DEFAULT 'landscape=0.0',
  `heightmap` text DEFAULT NULL,
  `trade_state` enum('OWNER_ONLY','ENABLED','DISABLED') DEFAULT 'ENABLED',
  `mute_state` enum('NONE','RIGHTS') NOT NULL DEFAULT 'NONE',
  `kick_state` enum('NONE','RIGHTS','EVERYONE') NOT NULL DEFAULT 'RIGHTS',
  `ban_state` enum('NONE','RIGHTS') NOT NULL DEFAULT 'NONE',
  `bubble_mode` tinyint(3) NOT NULL DEFAULT 0,
  `bubble_type` tinyint(3) DEFAULT 0,
  `bubble_scroll` tinyint(3) NOT NULL DEFAULT 0,
  `chat_distance` tinyint(3) NOT NULL DEFAULT 0,
  `flood_level` tinyint(3) NOT NULL DEFAULT 0,
  `disabled_commands` varchar(255) DEFAULT '',
  `required_badge` varchar(50) DEFAULT NULL,
  `thumbnail` varchar(128) NOT NULL DEFAULT 'navigator-thumbnail/default.png',
  `users_now` int(11) DEFAULT 0,
  `hide_wired` enum('0','1') DEFAULT '0',
  `allow_recount` enum('0','1') NOT NULL DEFAULT '0',
  `roller_speed` int(11) DEFAULT 4,
  `has_sorting` enum('0','1') NOT NULL DEFAULT '0',
  `advanced_collision` enum('0','1') NOT NULL DEFAULT '0',
  `user_idle_ticks` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `owner_id` (`id`,`owner_id`) USING BTREE,
  KEY `name` (`name`) USING BTREE,
  KEY `tags` (`tags`) USING BTREE,
  KEY `score` (`score`) USING BTREE,
  KEY `category` (`category`) USING BTREE,
  KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `rooms_catalog` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `room_id` int(250) NOT NULL,
  `name` varchar(250) NOT NULL,
  `image` varchar(250) NOT NULL,
  `owner` varchar(250) NOT NULL,
  `type` int(1) NOT NULL,
  `price` int(100) NOT NULL,
  `sell` int(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `rooms_promoted` (
  `room_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `description` varchar(128) DEFAULT NULL,
  `time_start` int(11) DEFAULT NULL,
  `time_expire` int(11) DEFAULT NULL,
  PRIMARY KEY (`room_id`) USING BTREE,
  KEY `expire` (`time_expire`) USING BTREE,
  KEY `room_id` (`room_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `room_bans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NOT NULL DEFAULT 0,
  `player_id` int(11) NOT NULL DEFAULT 0,
  `expire_timestamp` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC COMMENT='latin1_swedish_ci';

CREATE TABLE IF NOT EXISTS `room_bundles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enabled` enum('1','0') NOT NULL DEFAULT '1',
  `alias` varchar(255) DEFAULT 'roombundle',
  `room_id` int(11) DEFAULT NULL,
  `model_data` text DEFAULT NULL,
  `bundle_data` text DEFAULT NULL,
  `cost_credits` int(11) DEFAULT 20,
  `cost_seasonal` int(11) DEFAULT 0,
  `cost_vip` int(11) DEFAULT 0,
  `cost_activity_points` int(11) DEFAULT 0,
  `room_config` text DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `alias` (`alias`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `room_models` (
  `id` varchar(100) NOT NULL,
  `door_x` int(11) NOT NULL,
  `door_y` int(11) NOT NULL,
  `door_dir` int(4) NOT NULL DEFAULT 2,
  `heightmap` text NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `room_rights` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `player_id` int(10) DEFAULT 0,
  `room_id` int(10) DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `room_word_filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` int(11) NOT NULL DEFAULT 0,
  `word` varchar(255) NOT NULL DEFAULT 'bobba',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `room_id_word` (`room_id`,`word`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `server_articles` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(128) DEFAULT NULL,
  `message` varchar(512) DEFAULT NULL,
  `button_text` varchar(35) DEFAULT NULL,
  `button_link` varchar(512) DEFAULT '',
  `image_path` varchar(200) DEFAULT NULL,
  `visible` enum('1','0') DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `server_bets` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `amount` varchar(255) NOT NULL,
  `timestamp` varchar(255) NOT NULL,
  `result` varchar(255) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `server_calendar_gifts` (
  `day` int(11) NOT NULL,
  `gift` varchar(24) NOT NULL DEFAULT '',
  `product` varchar(32) NOT NULL DEFAULT '',
  `image` varchar(128) DEFAULT '',
  `item` varchar(32) NOT NULL DEFAULT '',
  PRIMARY KEY (`day`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `server_config` (
  `key` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `value` text CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `server_configuration` (
  `motd_enabled` enum('true','false') NOT NULL DEFAULT 'true',
  `motd_message` varchar(500) NOT NULL DEFAULT 'Welcome to Comet!',
  `hotel_name` varchar(100) NOT NULL DEFAULT 'Comet',
  `hotel_url` varchar(100) NOT NULL DEFAULT '',
  `group_cost` int(11) NOT NULL DEFAULT 100,
  `online_reward_enabled` enum('true','false') NOT NULL DEFAULT 'true',
  `online_reward_interval` int(11) NOT NULL DEFAULT 15,
  `online_reward_credits` int(11) NOT NULL DEFAULT 150,
  `online_reward_duckets` int(11) NOT NULL DEFAULT 150,
  `online_reward_diamonds_interval` int(11) NOT NULL DEFAULT 150,
  `online_reward_diamonds` int(11) NOT NULL DEFAULT 150,
  `online_reward_double_days` varchar(255) NOT NULL DEFAULT '',
  `about_image` varchar(100) NOT NULL DEFAULT '',
  `about_show_players_online` enum('true','false') NOT NULL DEFAULT 'false',
  `about_show_rooms_active` enum('true','false') NOT NULL DEFAULT 'false',
  `about_show_uptime` enum('true','false') NOT NULL DEFAULT 'false',
  `floor_editor_max_x` int(11) NOT NULL DEFAULT 0,
  `floor_editor_max_y` int(11) NOT NULL DEFAULT 0,
  `floor_editor_max_total` int(11) NOT NULL DEFAULT 0,
  `room_max_players` int(11) NOT NULL DEFAULT 150,
  `room_encrypt_passwords` enum('true','false') NOT NULL DEFAULT 'false',
  `room_can_place_item_on_entity` enum('true','false') NOT NULL DEFAULT 'false',
  `room_max_bots` int(11) NOT NULL DEFAULT 15,
  `room_max_pets` int(11) NOT NULL DEFAULT 15,
  `room_wired_reward_minimum_rank` int(11) NOT NULL DEFAULT 7,
  `room_wired_max_effects` int(11) NOT NULL DEFAULT 10,
  `room_wired_max_triggers` int(11) NOT NULL DEFAULT 10,
  `room_wired_max_execute_stacks` int(11) NOT NULL DEFAULT 5,
  `room_idle_minutes` int(11) NOT NULL DEFAULT 15,
  `word_filter_mode` enum('default','smart','strict') NOT NULL DEFAULT 'default',
  `word_filter_strict_chars` varchar(500) NOT NULL DEFAULT '',
  `use_database_ip` enum('true','false') NOT NULL DEFAULT 'false',
  `save_logins` enum('true','false') NOT NULL DEFAULT 'false',
  `player_infinite_balance` enum('true','false') NOT NULL DEFAULT 'false',
  `player_gift_cooldown` int(11) NOT NULL DEFAULT 30,
  `player_change_figure_cooldown` int(11) NOT NULL DEFAULT 5,
  `player_figure_validation` enum('true','false') NOT NULL DEFAULT 'false',
  `messenger_max_friends` int(11) NOT NULL DEFAULT 1500,
  `messenger_log_messages` enum('true','false') NOT NULL DEFAULT 'true',
  `camera_photo_url` varchar(255) NOT NULL DEFAULT 'http://localhost:8080/camera/photo/%photoId%.png',
  `camera_photo_upload_url` varchar(255) NOT NULL DEFAULT 'http://localhost:8080/camera/upload/%photoId%',
  `camera_photo_itemid` int(11) NOT NULL DEFAULT 50001,
  `max_connections_per_ip` int(11) NOT NULL DEFAULT 5,
  `max_connections_block_suspicious` enum('true','false') NOT NULL DEFAULT 'true',
  `log_catalog_purchases` enum('true','false') DEFAULT 'true',
  `group_chat_enabled` enum('true','false') NOT NULL DEFAULT 'false',
  `hall_of_fame_enabled` enum('true','false') NOT NULL DEFAULT 'true',
  `hall_of_fame_currency` varchar(50) NOT NULL DEFAULT 'vip_points',
  `hall_of_fame_refresh_minutes` int(10) NOT NULL DEFAULT 10,
  `hall_of_fame_texts_key` varchar(50) NOT NULL DEFAULT 'halloffame',
  `bet_system_enabled` enum('true','false') NOT NULL DEFAULT 'true',
  `bet_system_roomid` int(11) NOT NULL DEFAULT 0,
  `event_winner_enabled` enum('true','false') NOT NULL DEFAULT 'false',
  `max_seasonal_reward` int(11) NOT NULL DEFAULT 50,
  `casino_free_roll_enabled` enum('true','false') NOT NULL DEFAULT 'false',
  `bank_mininum_required` int(11) NOT NULL DEFAULT 25,
  `bank_seasonal_enabled` enum('true','false') NOT NULL DEFAULT 'false',
  `landing_bag_enabled` enum('true','false') NOT NULL DEFAULT 'true',
  `landing_bag_configuration` varchar(255) NOT NULL DEFAULT 'item,102,diamonds,50',
  `currency_enabled` enum('true','false') NOT NULL DEFAULT 'false',
  `websocket_url` varchar(100) NOT NULL DEFAULT '',
  `casino_roomid` int(11) NOT NULL DEFAULT 12951,
  `community_goal` int(11) NOT NULL DEFAULT 150,
  `community_goal_prize` varchar(100) NOT NULL DEFAULT 'ADM',
  `easter_limited` int(11) NOT NULL DEFAULT 1,
  `calendar_timestamp` int(11) NOT NULL,
  `rp_hospital_roomid` int(11) NOT NULL DEFAULT 1,
  `rp_police_roomid` int(11) NOT NULL DEFAULT 1,
  `rp_law_roomid` int(11) NOT NULL DEFAULT 1,
  `rp_mafia_roomid` int(11) NOT NULL DEFAULT 1,
  `rp_politics_roomid` int(11) NOT NULL DEFAULT 1,
  `rp_hospital_salary` int(11) NOT NULL DEFAULT 1,
  `rp_police_salary` int(11) NOT NULL DEFAULT 1,
  `rp_law_salary` int(11) NOT NULL DEFAULT 1,
  `rp_mafia_salary` int(11) NOT NULL DEFAULT 1,
  `rp_politic_salary` int(11) NOT NULL DEFAULT 1,
  `rp_salary_interval` int(11) NOT NULL DEFAULT 1,
  `rp_hunger_interval` int(11) NOT NULL DEFAULT 60,
  `rp_hunger_tick_amount` int(11) NOT NULL DEFAULT 5,
  `rp_starving_interval` int(11) NOT NULL DEFAULT 35,
  `rp_starving_tick_amount` int(11) NOT NULL DEFAULT 5,
  `console_debug` enum('true','false') NOT NULL DEFAULT 'true',
  `snow_storm_min_players` int(11) NOT NULL DEFAULT 5,
  `welcome_room_id` int(11) NOT NULL DEFAULT 1,
  `seasonal_activity_points` int(11) NOT NULL DEFAULT 1,
  `thumbnail_upload_url` varchar(100) NOT NULL DEFAULT 'http://localhost:8080/camera/upload/%photoId%'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `server_locale` (
  `key` varchar(50) NOT NULL,
  `value` text DEFAULT NULL,
  PRIMARY KEY (`key`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `server_permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `flood_bypass` enum('1','0') NOT NULL DEFAULT '0',
  `flood_time` int(11) NOT NULL DEFAULT 30,
  `disconnectable` enum('1','0') NOT NULL DEFAULT '1',
  `mod_tool` enum('1','0') NOT NULL DEFAULT '0',
  `alfa_tool` enum('1','0') NOT NULL DEFAULT '0',
  `bannable` enum('1','0') NOT NULL DEFAULT '0',
  `room_kickable` enum('1','0') NOT NULL DEFAULT '1',
  `room_full_control` enum('1','0') NOT NULL DEFAULT '1',
  `room_mute_bypass` enum('1','0') NOT NULL DEFAULT '1',
  `room_filter_bypass` enum('1','0') NOT NULL DEFAULT '0',
  `room_ignorable` enum('1','0') NOT NULL DEFAULT '1',
  `room_enter_full` enum('1','0') NOT NULL DEFAULT '1',
  `cmd_search` enum('1','0') NOT NULL DEFAULT '1',
  `room_enter_locked` enum('1','0') NOT NULL DEFAULT '1',
  `room_staff_pick` enum('1','0') NOT NULL DEFAULT '1',
  `room_see_whispers` enum('1','0') NOT NULL DEFAULT '1',
  `messenger_staff_chat` enum('1','0') NOT NULL DEFAULT '0',
  `messenger_mod_chat` enum('1','0') NOT NULL DEFAULT '0',
  `messenger_log_chat` enum('1','0') NOT NULL DEFAULT '0',
  `messenger_alfa_chat` enum('1','0') NOT NULL DEFAULT '0',
  `messenger_max_friends` int(11) NOT NULL DEFAULT 1100,
  `about_detailed` enum('1','0') NOT NULL DEFAULT '0',
  `about_stats` enum('1','0') NOT NULL DEFAULT '0',
  `login_notif` enum('1','0') NOT NULL DEFAULT '0',
  `about_command` enum('1','0') DEFAULT '0',
  `globalbubble_command` enum('1','0') DEFAULT '0',
  `globalalert_command` enum('1','0') DEFAULT '0',
  `freeze_command` enum('1','0') DEFAULT '0',
  `alertnoti_command` enum('1','0') DEFAULT '0',
  `preguntar_command` enum('1','0') DEFAULT '0',
  `commands_command` enum('1','0') DEFAULT '1',
  `restart_command` enum('1','0') DEFAULT '0',
  `teleport_command` enum('2','1','0') DEFAULT '0',
  `sellroom_command` enum('2','1','0') DEFAULT '2',
  `pickall_command` enum('2','1','0') DEFAULT '0',
  `massmotd_command` enum('1','0') DEFAULT '0',
  `hotelalert_command` enum('1','0') DEFAULT '0',
  `invisible_command` enum('1','0') DEFAULT '0',
  `disablefun_command` enum('1','0') DEFAULT '0',
  `push_command` enum('2','1','0') DEFAULT '0',
  `moonwalk_command` enum('1','0') DEFAULT '0',
  `enable_command` enum('1','0') DEFAULT '0',
  `ban_command` enum('1','0') DEFAULT '0',
  `empty_command` enum('1','0') DEFAULT '0',
  `unload_command` enum('2','1','0') DEFAULT '0',
  `roomvideo_command` enum('2','1','0') DEFAULT '2',
  `ipban_command` enum('1','0') DEFAULT '0',
  `givebadge_command` enum('1','0') DEFAULT '0',
  `reload_command` enum('2','1','0') DEFAULT '0',
  `transform_command` enum('1','0') DEFAULT '0',
  `coins_command` enum('1','0') DEFAULT '0',
  `pull_command` enum('2','1','0') DEFAULT '0',
  `sit_command` enum('1','0') DEFAULT '0',
  `buyroom_command` enum('1','0') DEFAULT '1',
  `prefix_command` enum('1','0') DEFAULT '1',
  `alert_command` enum('1','0') DEFAULT '0',
  `points_command` enum('1','0') DEFAULT '0',
  `kick_command` enum('1','0') DEFAULT '0',
  `mimic_command` enum('1','0') DEFAULT '0',
  `machineban_command` enum('1','0') DEFAULT '0',
  `massbadge_command` enum('1','0') DEFAULT '0',
  `masscoins_command` enum('1','0') DEFAULT '0',
  `masspoints_command` enum('1','0') DEFAULT '0',
  `massduckets_command` enum('1','0') DEFAULT '0',
  `redeemcredits_command` enum('1','0') DEFAULT '0',
  `playerinfo_command` enum('1','0') DEFAULT '0',
  `roommute_command` enum('1','0') DEFAULT '0',
  `roomunmute_command` enum('1','0') DEFAULT '0',
  `handitem_command` enum('1','0') DEFAULT '0',
  `setmax_command` enum('2','1','0') DEFAULT '0',
  `removebadge_command` enum('1','0') DEFAULT '0',
  `deletegroup_command` enum('1','0') DEFAULT '0',
  `shutdown_command` enum('1','0') DEFAULT '0',
  `togglediagonal_command` enum('2','1','0') DEFAULT '0',
  `roll_command` enum('1','0') DEFAULT '0',
  `hotelalertlink_command` enum('1','0') DEFAULT '0',
  `summon_command` enum('1','0') DEFAULT '0',
  `togglefriends_command` enum('1','0') DEFAULT '0',
  `roomaction_command` enum('1','0') DEFAULT '0',
  `enablecommand_command` enum('1','0') DEFAULT '0',
  `disablecommand_command` enum('1','0') DEFAULT '0',
  `mute_command` enum('1','0') DEFAULT '0',
  `unmute_command` enum('1','0') DEFAULT '0',
  `punch_command` enum('2','1','0') DEFAULT '0',
  `bundle_command` enum('1','0') DEFAULT '0',
  `notification_command` enum('1','0') DEFAULT '0',
  `maintenance_command` enum('1','0') DEFAULT '0',
  `eventalert_command` enum('1','0') DEFAULT '0',
  `quickpoll_command` enum('1','0') DEFAULT '0',
  `ejectall_command` enum('1','0') DEFAULT '0',
  `fastwalk_command` enum('1','0') DEFAULT '0',
  `roomoption_command` enum('1','0') DEFAULT '0',
  `ignoreevents_command` enum('1','0') DEFAULT '0',
  `hidewired_command` enum('1','0') DEFAULT '0',
  `eventreward_command` enum('1','0') DEFAULT '0',
  `emptyfriends_command` enum('1','0') DEFAULT '0',
  `reward_command` enum('1','0') DEFAULT '0',
  `massteleport_command` enum('1','0') DEFAULT '0',
  `massfreeze_command` enum('1','0') DEFAULT '0',
  `eventwon_command` enum('1','0') DEFAULT '0',
  `height_command` enum('1','0') DEFAULT '0',
  `listen_command` enum('1','0') DEFAULT '0',
  `cloneroom_command` enum('1','0') DEFAULT '0',
  `follow_command` enum('1','0') DEFAULT '0',
  `rob_command` enum('1','0') DEFAULT '0',
  `kiss_command` enum('1','0') DEFAULT '0',
  `sex_command` enum('1','0') DEFAULT '1',
  `nalgada_command` enum('1','0') DEFAULT '1',
  `murder_command` enum('1','0') DEFAULT '1',
  `sing_command` enum('1','0') DEFAULT '1',
  `secuestrar_command` enum('1','0') DEFAULT '1',
  `nuke_command` enum('1','0') DEFAULT '1',
  `staffon_command` enum('1','0') DEFAULT '1',
  `hug_command` enum('1','0') DEFAULT '0',
  `lay_command` enum('1','0') DEFAULT '0',
  `mass_seasonal_command` enum('1','0') DEFAULT '0',
  `staffalert_command` enum('1','0') DEFAULT '0',
  `roomalert_command` enum('1','0') DEFAULT '0',
  `roomkick_command` enum('1','0') DEFAULT '0',
  `makesay_command` enum('1','0') DEFAULT '0',
  `disconnect_command` enum('1','0') DEFAULT '0',
  `superpull_command` enum('1','0') DEFAULT '0',
  `staffinfo_command` enum('1','0') DEFAULT '0',
  `unban_command` enum('1','0') DEFAULT '0',
  `roomnotification_command` enum('1','0') DEFAULT '0',
  `namecolour_command` enum('1','0') DEFAULT '0',
  `disablewhisper_command` enum('1','0') DEFAULT '0',
  `superban_command` enum('1','0') DEFAULT '0',
  `setspeed_command` enum('1','0') DEFAULT '0',
  `viewinventory_command` enum('1','0') DEFAULT '0',
  `mentionsettings_command` enum('1','0') DEFAULT '0',
  `toggleshoot_command` enum('1','0') DEFAULT '0',
  `personalstaff_command` enum('1','0') DEFAULT '0',
  `seasonal_command` enum('1','0') DEFAULT '0',
  `setbet_command` enum('1','0') DEFAULT '0',
  `warp_command` enum('2','1','0') DEFAULT '0',
  `changelog_command` enum('1','0') DEFAULT '0',
  `staffbubble_command` enum('1','0') DEFAULT '0',
  `tradeban_command` enum('1','0') DEFAULT '0',
  `resetdicecount_command` enum('1','0') DEFAULT '0',
  `betsystem_command` enum('1','0') DEFAULT '0',
  `rps_command` enum('1','0') DEFAULT '0',
  `bank_command` enum('1','0') DEFAULT '0',
  `eventlog_command` enum('1','0') DEFAULT '0',
  `finalevent_command` enum('1','0') DEFAULT '0',
  `selectwindow_command` enum('1','0') DEFAULT '0',
  `voucher_command` enum('1','0') DEFAULT '0',
  `puke_command` enum('1','0') DEFAULT '0',
  `help_command` enum('1','0') DEFAULT '0',
  `link_command` enum('1','0') DEFAULT '0',
  `idle_command` enum('1','0') DEFAULT '0',
  `control_command` enum('1','0') DEFAULT '0',
  `position_command` enum('1','0') DEFAULT '0',
  `build_command` enum('1','0') DEFAULT '0',
  `massrare_command` enum('1','0') DEFAULT '0',
  `invitation_command` enum('1','0') DEFAULT '0',
  `invite_command` enum('1','0') DEFAULT '0',
  `setz_command` enum('1','0') DEFAULT '0',
  `vipbundle_command` enum('1','0') DEFAULT '0',
  `flaguser_command` enum('1','0') DEFAULT '0',
  `override_command` enum('1','0') DEFAULT '0',
  `furnifix_command` enum('1','0') DEFAULT '0',
  `closedice_command` enum('1','0') DEFAULT '1',
  `smoke_command` enum('1','0') DEFAULT '1',
  `married_command` enum('1','0') DEFAULT '1',
  `mentions_command` enum('1','0') DEFAULT '1',
  `look_command` enum('1','0') DEFAULT '1',
  `giverank_command` enum('1','0') DEFAULT '0',
  `wha_command` enum('1','0') DEFAULT '0',
  `flagme_command` enum('1','0') DEFAULT '0',
  `autofloor_command` enum('2','1','0') DEFAULT '2',
  `maxfloor_command` enum('2','1','0') DEFAULT '2',
  `setidletimer_command` enum('2','1','0') DEFAULT '1',
  `flooredit_command` enum('0','1','2') DEFAULT '2',
  `superwired_command` enum('0','1') DEFAULT '1',
  `superpush_command` enum('0','1') DEFAULT '0',
  `welcome_command` enum('1','0') DEFAULT '0',
  `lava_command` enum('1','0') DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `server_permissions_ranks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL DEFAULT '0',
  `flood_bypass` enum('1','0') NOT NULL DEFAULT '0',
  `flood_time` int(11) NOT NULL DEFAULT 30,
  `disconnectable` enum('1','0') NOT NULL DEFAULT '1',
  `mod_tool` enum('1','0') NOT NULL DEFAULT '0',
  `bannable` enum('1','0') NOT NULL DEFAULT '0',
  `room_kickable` enum('1','0') NOT NULL DEFAULT '1',
  `room_full_control` enum('1','0') NOT NULL DEFAULT '1',
  `room_mute_bypass` enum('1','0') NOT NULL DEFAULT '1',
  `room_filter_bypass` enum('1','0') NOT NULL DEFAULT '0',
  `room_ignorable` enum('1','0') NOT NULL DEFAULT '1',
  `room_enter_full` enum('1','0') NOT NULL DEFAULT '1',
  `room_enter_locked` enum('1','0') NOT NULL DEFAULT '1',
  `room_staff_pick` enum('1','0') NOT NULL DEFAULT '1',
  `room_see_whispers` enum('1','0') NOT NULL DEFAULT '1',
  `messenger_staff_chat` enum('1','0') NOT NULL DEFAULT '0',
  `messenger_max_friends` int(11) NOT NULL DEFAULT 1100,
  `about_detailed` enum('1','0') NOT NULL DEFAULT '0',
  `about_stats` enum('1','0') NOT NULL DEFAULT '0',
  `messenger_log_chat` enum('1','0') NOT NULL DEFAULT '1',
  `login_notif` enum('1','0') DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `server_ranks` (
  `rank_id` int(11) NOT NULL DEFAULT 1,
  `title` varchar(50) NOT NULL DEFAULT 'Player',
  `badge_id` varchar(50) DEFAULT 'Player',
  `permission_mod_tool` enum('1','0') NOT NULL DEFAULT '0',
  `permission_room_full_control` enum('1','0') NOT NULL DEFAULT '0',
  `permission_bypass_filter` enum('1','0') NOT NULL DEFAULT '0',
  `permission_room_unkickable` enum('1','0') NOT NULL DEFAULT '0',
  `permission_unignorable` enum('1','0') NOT NULL DEFAULT '0',
  `permission_room_staff_pick` enum('1','0') NOT NULL DEFAULT '0',
  `messenger_log_chat` enum('1','0') NOT NULL DEFAULT '0',
  `flood_length` int(11) DEFAULT 30,
  PRIMARY KEY (`rank_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `server_status` (
  `active_players` int(11) DEFAULT 0,
  `player_record` int(11) DEFAULT 0,
  `active_rooms` int(11) DEFAULT 0,
  `server_version` varchar(50) DEFAULT '0',
  `player_record_timestamp` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `server_survival_settings` (
  `gun_damage` int(11) NOT NULL DEFAULT 15,
  `gun_distance` int(11) NOT NULL DEFAULT 60,
  `gun_cooldown` int(11) NOT NULL DEFAULT 150,
  `gun_bullets` int(11) NOT NULL DEFAULT 150,
  `sniper_damage` int(11) NOT NULL DEFAULT 150,
  `sniper_distance` int(11) NOT NULL DEFAULT 5,
  `sniper_cooldown` int(11) NOT NULL DEFAULT 150,
  `sniper_bullets` int(11) NOT NULL DEFAULT 0,
  `melee_damage` int(11) NOT NULL DEFAULT 0,
  `melee_distance` int(11) NOT NULL DEFAULT 35,
  `melee_cooldown` int(11) NOT NULL DEFAULT 0,
  `slay_xp` int(11) NOT NULL DEFAULT 150,
  `win_xp` int(11) NOT NULL DEFAULT 5,
  `slay_bullets` int(11) NOT NULL DEFAULT 5,
  `slay_shield` enum('true','false') NOT NULL DEFAULT 'true',
  `chest_bullets` int(11) NOT NULL DEFAULT 5,
  `speed_time` int(11) NOT NULL DEFAULT 5
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `site_hotcampaigns` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL DEFAULT 1,
  `enabled` enum('0','1') NOT NULL DEFAULT '1',
  `image_url` text NOT NULL,
  `caption` text NOT NULL,
  `descr` text NOT NULL,
  `url` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `site_news` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `seo_link` varchar(120) NOT NULL DEFAULT 'news-article',
  `title` text NOT NULL,
  `category_id` int(10) unsigned NOT NULL DEFAULT 1,
  `topstory_image` text NOT NULL,
  `body` text NOT NULL,
  `snippet` text NOT NULL,
  `datestr` varchar(50) NOT NULL,
  `timestamp` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `datestr` (`datestr`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `sollicitaties` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `naam` text NOT NULL,
  `email` text NOT NULL,
  `skype` text NOT NULL,
  `leeftijd` text NOT NULL,
  `rank` text NOT NULL,
  `reden` text NOT NULL,
  `ervaring` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `songs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `song_data` text NOT NULL,
  `artist` varchar(32) NOT NULL,
  `creator_id` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `staffapplication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text DEFAULT NULL,
  `realname` text DEFAULT NULL,
  `skype` text DEFAULT NULL,
  `age` text DEFAULT NULL,
  `functie` text DEFAULT NULL,
  `onlinetime` text DEFAULT NULL,
  `experience` text DEFAULT NULL,
  `quarrel` text DEFAULT NULL,
  `serious` text DEFAULT NULL,
  `improve` text DEFAULT NULL,
  `microphone` text DEFAULT NULL,
  `ip` text DEFAULT NULL,
  `date` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `staff_logs` (
  `player_id` int(11) NOT NULL DEFAULT 0,
  `type` enum('ALERT','BAN','DISCONNECT','CHECK','NONE') NOT NULL DEFAULT 'NONE',
  `target` varchar(50) NOT NULL DEFAULT '',
  `value` varchar(255) DEFAULT '',
  `time` int(11) NOT NULL DEFAULT 0,
  KEY `player_id` (`player_id`),
  KEY `target` (`target`),
  KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `sterrenkopen` (
  `naam` varchar(100) NOT NULL,
  `datum` datetime NOT NULL,
  `aantal` varchar(100) NOT NULL,
  `betaald` varchar(100) NOT NULL DEFAULT 'ja'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `teamapplication` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` text DEFAULT NULL,
  `realname` text DEFAULT NULL,
  `skype` text DEFAULT NULL,
  `age` text DEFAULT NULL,
  `functie` text DEFAULT NULL,
  `onlinetime` text DEFAULT NULL,
  `experience` text DEFAULT NULL,
  `quarrel` text DEFAULT NULL,
  `serious` text DEFAULT NULL,
  `improve` text DEFAULT NULL,
  `microphone` text DEFAULT NULL,
  `ip` text DEFAULT NULL,
  `date` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `teams` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `badgeid` varchar(5) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `uotw` (
  `userid` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL,
  `userid2` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL,
  `userid3` varchar(255) CHARACTER SET utf8mb3 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `users_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) DEFAULT NULL,
  `likefrom` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `user_session_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `browser` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `vip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `timestamp` int(11) NOT NULL,
  `timestampend` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `id_user` (`id_user`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `vouchers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('COINS','DUCKETS','VIP_POINTS','ROOM_BUNDLE','CRYPTOLOGY','ITEM') NOT NULL DEFAULT 'COINS',
  `data` text NOT NULL,
  `created_by` int(11) NOT NULL DEFAULT 0,
  `created_at` int(11) NOT NULL DEFAULT 0,
  `claimed_by` varchar(255) NOT NULL DEFAULT '0',
  `claimed_at` int(11) NOT NULL DEFAULT 0,
  `limit_use` int(11) NOT NULL DEFAULT 0,
  `status` enum('UNCLAIMED','CLAIMED') NOT NULL DEFAULT 'UNCLAIMED',
  `code` varchar(128) NOT NULL DEFAULT 'voucher-00001',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `code` (`code`) USING BTREE,
  KEY `created_by` (`created_by`) USING BTREE,
  KEY `claimed_by` (`claimed_by`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `website_articles` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `article_slug` varchar(50) NOT NULL DEFAULT '0',
  `article_title` varchar(96) NOT NULL DEFAULT '0',
  `article_description` varchar(128) NOT NULL DEFAULT '0',
  `article_body` text NOT NULL,
  `article_author` int(11) NOT NULL DEFAULT 0,
  `article_promo_image` varchar(96) NOT NULL DEFAULT '',
  `article_allow_comments` enum('true','false') NOT NULL DEFAULT 'false',
  `article_time_created` int(11) NOT NULL DEFAULT 0,
  `article_hidden` enum('true','false') DEFAULT 'false',
  PRIMARY KEY (`id`),
  UNIQUE KEY `article_slug` (`article_slug`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `website_config` (
  `hotel_name` varchar(50) DEFAULT 'Comet',
  `hotel_slogan` varchar(96) DEFAULT 'Comet',
  `hotel_description` text DEFAULT NULL,
  `twitter_username` varchar(50) DEFAULT 'habbo',
  `facebook_username` varchar(50) DEFAULT 'habbo',
  `twitter_widget_id` int(11) DEFAULT 0,
  `player_default_credits` int(11) DEFAULT 0,
  `player_default_activity_points` int(11) DEFAULT 0,
  `player_default_vip_points` int(11) DEFAULT 0,
  `player_default_figure` varchar(128) DEFAULT '0',
  `player_default_motto` varchar(128) DEFAULT '0',
  `player_default_homeroom` varchar(128) DEFAULT '0',
  `game_host` varchar(50) DEFAULT NULL,
  `game_port` int(5) DEFAULT NULL,
  `game_client_swf` varchar(128) DEFAULT '',
  `game_client_variables` varchar(128) DEFAULT '',
  `game_client_texts` varchar(128) DEFAULT '',
  `game_client_productdata` varchar(128) DEFAULT '',
  `game_client_furnidata` varchar(128) DEFAULT '',
  `game_client_base` varchar(128) DEFAULT '',
  `game_client_banner` varchar(128) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `website_gamedata_texts` (
  `key` varchar(255) NOT NULL,
  `value` text NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `website_gamedata_variables` (
  `key` varchar(50) NOT NULL,
  `value` text DEFAULT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `website_news` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `slug` varchar(255) NOT NULL DEFAULT '',
  `title` varchar(96) NOT NULL DEFAULT '0',
  `description` varchar(128) NOT NULL DEFAULT '0',
  `body` text NOT NULL,
  `images` text NOT NULL,
  `author` int(11) NOT NULL DEFAULT 0,
  `header` varchar(96) NOT NULL DEFAULT '',
  `timestamp` int(11) NOT NULL DEFAULT 0,
  `hidden` enum('0','1') DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `wordfilter` (
  `word` varchar(50) NOT NULL,
  `replacement` varchar(50) DEFAULT '*****'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `wordfilter_characters` (
  `character` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL DEFAULT '',
  `replacement` varchar(255) NOT NULL DEFAULT ''
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_alerts` (
  `id` varchar(255) NOT NULL,
  `alert` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 ROW_FORMAT=COMPACT;

CREATE TABLE IF NOT EXISTS `cms_catalog_categories` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `image` varchar(250) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `cms_catalog_items` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `image` varchar(250) NOT NULL,
  `category` int(250) NOT NULL,
  `price_diamonds` int(250) NOT NULL,
  `price_th` int(250) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `cms_clients` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `version` enum('0','24','60') DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=158 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_comments` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL,
  `value` text DEFAULT NULL,
  `author` int(11) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_errands` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_from_id` varchar(11) DEFAULT NULL,
  `user_to_id` varchar(11) DEFAULT NULL,
  `data` int(11) DEFAULT NULL,
  `value` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_events` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(25) DEFAULT NULL,
  `description` varchar(40) DEFAULT NULL,
  `type` enum('atividade','evento') DEFAULT NULL,
  `link` varchar(500) DEFAULT NULL,
  `image` varchar(300) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
  `timestamp_expire` int(11) DEFAULT NULL,
  `room_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_forms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `form` int(11) DEFAULT 0,
  `usernames` text DEFAULT NULL,
  `expire_timestamp` int(11) DEFAULT NULL,
  `link` text DEFAULT NULL,
  `message` text DEFAULT NULL,
  `status` enum('enabled','disabled') DEFAULT 'disabled',
  `form_code` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `image` varchar(100) NOT NULL DEFAULT '0',
  `shortstory` text NOT NULL,
  `longstory` text NOT NULL,
  `author` varchar(100) NOT NULL DEFAULT 'Tom',
  `date` int(11) NOT NULL DEFAULT 0,
  `expire_timestamp` int(11) NOT NULL DEFAULT 0,
  `form` int(11) NOT NULL DEFAULT 0,
  `rascunho` enum('0','1') NOT NULL DEFAULT '0',
  `comments` enum('enabled','disabled') NOT NULL DEFAULT 'enabled',
  `type` varchar(100) NOT NULL DEFAULT '1',
  `form_link` varchar(100) NOT NULL DEFAULT '1',
  `roomid` varchar(100) NOT NULL DEFAULT '1',
  `updated` enum('0','1') NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_news_comments` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `user_id` int(250) NOT NULL,
  `notice_id` int(250) NOT NULL,
  `comment` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `cms_news_like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(255) DEFAULT NULL,
  `newsid` int(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_news_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` int(11) NOT NULL DEFAULT 0,
  `buggid` int(11) DEFAULT NULL,
  `userid` int(11) DEFAULT NULL,
  `message` varchar(250) DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_panel_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5147 DEFAULT CHARSET=utf8mb3;

CREATE TABLE IF NOT EXISTS `cms_post_comments` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `type` enum('undefined','article','errand') NOT NULL DEFAULT 'undefined',
  `post_id` int(11) DEFAULT 0,
  `value` text DEFAULT NULL,
  `author_id` int(11) DEFAULT 0,
  `to_user_id` int(11) NOT NULL DEFAULT 0,
  `timestamp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_post_forms` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('undefined','article','radio') NOT NULL,
  `post_id` int(11) DEFAULT 0,
  `label` text DEFAULT NULL,
  `user_id` int(11) DEFAULT 0,
  `timestamp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_post_reaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` enum('undefined','article') NOT NULL DEFAULT 'undefined',
  `post_id` int(11) DEFAULT 0,
  `user_id` int(11) DEFAULT 0,
  `state` enum('undefined','like','deslike') NOT NULL DEFAULT 'undefined',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_profile_comments` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `profile_id` int(250) NOT NULL,
  `user_id` int(250) NOT NULL,
  `comment` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `cms_profile_posts` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `user_id` int(250) NOT NULL,
  `post` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `cms_rarevalues` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(25) DEFAULT '',
  `item_image` varchar(30) DEFAULT '',
  `item_cost` int(11) DEFAULT 0,
  `item_status` enum('up','down','same') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_reactions` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `state` enum('0','1','2') NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_referred` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `user_id` int(250) NOT NULL,
  `referred_id` int(250) NOT NULL,
  `referred_ip` varchar(100) COLLATE utf8mb4_spanish_ci NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci ROW_FORMAT=DYNAMIC;

CREATE TABLE IF NOT EXISTS `cms_settings` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `hotelname` varchar(255) NOT NULL DEFAULT 'Habbo',
  `site` varchar(255) NOT NULL DEFAULT 'http://localhost/',
  `host` varchar(30) DEFAULT NULL,
  `port` int(10) DEFAULT NULL,
  `external_variables` text DEFAULT NULL,
  `external_override_variables` text DEFAULT NULL,
  `external_flash_texts` text DEFAULT NULL,
  `external_flash_override_texts` text DEFAULT NULL,
  `figuredata` text DEFAULT NULL,
  `figuremap` text DEFAULT NULL,
  `furnidata` text DEFAULT NULL,
  `flash_client_url` text DEFAULT NULL,
  `productdata` text DEFAULT NULL,
  `avatarimage` varchar(255) NOT NULL DEFAULT 'http://www.habbo.fr/habbo-imaging/',
  `maintenance` set('enabled','disabled') NOT NULL DEFAULT 'disabled',
  `facebook` text NOT NULL,
  `twitter` text NOT NULL,
  `discord` text NOT NULL,
  `application` text DEFAULT NULL,
  `recaptcha` varchar(255) NOT NULL,
  `credits` varchar(255) NOT NULL DEFAULT '5000',
  `diamonds` int(11) NOT NULL DEFAULT 0,
  `duckets` int(11) NOT NULL DEFAULT 0,
  `motto` text DEFAULT NULL,
  `rank` int(11) NOT NULL DEFAULT 1,
  `figure` varchar(300) DEFAULT NULL,
  `cms_name` text DEFAULT NULL,
  `cms_version` text DEFAULT NULL,
  `cms_developers` text DEFAULT NULL,
  `force_room` enum('0','1') NOT NULL DEFAULT '0',
  `force_room_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `cms_tweets` (
  `id` int(250) NOT NULL AUTO_INCREMENT,
  `user_id` int(250) NOT NULL,
  `tweet` varchar(250) NOT NULL,
  `date` int(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `cms_wordfilter` (
  `word` text DEFAULT NULL,
  `replacement` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

