CREATE TABLE IF NOT EXISTS `permission_groups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `display_name` varchar(100) NOT NULL,
  `priority` int NOT NULL DEFAULT 0,
  `legacy_rank_id` int NOT NULL DEFAULT 1,
  `staff` enum('1','0') NOT NULL DEFAULT '0',
  `default_group` enum('1','0') NOT NULL DEFAULT '0',
  `enabled` enum('1','0') NOT NULL DEFAULT '1',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_groups_code_unique` (`code`),
  KEY `permission_groups_priority_index` (`priority`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `permission_group_nodes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL,
  `node` varchar(190) NOT NULL,
  `effect` enum('ALLOW','DENY') NOT NULL DEFAULT 'ALLOW',
  `context` varchar(50) NOT NULL DEFAULT 'global',
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `permission_group_nodes_unique` (`group_id`, `node`, `context`),
  KEY `permission_group_nodes_node_index` (`node`),
  CONSTRAINT `permission_group_nodes_group_fk`
    FOREIGN KEY (`group_id`) REFERENCES `permission_groups` (`id`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `player_permission_groups` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `player_id` int NOT NULL,
  `group_id` bigint NOT NULL,
  `granted_by` varchar(100) NOT NULL DEFAULT 'system',
  `reason` varchar(255) NOT NULL DEFAULT '',
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `player_permission_groups_unique` (`player_id`, `group_id`),
  KEY `player_permission_groups_player_index` (`player_id`),
  KEY `player_permission_groups_group_index` (`group_id`),
  CONSTRAINT `player_permission_groups_group_fk`
    FOREIGN KEY (`group_id`) REFERENCES `permission_groups` (`id`)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS `permission_audit_log` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action` varchar(100) NOT NULL,
  `actor_type` varchar(50) NOT NULL,
  `actor_id` varchar(100) NOT NULL,
  `target_type` varchar(50) NOT NULL,
  `target_id` varchar(100) NOT NULL,
  `metadata` text NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `permission_audit_log_target_index` (`target_type`, `target_id`),
  KEY `permission_audit_log_action_index` (`action`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
