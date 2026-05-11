-- Adds the `items` table which stores player inventory and room furniture placements.
-- The table was absent from earlier migration snapshots.

CREATE TABLE IF NOT EXISTS `items` (
  `id`         int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id`    int(10)          NOT NULL DEFAULT 0,
  `room_id`    int(10) unsigned NOT NULL DEFAULT 0,
  `base_item`  int(10) unsigned NOT NULL DEFAULT 0,
  `extra_data` text             NOT NULL,
  `x`          int(11)          NOT NULL DEFAULT 0,
  `y`          int(11)          NOT NULL DEFAULT 0,
  `z`          double           NOT NULL DEFAULT 0,
  `rot`        int(11)          NOT NULL DEFAULT 0,
  `wall_pos`   varchar(100)     NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_items_user_room` (`user_id`, `room_id`),
  KEY `idx_items_room`      (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;
