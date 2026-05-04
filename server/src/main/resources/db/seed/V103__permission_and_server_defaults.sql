-- Generated from SQL.sql. Permission and server defaults.

INSERT IGNORE INTO `permission_ranks` (`fuse`, `min_rank`, `note`) VALUES
	('mod_tool', 6, 'The minimum rank of the user that can use the moderation tool.'),
	('room_enter_full', 3, 'The minimum rank of the user that can enter full rooms.'),
	('room_unkickable', 6, 'The minimum rank of the user than can\'t be kicked / banned from a room.'),
	('room_enter_locked', 6, 'The minimum rank of the user that can enter locked rooms.'),
	('about_detailed', 7, 'The minimum rank of the user that can view full info on about dialogue.'),
	('user_unbannable', 7, 'The minimum rank of the user who is unbannable.'),
	('room_staff_pick', 7, 'The minimum rank of the user able to "Add To staff picked rooms"'),
	('room_full_control', 7, 'The minimum rank of the user who has full access to all rooms.'),
	('bypass_filter', 9, 'The minimum rank of the user that can bypass the word filter.'),
	('undisconnectable', 7, 'The minimum rank of user that cannot be disconnected by another staff member.'),
	('bypass_flood', 7, 'The minimum rank of the user that can bypass the flood.'),
	('staff_chat', 6, 'The minimum rank of the user that can access staff chat.'),
	('room_see_whisper', 6, 'The minimum rank of the user who can see all whispers in the room.'),
	('about_stats', 7, 'The minimum rank of the user that can view online stats in the about command (Current online record & online record)');

INSERT IGNORE INTO `server_config` (`key`, `value`) VALUES
	('hotel.home.room', '19'),
	('hotel.motd.enabled', '0');

