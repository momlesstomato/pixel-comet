package com.cometproject.server.storage.jooq.repositories;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.storage.jooq.IJooqDslProvider;
import com.cometproject.server.storage.jooq.JooqRepository;
import com.cometproject.storage.api.data.players.PlayerAvatarData;
import com.cometproject.storage.api.repositories.IPlayerRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Table;
import org.jooq.exception.DataAccessException;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * JOOQ-backed implementation of player account storage.
 */
@Singleton
public final class JooqPlayerRepository extends JooqRepository implements IPlayerRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(JooqPlayerRepository.class);

    private static final Table<Record> PLAYERS = DSL.table(DSL.name("players"));
    private static final Table<Record> PLAYER_SETTINGS = DSL.table(DSL.name("player_settings"));
    private static final Table<Record> PLAYER_STATS = DSL.table(DSL.name("player_stats"));

    private static final Field<Integer> PLAYERS_ID = integerField("players", "id");
    private static final Field<String> PLAYERS_USERNAME = stringField("players", "username");
    private static final Field<String> PLAYERS_FIGURE = stringField("players", "figure");
    private static final Field<String> PLAYERS_MOTTO = stringField("players", "motto");
    private static final Field<String> PLAYERS_GENDER = stringField("players", "gender");
    private static final Field<Integer> SETTINGS_PLAYER_ID = integerField("player_settings", "player_id");
    private static final Field<Integer> STATS_PLAYER_ID = integerField("player_stats", "player_id");

    /**
     * Creates the repository with the DSL provider used for query execution.
     *
     * @param dslProvider the configured JOOQ DSL provider.
     */
    @Inject
    public JooqPlayerRepository(final IJooqDslProvider dslProvider) {
        super(dslProvider);
    }

    /**
     * Loads the complete player aggregate needed by the login pipeline.
     *
     * @param playerId the database identifier resolved from the SSO ticket.
     * @param consumer the callback that receives the loaded player; it is not called when no
     *                 player exists.
     */
    @Override
    public void findById(final int playerId, final Consumer<IPlayer> consumer) {
        try {
            final ResultSet resultSet = this.dsl()
                    .select(loginFields())
                    .from(PLAYERS)
                    .join(PLAYER_SETTINGS).on(SETTINGS_PLAYER_ID.eq(PLAYERS_ID))
                    .join(PLAYER_STATS).on(STATS_PLAYER_ID.eq(PLAYERS_ID))
                    .where(PLAYERS_ID.eq(playerId))
                    .limit(1)
                    .fetch()
                    .intoResultSet();

            if (resultSet.next()) {
                consumer.accept(new Player(resultSet, false));
            }
        } catch (DataAccessException | SQLException exception) {
            LOGGER.error("Failed to load player {}", playerId, exception);
        }
    }

    /**
     * Loads mutable account data without session-bound components.
     *
     * @param playerId the database identifier of the player.
     * @param consumer the callback that receives the loaded player data; it is not called when no
     *                 player exists.
     */
    @Override
    public void findDataById(final int playerId, final Consumer<IPlayerData> consumer) {
        this.dsl()
                .select(dataFields())
                .from(PLAYERS)
                .where(PLAYERS_ID.eq(playerId))
                .limit(1)
                .fetchOptional()
                .map(PlayerDataMapper::fromPlayerRecord)
                .ifPresent(consumer);
    }

    /**
     * Loads the lightweight avatar view used by profile and group summaries.
     *
     * @param playerId the database identifier of the player.
     * @param mode     the requested avatar projection, following {@link PlayerAvatar} constants.
     * @param consumer the callback that receives the avatar; it is not called when no player
     *                 exists.
     */
    @Override
    public void findAvatarById(final int playerId, final byte mode, final Consumer<PlayerAvatar> consumer) {
        final Optional<? extends Record> avatarRecord = this.dsl()
                .select(PLAYERS_USERNAME, PLAYERS_FIGURE, PLAYERS_GENDER, PLAYERS_MOTTO)
                .from(PLAYERS)
                .where(PLAYERS_ID.eq(playerId))
                .limit(1)
                .fetchOptional();

        avatarRecord.ifPresent(record -> consumer.accept(new PlayerAvatarData(
                playerId,
                record.get(PLAYERS_USERNAME),
                record.get(PLAYERS_FIGURE),
                record.get(PLAYERS_MOTTO),
                record.get(PLAYERS_GENDER))));
    }

    /**
     * Persists the mutable player account fields that are flushed during normal gameplay.
     *
     * @param data the player data snapshot to persist.
     */
    @Override
    public void save(final IPlayerData data) {
        this.dsl()
                .update(PLAYERS)
                .set(stringField("players", "username"), data.getUsername())
                .set(stringField("players", "motto"), data.getMotto())
                .set(stringField("players", "figure"), data.getFigure())
                .set(integerField("players", "credits"), data.getCredits())
                .set(integerField("players", "vip_points"), data.getVipPoints())
                .set(stringField("players", "gender"), data.getGender())
                .set(integerField("players", "favourite_group"), data.getFavouriteGroup())
                .set(integerField("players", "activity_points"), data.getActivityPoints())
                .set(integerField("players", "seasonal_points"), data.getSeasonalPoints())
                .set(integerField("players", "quest_id"), data.getQuestId())
                .set(integerField("players", "achievement_points"), data.getAchievementPoints())
                .set(stringField("players", "name_colour"), data.getNameColour())
                .set(stringField("players", "tag"), data.getTag())
                .set(stringField("players", "job"), data.getJob())
                .set(integerField("players", "black_money"), data.getBlackMoney())
                .where(PLAYERS_ID.eq(data.getId()))
                .execute();
    }

    /**
     * Updates a player's last-online timestamp and optional last known IP address.
     *
     * @param playerId  the database identifier of the player.
     * @param ipAddress the last known IP address, or an empty value when unavailable.
     */
    @Override
    public void updateLastOnline(final int playerId, final String ipAddress) {
        this.dsl()
                .update(PLAYERS)
                .set(integerField("players", "last_online"), Math.toIntExact(Instant.now().getEpochSecond()))
                .set(stringField("players", "last_ip"), ipAddress == null ? "" : ipAddress)
                .where(PLAYERS_ID.eq(playerId))
                .execute();
    }

    /**
     * Updates a player's online flag without changing other account fields.
     *
     * @param playerId the database identifier of the player.
     * @param online   whether the player should be marked online.
     */
    @Override
    public void updateOnlineStatus(final int playerId, final boolean online) {
        this.dsl()
                .update(PLAYERS)
                .set(stringField("players", "online"), online ? "1" : "0")
                .where(PLAYERS_ID.eq(playerId))
                .execute();
    }

    /**
     * Clears stale online flags left behind by an unclean server shutdown.
     */
    @Override
    public void resetOnlineStatus() {
        this.dsl()
                .update(PLAYERS)
                .set(stringField("players", "online"), "0")
                .where(stringField("players", "online").eq("1"))
                .execute();
    }

    private static SelectFieldOrAsterisk[] loginFields() {
        return new SelectFieldOrAsterisk[]{
                integerField("players", "id").as("playerId"),
                stringField("players", "username").as("playerData_username"),
                stringField("players", "figure").as("playerData_figure"),
                stringField("players", "motto").as("playerData_motto"),
                integerField("players", "credits").as("playerData_credits"),
                integerField("players", "vip_points").as("playerData_vipPoints"),
                integerField("players", "rank").as("playerData_rank"),
                stringField("players", "last_ip").as("playerData_lastIp"),
                integerField("players", "seasonal_points").as("playerData_seasonalPoints"),
                integerField("players", "black_money").as("playerData_blackMoney"),
                stringField("players", "vip").as("playerData_vip"),
                stringField("players", "gender").as("playerData_gender"),
                integerField("players", "last_online").as("playerData_lastOnline"),
                integerField("players", "reg_timestamp").as("playerData_regTimestamp"),
                stringField("players", "reg_date").as("playerData_regDate"),
                integerField("players", "favourite_group").as("playerData_favouriteGroup"),
                integerField("players", "achievement_points").as("playerData_achievementPoints"),
                stringField("players", "email").as("playerData_email"),
                integerField("players", "activity_points").as("playerData_activityPoints"),
                integerField("players", "quest_id").as("playerData_questId"),
                integerField("players", "time_muted").as("playerData_timeMuted"),
                stringField("players", "name_colour").as("playerData_nameColour"),
                stringField("players", "tag").as("playerData_tag"),
                stringField("players", "job").as("playerData_job"),
                integerField("players", "view_points").as("playerData_viewPoints"),
                stringField("player_settings", "volume").as("playerSettings_volume"),
                integerField("player_settings", "home_room").as("playerSettings_homeRoom"),
                stringField("player_settings", "hide_online").as("playerSettings_hideOnline"),
                integerField("player_settings", "nux").as("playerSettings_nux"),
                stringField("player_settings", "hide_inroom").as("playerSettings_hideInRoom"),
                stringField("player_settings", "personal_pin").as("playerSettings_personalPin"),
                stringField("player_settings", "ignore_invites").as("playerSettings_ignoreInvites"),
                stringField("player_settings", "event_type").as("playerSettings_eventType"),
                integerField("player_settings", "royale_xp").as("playerSettings_royaleXP"),
                stringField("player_settings", "allow_friend_requests").as("playerSettings_allowFriendRequests"),
                stringField("player_settings", "allow_trade").as("playerSettings_allowTrade"),
                stringField("player_settings", "allow_follow").as("playerSettings_allowFollow"),
                stringField("player_settings", "allow_mimic").as("playerSettings_allowMimic"),
                stringField("player_settings", "wardrobe").as("playerSettings_wardrobe"),
                stringField("player_settings", "playlist").as("playerSettings_playlist"),
                stringField("player_settings", "chat_oldstyle").as("playerSettings_useOldChat"),
                integerField("player_settings", "navigator_x").as("playerSettings_navigatorX"),
                integerField("player_settings", "navigator_y").as("playerSettings_navigatorY"),
                integerField("player_settings", "navigator_height").as("playerSettings_navigatorHeight"),
                integerField("player_settings", "navigator_width").as("playerSettings_navigatorWidth"),
                stringField("player_settings", "navigator_show_searches").as("playerSettings_navigatorShowSearches"),
                stringField("player_settings", "ignore_events").as("playerSettings_ignoreEvents"),
                stringField("player_settings", "disable_whisper").as("playerSettings_disableWhisper"),
                stringField("player_settings", "send_login_notif").as("playerSettings_sendLoginNotif"),
                stringField("player_settings", "personalstaff").as("playerSettings_personalstaff"),
                integerField("player_settings", "bubble_id").as("playerSettings_bubbleId"),
                integerField("player_settings", "room_tool_state").as("playerSettings_roomToolState"),
                stringField("player_settings", "camera_follow").as("playerSettings_roomCameraFollow"),
                stringField("player_settings", "claimed_goal").as("playerSettings_claimedGoal"),
                integerField("player_stats", "level").as("playerStats_level"),
                integerField("player_stats", "experience_points").as("playerStats_experiencePoints"),
                integerField("player_stats", "daily_respects").as("playerStats_dailyRespects"),
                integerField("player_stats", "daily_rolls").as("playerStats_dailyRolls"),
                integerField("player_stats", "fireworks").as("playerStats_fireworks"),
                integerField("player_stats", "total_respect_points").as("playerStats_totalRespectPoints"),
                integerField("player_stats", "help_tickets").as("playerStats_helpTickets"),
                integerField("player_stats", "help_tickets_abusive").as("playerStats_helpTicketsAbusive"),
                integerField("player_stats", "cautions").as("playerStats_cautions"),
                integerField("player_stats", "bans").as("playerStats_bans"),
                integerField("player_stats", "daily_scratches").as("playerStats_scratches"),
                longField("player_stats", "trade_lock").as("playerStats_tradeLock")
        };
    }

    private static SelectFieldOrAsterisk[] dataFields() {
        return new SelectFieldOrAsterisk[]{
                integerField("players", "id"),
                stringField("players", "username"),
                stringField("players", "motto"),
                stringField("players", "figure"),
                stringField("players", "gender"),
                stringField("players", "email"),
                integerField("players", "rank"),
                integerField("players", "credits"),
                integerField("players", "vip_points"),
                integerField("players", "activity_points"),
                integerField("players", "seasonal_points"),
                integerField("players", "black_money"),
                stringField("players", "reg_date"),
                integerField("players", "last_online"),
                stringField("players", "vip"),
                stringField("players", "name_colour"),
                integerField("players", "achievement_points"),
                integerField("players", "reg_timestamp"),
                integerField("players", "favourite_group"),
                stringField("players", "last_ip"),
                integerField("players", "quest_id"),
                integerField("players", "time_muted"),
                stringField("players", "tag"),
                stringField("players", "job"),
                integerField("players", "view_points")
        };
    }

    private static Field<Integer> integerField(final String table, final String field) {
        return DSL.field(DSL.name(table, field), Integer.class);
    }

    private static Field<Long> longField(final String table, final String field) {
        return DSL.field(DSL.name(table, field), Long.class);
    }

    private static Field<String> stringField(final String table, final String field) {
        return DSL.field(DSL.name(table, field), String.class);
    }
}
