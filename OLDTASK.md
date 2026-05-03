# Old Tasks & Changelog Archive

---

## Comet Pre-1.0 Changelog (`changelog.md`)

### 31/03/2015
* Bot Mimic AI (If the bot owner says something, the bot will echo)
* Spawn bots using roomaction

### 03/04/2015
* Work on group forums

### 05/04/2015
* Fixed a problem where you would not be able to sit on an item if it's stacked on top of an item that you can't sit on.

### 06/04/2015
* Rollers will now only roll entities if the player is only standing on the roller, it will not roll them if they're standing on a piece of furniture.
* Rollers will only roll items if they can stack.

### 08/04/2015
* Fixed disconnection with clicking searched items in catalog
* New method of handling incoming packets (It's configurable right now but should improve performance)
* Bots and pets becoming idle has been removed.

### 09/04/2015
* Fixed room authentication issue where players could perform steps to enter a room, which is locked, without permission.
* Vastly improve item performance, allowing for smooth gameplay in even the most complex rooms.

### 10/04/2015
* Rollers: Alter the default speed
* Rollers: Process items before entities
* Allow support for separate path finding for room entities and items.
* Configurable timeout for updating player figure

### 11/04/2015
* Load friend requests only when needed (Takes some stress away from initial login process)
* Fix rare problem where room actors would not be visible in room
* Show badges on profiles whether the player is online or not
* Group administrators can now accept membership requests

### 12/04/2015
* Groups: When membership is accepted, it will now instantly show in profiles, no need to reload client.
* Improve performance of the processing of room actors
* Re-introduce the idle timer, now if players haven't sent a request to the server in a while, they'll be disconnected.
* You can now give a hand item to all players in the room via the roomaction command (`:roomaction handitem <item ID>`)

### 14/04/2015
* Limit bots per room (Configurable)

### 15/04/2015
* Asynchronous incoming packet handling is now segregated from any other thread pools.

### 16/04/2015
* wf_act_chase is now working as intended.
* New item pathfinder implementation
* Disabled football temporarily

### 17/04/2015
* Restructure project into modules
* Implement a Module API
* Asynchronous module event execution
* Fixed: When a player is respected, the count is not updated in the database.

### 18/04/2015
* Threading changes
* Changes to room actor processing
* Move the client-side friend list limit to config (`comet.game.messenger.maxFriends`)

### 22/04/2015
* Enable TCP_NODELAY for client connections
* Configurable idle timer (Cleaning up idle connections)
* wf_cnd_match_snapshot now works as intended.
* wf_cnd_not_match_snap now works as intended.
* Update entity grid when bot is placed.
* Update entity grid when pet is placed.
* Remove the ability to modify an active Banzai timer using wf_act_match_to_sshot

### 23/04/2015
* Fix item movement animation with wf_act_chase

### 24/04/2015
* Async command support (for heavy commands such as massbadge, masscoins etc.)
* Ability to enable and disable several commands in your rooms (Example: Push, Pull)
* Fixed issue where sometimes where the player would appear online even when "hideOnline" is '1'.
* Fixed client-side cloning with following friend in same room.

### 25/04/2015
* No longer possible for rooms to have multiple groups.
* Start initial work on sound machine.

### 26/04/2015
* More work on sound machine-related stuff
* Fix a bug where a session would not correctly be disposed if the user logs in from 2 locations.

### 27/04/2015
* Created a timer interaction which can be interacted with via wired.

### 28/04/2015
* Work on modules

### 29/04/2015
* Lots more module work

### 30/04/2015
* Soundmachine/Trax complete

### 05/05/2015
* Trax bug fixes
* Bug with sending a limited edition item as a gift causing a client crash fixed.
* Commands are no longer case-sensitive.
* Badges given via :roombadge are now saved to the database (Oops!)
* Strict filter updates

### 07/05/2015
* Fixed walking interruptions
* Walk-on trigger is now only triggered if the item is on top.

### 09/05/2015
* New player data caching system
* Moved some of the more time consuming tasks to other threads (Player login, room loading and catalog purchasing)

### 10/05/2015
* Optimizations and stability improvements
* Lots of work on manager (Over the past week).
* String filter updates

### 11/05/2015
* :empty bots command will now update the inventory in-game.

### 12/05/2015
* Re-enabled football (new system)
* Roller fixes
* Standing on the same tile as another player is now fixed.

### 18/05/2015
* Fixed bug with following/summon which would not allow you enter the room (Password/Doorbell)
* Fixed dice rigging (stacking dice inside each other)
* Quests
* Messenger chats can now be logged.
* You can now redeem diamond furniture.

### 19/05/2015
* Support for fork-join event execution.
* HikariCP database connection pooling (Configurable BoneCP or HikariCP.. for testing)
* Fixed wired timer
* Fixed kick/ban from rooms

### 20/05/2015
* Messenger invites are now filtered
* Inventory limits

### 21/05/2015
* Fix teleport walking override glitch when entering another room before the player is teleported.

### 22/05/2015
* Optimizations with inventory and room item loading
* Optimizations with friends list.

### 23/05/2015
* Minor optimizations to group membership list
* Fix modifying the settings of a room you're not currently in
* Ability to make certain ranks (without mod tool) un-ignorable.

### 31/05/2015
* Lots of work on achievements
* Improvements with unseen inventory items (Bots, pets and badges now use this system too)
* Fixed bug with limited edition items not working properly on first purchase.

### 12/06/2015
* Catalog bundles

### 15/06/2015
* Camera
* Player rewards are now processed individually, allowing for configurable time, faster reward processing and reliability.
* Fixed random disconnection bug caused by invalid cached user data
* Fixed bug with catalog causing disconnections
* More player data caching

### 17/06/2015
* IP banning a player will now also disconnect any other player using the same IP address

### 30/06/2015
* Mimic AI additions (speech commands and more)

### 02/07/2015
* Fixed issues with football

### 03/07/2015
* Fixed issue causing items not to be saved properly when used by wf_act_match_to_sshot
* Fixed issue with buying catalog items via search
* Fixed player ID not being saved with messenger chatlog

### 07/07/2015
* Lots of work on achievements
* Bug fix with catalog purchasing
* Commands are now logged

### 12/07/2015
* Fixed searching "owner:" causing an error in the navigator.
* Added room idle time to the server config (`comet.room.idleTimeMinutes`)

### 13/07/2015
* Lots of work on achievements

### 14/07/2015
* Achievements is now ready to go live (Not all achievements added but a big chunk.)
* Lay & Transform commands can now be disabled via :disablecommand

### 15/07/2015
* Fixed bug: Sitting on a chair which is stacked on a "adjustable_height" item will have issues
* Fixed issue with badges not giving correct badge (Users would get the level+1 badge, not the correct level)

### 18/07/2015
* Banzai games can now be ended by picking up the timer
* Wired items will now show the animation

### 19/07/2015
* Fixed issue with the HTTP API causing player data to not reload correctly.

### 20/07/2015
* Fixed issue with permissions related to room settings.
* Fixed issue with pathfinding in rooms with stairs and rugs.

### 24/07/2015
* Idle players are no longer kicked from their own rooms.
* allow_trade in player settings now works as intended.

### 28/07/2015
* Added Highscore classic "all time" scoreboard.

### 30/07/2015
* New rank permissions system
* Fixed team effects removing when player walks off a the item which triggered the join effect.
* Fixed wf_act_toggle_state not allowing you to set a delay.
* Fixed the "One furni or all furni" option in wf_cnd_has_furni_on

### 31/07/2015
* Fixed room auth issue causing players who are teleporting to enter a locked room.

### 04/08/2015
* Group Forums
* Badge displays will now show the creation date & username.

### 05/08/2015
* Room queue & spectator mode

### 07/08/2015
* Made some changes to trade that should improve reliability.

### 08/08/2015
* Fixed some small issues to gifts, they should be more secure now.

---

## 1.0.1 Bug Tracker (`1.0.1.md`)

### TODO
- Create window of Room don't disappear after create the room
- Some wireds doesn't work
- If u follow a user on a room with doorbell appears all black
- Floor Editor is bugged
- Horse things
- Reload button on Navigator don't appear
- If u be a normal user u can't erase your room
- Users banned of room don't appear on "banned list" in room settings
- If have lots of furnis on Room, some of them don't load
- Group rights doesn't work properly
- Bots return to original configurations if u out of room or unload him

### DONE (24/01/2015)
- If u put any wired on a "One Way Gate" u can pass though him without her being facing toward you
- If u ban a person of room, he will not receive the right warning
- Unstackable items will not move on rollers
- If u apply a wallpaper and floor thing, if u out of room they return to original state and to inventory
- Some times gives an alert "Pet is not allowed in room" and disconnect the person who receive this alert
- Some rooms you just can't enter, shows everything black
- When buy a badge, the user don't receive the badge (if user buy need enter again on Hotel)
- Staff picked rooms
- Public rooms doesn't show
- height_adjustable don't update properly and doesn't works fine with floor editor
- Football still bugged
- Fixed wf_act_match_to_sshot
- I can't put stickers on wall
- Delete group
- Wired rewards are now persistent
- Some times all furnis of room is disappearing
- We can't set a "Homeroom"
- ":reload navigator" doesn't work for categories
- Promote your Room doesn't work
- Mute everyone on Room Settings doesn't work
- Coins don't exchange
- Reception images don't appear
- Trade is not working (And DC)
- If you update some change of Group on quarter of Group need out and enter again on room to see the changes
- Teleport doesn't work
- ADS Furni, when on Room gives DC
- When you open the GIFT, he return to your inventory
- Can't apply wallpaper and floor
- Moodlight doesn't work
- If you in of room and click to enter on it again, you can't enter

---

## 2016 TODO (`TODO_2016.md`)

### Features
- Football
- New Wired (Highscores etc.)
- Kickwars
- Group Chats (custom feature)
- Room thumbnails
- Marketplace (for special furni only)
- Ecotron (special presents by entering special items)
- Pets eating, drinking, playing & training
- Stack Tile command so you don't need a stack tile to build your room

### Current Bugs
- Sometimes if you click on another user you random walk to some random place

---

## 2017 Changelog (`2017.md`)

New year, new Comet!

### 06/01/2017
* Optimized isBanned & isMuted checks in rooms
* Room bans are now persistent
* Wired rewards are now working perfectly

### 07/01/2017
* Fixed inventory issue with trading, new items now show properly!
* Roller issues have been fixed

### 08/01/2017
* Messenger flood issues are now resolved (Players not receiving their friends messages etc. is now sorted)

### 09/01/2017
* Mod tools "User Kick" feature now relys on the command "roomKickable".
* Command permissions can now be overriden per-user (Enable & disable any command for any player!)

### 11/01/2017
* wf_act_flee wired added
* Group message removals etc now show the remover
* Wired items will always be reset when picked up
* Optimisations have been made to wired item saving
* Stacking items from the inventory onto items which shouldn't be stackable is now fixed.

---

## Comet 2 Release Notes (`_`)

### Features Completed
- Freeze 100%
- In-game room word filter 100%
- Update to PRODUCTION-201705151314-310198720
- Pet breeding 100%
- Pets eat food
- Pets sleep
- Pet pterodactyl & velociraptor eggs
- Horse jump
- Favourite rooms
- Football 100%
- Wired: wf_act_move_to_dir
- Wired: wf_cnd_stuff_is / wf_cnd_not_stuff_is
- Purchasable clothing 100%
- Paginated inventory packet (basically: infinite sized inventory without crash)
- Command :roomoption
- Command :emptybots
- Command :emptypets
- Command :mutebots
- Command :mutepets
- Rank 4 or above can now pass through group gates
- Wired bug: Teleport/Step On recursion fixed
- Pets now gain experience and level up
- Pets now lose energy and must sleep to continue playing/training
- Pets now gain hunger and must eat to continue playing/training
- Pets now display their correct age
- Items with "step-on/off" effects (e.g water patches) now function as intended
- Freeze shields are now functional
- Scratching pets from a distance is now more reliable
- Banzai teleporters are now fixed
- Entity height is now updated when furni is moved from beneath them
- Wired: wf_trg_collision is fixed
- Pets increase in happiness when scratched

### SQL Migrations
```sql
ALTER TABLE `server_configuration`
    ADD COLUMN `room_wired_max_effects` INT(11) NOT NULL DEFAULT '10' AFTER `group_chat_enabled`;

ALTER TABLE `player_settings`
    ADD COLUMN `ignore_events` ENUM('1','0') NULL DEFAULT '0' AFTER `navigator_show_searches`;

CREATE TABLE IF NOT EXISTS `pet_breeds` (
  `pet_type` int(11) NOT NULL DEFAULT '1',
  `pallet_id` int(11) NOT NULL DEFAULT '0',
  `level` enum('EPIC','RARE','UNCOMMON','COMMON') DEFAULT 'COMMON',
  PRIMARY KEY (`pet_type`,`pallet_id`),
  KEY `pet_type` (`pet_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE `pet_data`
    ADD COLUMN `hunger` INT(11) NOT NULL DEFAULT '0';
```

---

## Feature Wishlist (`todo.md`)

### Features
* SWF update
* ~~Achievements~~
* Rewrite teleports
* ~~Group Forum~~
* More Wired: wf_act_flee, Scoreboard, wf_blob & wf_blob2, wf_act_move_to_dir
* Freeze game
* Spy bot (Displays visitors in your room while you were away)
* Pets (Basics done — horses and monster plants, breeding)
* Helper Tool
* Talents
* ~~Room queue/spectator~~
* Habbo palooza (Plot renting)
* Habbo Club
* Builders Club
* Targeted offers (The popup showing an offer, which expires after x days.)
* Football (100%)
* Room bundles

### Bugs
* Floor/wallpaper gifting
* ~~Sitting on a chair which is stacked on a "adjustable_height" item will have issues~~
* More reliable player disposal (Including entities tied to this player)
* Fix all logged exceptions

### Improvements
* Smarter flood protection
* Hard limit on inventory (Do not allow them to add more items until they remove some.)
* ~~Player data cache support~~
* More queued database writes
* ~~New rank system~~
* Expand on the module API
* Allow wf_act_give_reward to remove rewards (such as coins/diamonds)
* Restructure server_config table
* Check potential issues with trading

### Commands
* Reload playlist
* More fun commands
* ~~Room bot management commands~~
* Command to see players with the same IP
* Freeze entity command
