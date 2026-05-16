package com.cometproject.server.storage.queries.player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.game.players.data.components.navigator.ISavedSearch;
import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.players.PlayerManager;
import com.cometproject.server.game.players.components.types.navigator.SavedSearch;
import com.cometproject.server.game.players.data.PlayerAvatarData;
import com.cometproject.server.game.players.data.PlayerBattlePassInfo;
import com.cometproject.server.game.players.data.PlayerData;
import com.cometproject.server.game.players.types.MisteryComponent;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.players.types.PlayerSettings;
import com.cometproject.server.game.players.types.PlayerStatistics;
import com.cometproject.server.storage.SqlHelper;
import com.cometproject.server.storage.cache.CacheManager;
import com.cometproject.server.utilities.collections.ConcurrentHashSet;

/**
 * Describes player dao behavior for the storage subsystem.
 */
public class PlayerDao {
    /**
     * Returns the player for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    @Deprecated
    public static Player getPlayer(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT p.id as playerId, p.username AS playerData_username, p.figure AS playerData_figure, p.motto AS playerData_motto, p.credits AS playerData_credits, 0 AS playerData_currency5, p.rank AS playerData_rank, p.last_ip AS playerData_lastIp, 0 AS playerData_currency103, 0 AS playerData_currency105, " +
                    " p.vip AS playerData_vip, p.gender AS playerData_gender, p.last_online AS playerData_lastOnline, p.reg_timestamp AS playerData_regTimestamp, p.reg_date AS playerData_regDate, p.favourite_group AS playerData_favouriteGroup, p.achievement_points AS playerData_achievementPoints," +
                    " p.email AS playerData_email, 0 AS playerData_currency0, p.quest_id AS playerData_questId, p.time_muted AS playerData_timeMuted, p.name_colour AS playerData_nameColour, p.tag AS playerData_tag, p.job AS playerData_job, p.view_points AS playerData_viewPoints, \n" +
                    "  pSettings.volume AS playerSettings_volume, pSettings.home_room AS playerSettings_homeRoom, pSettings.hide_online AS playerSettings_hideOnline, pSettings.nux AS playerSettings_nux, pSettings.hide_inroom AS playerSettings_hideInRoom, pSettings.personal_pin AS playerSettings_personalPin, pSettings.ignore_invites AS playerSettings_ignoreInvites, pSettings.event_type AS playerSettings_eventType, pSettings.royale_xp AS playerSettings_royaleXP, \n" +
                    "   pSettings.allow_friend_requests AS playerSettings_allowFriendRequests, pSettings.allow_trade AS playerSettings_allowTrade, pSettings.allow_follow AS playerSettings_allowFollow, pSettings.allow_mimic AS playerSettings_allowMimic, pSettings.wardrobe AS playerSettings_wardrobe, pSettings.playlist AS playerSettings_playlist, pSettings.chat_oldstyle AS playerSettings_useOldChat,\n" +
                    " pSettings.navigator_x AS playerSettings_navigatorX, pSettings.navigator_y AS playerSettings_navigatorY, pSettings.navigator_height AS playerSettings_navigatorHeight, pSettings.navigator_width AS playerSettings_navigatorWidth, pSettings.navigator_show_searches AS playerSettings_navigatorShowSearches, pSettings.ignore_events AS playerSettings_ignoreEvents, pSettings.disable_whisper AS playerSettings_disableWhisper, pSettings.send_login_notif AS playerSettings_sendLoginNotif, pSettings.personalstaff AS playerSettings_personalstaff, pSettings.bubble_id AS playerSettings_bubbleId, pSettings.room_tool_state AS playerSettings_roomToolState, pSettings.camera_follow AS playerSettings_roomCameraFollow, pSettings.claimed_goal AS playerSettings_claimedGoal, " +
                    " pStats.level AS playerStats_level, pStats.experience_points AS playerStats_experiencePoints, pStats.daily_respects AS playerStats_dailyRespects, pStats.daily_rolls AS playerStats_dailyRolls, pStats.fireworks AS playerStats_fireworks, pStats.total_respect_points AS playerStats_totalRespectPoints, pStats.help_tickets AS playerStats_helpTickets, pStats.help_tickets_abusive AS playerStats_helpTicketsAbusive, pStats.cautions AS playerStats_cautions, pStats.bans AS playerStats_bans, pStats.daily_scratches AS playerStats_scratches, pStats.trade_lock AS playerStats_tradeLock \n" +
                    "FROM players p\n" +
                    " JOIN player_settings pSettings ON pSettings.player_id = p.id \n" +
                    " JOIN player_stats pStats ON pStats.player_id = p.id\n" +
                    "\n" +
                        "WHERE p.id = ?", sqlConnection);
                    preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return new Player(resultSet, false);
            }

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return null;
    }

    /**
     * Returns the user look for this storage contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static String getUserLook(String username) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("SELECT `figure` FROM `players` WHERE `username` = ?;", sqlConnection);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                return resultSet.getString("figure");
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
        return null;
    }

    /**
     * Returns the user gender for this storage contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static String getUserGender(String username) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("SELECT `gender` FROM `players` WHERE `username` = ?;", sqlConnection);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                return resultSet.getString("gender");
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
        return null;
    }

    /**
     * Updates NUX status for this storage contract.
     *
     * @param s S supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void updateNuxStatus(int s, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET nux = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, s);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists claimed goal for this storage contract.
     *
     * @param goal Goal supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveClaimedGoal(boolean goal, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET claimed_goal = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, goal ? "1" : "0");
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates subscription for this storage contract.
     *
     * @param v V supplied by the caller.
     * @param i I supplied by the caller.
     */
    public static void updateSubscription(boolean v, int i) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE players SET vip = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, v ? "1" : "0");
            preparedStatement.setInt(2, i);


            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the player battle plass info for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static PlayerBattlePassInfo getPlayerBattlePlassInfo(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("SELECT * FROM battlepass_active WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, playerId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return new PlayerBattlePassInfo(resultSet.getInt("level"), resultSet.getInt("exp"));
            }

            return null;
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
        return null;
    }

    /**
     * Executes player battle pass load missions completed for this storage contract.
     *
     * @param battlePass Battle pass supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public static void PlayerBattlePassLoadMissionsCompleted(PlayerBattlePassInfo battlePass, int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("SELECT * FROM battlepass_completed WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, playerId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                battlePass.completedMissions.add(resultSet.getInt("mission"));
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Executes player battle pass create for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     */
    public static void PlayerBattlePassCreate(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("INSERT INTO battlepass_active (player_id, level, exp) VALUES (?, 1, 0)", sqlConnection);
            preparedStatement.setInt(1, playerId);
            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Executes update battle pass for this storage contract.
     *
     * @param battlePass Battle pass supplied by the caller.
     */
    public static void UpdateBattlePass(PlayerBattlePassInfo battlePass) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("UPDATE battlepass_active SET level = ?, exp = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, battlePass.level);
            preparedStatement.setInt(2, battlePass.exp);
            preparedStatement.setInt(3, battlePass.playerId);
            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Executes update view points for this storage contract.
     *
     * @param points Points supplied by the caller.
     * @param id Id supplied by the caller.
     */
    public static void UpdateViewPoints(int points, int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("UPDATE players SET view_points = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, points);
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Executes insert battle pass completed for this storage contract.
     *
     * @param mission Mission supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public static void InsertBattlePassCompleted(int mission, int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("INSERT battlepass_completed (player_id, mission) VALUES (?, ?)", sqlConnection);
            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, mission);
            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the data by username for this storage contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static PlayerData getDataByUsername(String username) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT id, motto, figure, gender, email, rank, credits, 0 AS currency5, 0 AS currency0, 0 AS currency103, 0 AS currency105, reg_date, last_online, vip, achievement_points, reg_timestamp, favourite_group, last_ip, quest_id, time_muted, name_colour, tag, job FROM players WHERE username = ?", sqlConnection);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return new PlayerData(resultSet.getInt("id"), username, resultSet.getString("motto"), resultSet.getString("figure"), resultSet.getString("gender"),
                        resultSet.getString("email") == null ? "" : resultSet.getString("email"), resultSet.getInt("rank"), resultSet.getInt("credits"), resultSet.getInt("currency5"),
                        resultSet.getInt("currency0"), resultSet.getInt("currency103"), resultSet.getInt("currency105"), resultSet.getString("reg_date"), resultSet.getInt("last_online"), resultSet.getString("vip").equals("1"),
                        resultSet.getInt("achievement_points"), resultSet.getInt("reg_timestamp"), resultSet.getInt("favourite_group"), resultSet.getString("last_ip"), resultSet.getInt("quest_id"), resultSet.getInt("time_muted"), resultSet.getString("name_colour"), resultSet.getString("tag"), resultSet.getString("job"), resultSet.getInt("view_points"), null);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return null;
    }

    /**
     * Returns the data by id for this storage contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static PlayerData getDataById(int id) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT id, username, motto, figure, gender, email, rank, credits, 0 AS currency5, 0 AS currency0, 0 AS currency103, 0 AS currency105, reg_date, last_online, vip, name_colour, achievement_points, reg_timestamp, favourite_group, last_ip, quest_id, time_muted, tag, job, view_points FROM players WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return new PlayerData(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("motto"), resultSet.getString("figure"), resultSet.getString("gender"),
                        resultSet.getString("email") == null ? "" : resultSet.getString("email"), resultSet.getInt("rank"), resultSet.getInt("credits"), resultSet.getInt("currency5"),
                        resultSet.getInt("currency0"), resultSet.getInt("currency103"), resultSet.getInt("currency105"), resultSet.getString("reg_date"), resultSet.getInt("last_online"), resultSet.getString("vip").equals("1"),
                        resultSet.getInt("achievement_points"), resultSet.getInt("reg_timestamp"), resultSet.getInt("favourite_group"), resultSet.getString("last_ip"), resultSet.getInt("quest_id"), resultSet.getInt("time_muted"), resultSet.getString("name_colour"), resultSet.getString("tag"), resultSet.getString("job"), resultSet.getInt("view_points"), null);
            }

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return null;
    }

    /**
     * Returns the avatar by id for this storage contract.
     *
     * @param id Id supplied by the caller.
     * @param mode Mode supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static PlayerAvatar getAvatarById(int id, byte mode) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            String query;

            switch (mode) {
                case PlayerAvatar.USERNAME_FIGURE:
                    query = "SELECT username, figure, gender, motto FROM players WHERE id = ?";
                    break;


                default:
                case PlayerAvatar.USERNAME_FIGURE_MOTTO:
                    query = "SELECT username, figure, gender, motto FROM players WHERE id = ?";
                    break;
            }

            preparedStatement = SqlHelper.prepare(query, sqlConnection);

            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final PlayerAvatar playerAvatar = new PlayerAvatarData(id, resultSet.getString("username"), resultSet.getString("figure"), resultSet.getString("gender"), resultSet.getString("motto"));

                if (mode == PlayerAvatar.USERNAME_FIGURE_MOTTO) {
                    playerAvatar.setMotto(resultSet.getString("motto"));
                }

                return playerAvatar;
            }

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return null;
    }

    /**
     * Returns the motto by player id for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static String getMottoByPlayerId(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            String query = "SELECT motto FROM players WHERE id = ?";

            preparedStatement = SqlHelper.prepare(query, sqlConnection);

            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("motto");
            }

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return "";
    }

    /**
     * Returns the settings by id for this storage contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static PlayerSettings getSettingsById(int id) {
        Connection sqlConnection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM player_settings WHERE player_id = ? LIMIT 1;", sqlConnection);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new PlayerSettings(resultSet, false, null);
            } else {
                // close old statement
                SqlHelper.closeSilently(preparedStatement);

                preparedStatement = SqlHelper.prepare("INSERT into player_settings (`player_id`) VALUES(?)", sqlConnection);
                preparedStatement.setInt(1, id);

                SqlHelper.executeStatementSilently(preparedStatement, false);

                return new PlayerSettings();
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return new PlayerSettings();
    }

    /**
     * Returns the mistery by id for this storage contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static MisteryComponent getMisteryById(int id) {
        Connection sqlConnection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM player_mistery WHERE player_id = ? LIMIT 1;", sqlConnection);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new MisteryComponent(resultSet);
            } else {
                SqlHelper.closeSilently(preparedStatement);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return new MisteryComponent(id);
    }

    /**
     * Returns the statistics by id for this storage contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static PlayerStatistics getStatisticsById(int id) {
        if (id < 0) return null; // todo: figure out why this would be negative

        Connection sqlConnection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM player_stats WHERE player_id = ? LIMIT 1;", sqlConnection);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new PlayerStatistics(resultSet, false, null);
            } else {
                SqlHelper.closeSilently(preparedStatement);

                preparedStatement = SqlHelper.prepare("INSERT into player_stats (`player_id`) VALUES(?)", sqlConnection);
                preparedStatement.setInt(1, id);

                SqlHelper.executeStatementSilently(preparedStatement, false);

                return new PlayerStatistics(id);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return new PlayerStatistics(id);
    }

    /**
     * Updates player status for this storage contract.
     *
     * @param player Player participating in the operation.
     * @param online Online supplied by the caller.
     * @param setLastOnline Set last online supplied by the caller.
     */
    public static void updatePlayerStatus(Player player, boolean online, boolean setLastOnline) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE players SET online = ?" + (setLastOnline ? ", last_online = ?, last_ip = ?" : "") + " WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, online ? "1" : "0");

            if (setLastOnline) {
                preparedStatement.setLong(2, Comet.getTime());
                preparedStatement.setString(3, player.getData().getIpAddress());
                preparedStatement.setInt(4, player.getId());
            } else {
                preparedStatement.setInt(2, player.getId());
            }

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the username by player id for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static String getUsernameByPlayerId(int playerId) {
        if (CacheManager.getInstance().exists("players.username." + playerId)) {
            return CacheManager.getInstance().getString("players.username." + playerId);
        }

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `username` FROM players WHERE `id` = ?", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                final String username = resultSet.getString("username");

                CacheManager.getInstance().putString("players.username." + playerId, username);

                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return null;
    }

    /**
     * Returns the id by username for this storage contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getIdByUsername(String username) {
        if (PlayerManager.getInstance().getPlayerIdByUsername(username) != -1)
            return PlayerManager.getInstance().getPlayerIdByUsername(username);

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `id` FROM players WHERE `username` = ?", sqlConnection);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 0;
    }

    /**
     * Returns the rentable id for this storage contract.
     *
     * @param i I supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getRentableId(int i) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `space_id` FROM items_rentable WHERE `user_id` = ?", sqlConnection);
            preparedStatement.setInt(1, i);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("space_id");
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 0;
    }

    /**
     * Returns the renter by id for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static String getRenterById(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = sqlConnection.prepareStatement("SELECT username FROM players WHERE id = ?");
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("username");
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return "UnknownUsername";
    }

    /**
     * Returns the IP address for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static String getIpAddress(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `last_ip` FROM players WHERE `id` = ?", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString("last_ip");
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return "";
    }

    /**
     * Updates player data for this storage contract.
     *
     * @param id Id supplied by the caller.
     * @param username Username supplied by the caller.
     * @param motto Motto supplied by the caller.
     * @param figure Figure supplied by the caller.
     * @param credits Credits supplied by the caller.
     * @param points Points supplied by the caller.
     * @param gender Gender supplied by the caller.
     * @param favouriteGroup Favourite group supplied by the caller.
     * @param activityPoints Activity points supplied by the caller.
     * @param seasonalPoints Seasonal points supplied by the caller.
     * @param blackMoney Black money supplied by the caller.
     * @param questId Quest id supplied by the caller.
     * @param achievementPoints Achievement points supplied by the caller.
     * @param nameColour Name colour supplied by the caller.
     * @param tag Tag supplied by the caller.
     * @param job Job supplied by the caller.
     */
    public static void updatePlayerData(int id, String username, String motto, String figure, int credits, int points, String gender, int favouriteGroup, int activityPoints, int seasonalPoints, int blackMoney, int questId, int achievementPoints, String nameColour, String tag, String job) {
        updatePlayerDataWithoutCurrencies(id, username, motto, figure, credits, gender, favouriteGroup, questId, achievementPoints, nameColour, tag, job);
    }

    /**
     * Persists mutable player data while leaving legacy currency columns untouched.
     *
     * @param id                the player id.
     * @param username          the player username.
     * @param motto             the profile motto.
     * @param figure            the avatar figure.
     * @param gender            the avatar gender.
     * @param favouriteGroup    the favourite group id.
     * @param questId           the active quest id.
     * @param achievementPoints the achievement point total.
     * @param nameColour        the profile name colour.
     * @param tag               the profile tag.
     * @param job               the roleplay job.
     */
    public static void updatePlayerDataWithoutCurrencies(int id, String username, String motto, String figure, int credits, String gender, int favouriteGroup, int questId, int achievementPoints, String nameColour, String tag, String job) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE players SET username = ?, motto = ?, figure = ?, credits = ?, gender = ?, favourite_group = ?, quest_id = ?, achievement_points = ?, name_colour = ?, tag = ?, job = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, motto);
            preparedStatement.setString(3, figure);
            preparedStatement.setInt(4, credits);
            preparedStatement.setString(5, gender);
            preparedStatement.setInt(6, favouriteGroup);
            preparedStatement.setInt(7, questId);
            preparedStatement.setInt(8, achievementPoints);
            preparedStatement.setString(9, nameColour);
            preparedStatement.setString(10, tag);
            preparedStatement.setString(11, job);
            preparedStatement.setInt(12, id);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates player rp for this storage contract.
     *
     * @param id Id supplied by the caller.
     * @param job Job supplied by the caller.
     * @param rank Rank supplied by the caller.
     * @param hunger Hunger supplied by the caller.
     * @param health Health supplied by the caller.
     * @param warnings Warnings supplied by the caller.
     * @param arrests Arrests supplied by the caller.
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     * @param lastRoom Last room supplied by the caller.
     * @param weaponInfo Weapon info supplied by the caller.
     * @param lastRobbery Last robbery supplied by the caller.
     */
    public static void updatePlayerRP(int id, String job, int rank, int hunger, int health, int warnings, int arrests, int x, int y, int lastRoom, String weaponInfo, int lastRobbery) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_roleplay SET job = ?, rank = ?, hunger = ?, health = ?, x = ?, y = ?, last_room = ?, weapon_info = ?, warnings = ?, arrests = ?, last_robbery = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, job);
            preparedStatement.setInt(2, rank);
            preparedStatement.setInt(3, hunger);
            preparedStatement.setInt(4, health);
            preparedStatement.setInt(5, x);
            preparedStatement.setInt(6, y);
            preparedStatement.setInt(7, lastRoom);
            preparedStatement.setString(8, weaponInfo);
            preparedStatement.setInt(9, warnings);
            preparedStatement.setInt(10, arrests);
            preparedStatement.setInt(11, lastRobbery);
            preparedStatement.setInt(12, id);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates player credits for this storage contract.
     *
     * @param id Id supplied by the caller.
     * @param credits Credits supplied by the caller.
     */
    public static void updatePlayerCredits(int id, int credits) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE players SET credits = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, credits);
            preparedStatement.setInt(2, id);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists allow mentions for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param allowance Allowance supplied by the caller.
     */
    public static void saveAllowMentions(int playerId, String allowance) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare(
                    "INSERT IGNORE INTO player_permission_groups (`player_id`, `group_id`, `granted_by`, `reason`) "
                            + "SELECT ?, `id`, 'catalog', 'Catalog allowance purchase' "
                            + "FROM permission_groups WHERE `code` = ?",
                    sqlConnection);

            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, allowance);

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates player statistics for this storage contract.
     *
     * @param playerStatistics Player statistics supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean updatePlayerStatistics(PlayerStatistics playerStatistics) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_stats SET experience_points = ?, total_respect_points = ?, daily_respects = ?, help_tickets = ?, help_tickets_abusive = ?, cautions = ?, bans = ?, daily_scratches = ?, trade_lock = ?, level = ?, fireworks = ?, daily_rolls = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, playerStatistics.getExperiencePoints());
            preparedStatement.setInt(2, playerStatistics.getRespectPoints());
            preparedStatement.setInt(3, playerStatistics.getDailyRespects());
            preparedStatement.setInt(4, playerStatistics.getHelpTickets());
            preparedStatement.setInt(5, playerStatistics.getAbusiveHelpTickets());
            preparedStatement.setInt(6, playerStatistics.getCautions());
            preparedStatement.setInt(7, playerStatistics.getBans());
            preparedStatement.setInt(8, playerStatistics.getScratches());
            preparedStatement.setLong(9, playerStatistics.getTradeLock());
            preparedStatement.setInt(10, playerStatistics.getLevel());
            preparedStatement.setInt(11, playerStatistics.getFireworks());
            preparedStatement.setInt(12, playerStatistics.getDailyRolls());
            preparedStatement.setInt(13, playerStatistics.getPlayerId());

            return preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return false;
    }

    /**
     * Updates fireworks for this storage contract.
     *
     * @param fireworks Fireworks supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void updateFireworks(int fireworks, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_stats SET fireworks = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, fireworks);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates daily rolls for this storage contract.
     *
     * @param amount Amount supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void updateDailyRolls(int amount, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_stats SET daily_rolls = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, amount);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates home room for this storage contract.
     *
     * @param homeRoom Home room supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void updateHomeRoom(int homeRoom, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET home_room = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, homeRoom);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }


    /**
     * Updates event type for this storage contract.
     *
     * @param type Type supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void updateEventType(String type, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET event_type = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, type);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates mystery key for this storage contract.
     *
     * @param key Key supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void updateMysteryKey(String key, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_mistery SET mistery_key = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, key);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates mystery box for this storage contract.
     *
     * @param box Box supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void updateMysteryBox(String box, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_mistery SET mistery_box = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, box);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists wardrobe for this storage contract.
     *
     * @param wardrobeData Wardrobe data supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveWardrobe(String wardrobeData, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET wardrobe = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, wardrobeData);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists volume for this storage contract.
     *
     * @param volumeData Volume data supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveVolume(String volumeData, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET volume = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, volumeData);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists chat style for this storage contract.
     *
     * @param useOldChat Use old chat supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveChatStyle(boolean useOldChat, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET chat_oldstyle = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, useOldChat ? "1" : "0");
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists allow trade for this storage contract.
     *
     * @param allowTrade Allow trade supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveAllowTrade(boolean allowTrade, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET allow_trade = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, allowTrade ? "1" : "0");
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the tags by player id for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static List<String> getTagsByPlayerId(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<String> badges = new ArrayList<>();

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM player_tags WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                badges.add(resultSet.getString("tag"));
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return badges;
    }

    /**
     * Returns the keys by player id for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static List<String> getKeysByPlayerId(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        List<String> badges = new ArrayList<>();

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM player_mistery WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                badges.add(resultSet.getString("tag"));
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return badges;
    }

    /**
     * Persists ignore invitations for this storage contract.
     *
     * @param ignoreInvitations Ignore invitations supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveIgnoreInvitations(boolean ignoreInvitations, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET ignore_invites = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, ignoreInvitations ? "1" : "0");
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists room camera follow for this storage contract.
     *
     * @param r R supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveRoomCameraFollow(boolean r, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET camera_follow = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, r ? "1" : "0");
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists allow friend requests for this storage contract.
     *
     * @param allowFriendRequests Allow friend requests supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveAllowFriendRequests(boolean allowFriendRequests, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET allow_friend_requests = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, allowFriendRequests ? "1" : "0");
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists ignore events for this storage contract.
     *
     * @param ignoreEvents Ignore events supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveIgnoreEvents(boolean ignoreEvents, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET ignore_events = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, ignoreEvents ? "1" : "0");
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists personal staff for this storage contract.
     *
     * @param personalStaff Personal staff supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void savePersonalStaff(boolean personalStaff, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET personalstaff = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setString(1, personalStaff ? "1" : "0");
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists bubble id for this storage contract.
     *
     * @param bubbleId Bubble id supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveBubbleId(int bubbleId, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET bubble_id = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, bubbleId);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists room tool state for this storage contract.
     *
     * @param state State supplied by the caller.
     * @param userId User id supplied by the caller.
     */
    public static void saveRoomToolState(int state, int userId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET room_tool_state = ? WHERE player_id = ?", sqlConnection);
            preparedStatement.setInt(1, state);
            preparedStatement.setInt(2, userId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Executes username is available for this storage contract.
     *
     * @param username Username supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public static boolean usernameIsAvailable(String username) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `id` FROM players WHERE username = ? LIMIT 1", sqlConnection);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return false;
    }

    /**
     * Executes reset home room for this storage contract.
     *
     * @param roomId Room identifier used by the operation.
     */
    public static void resetHomeRoom(int roomId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET home_room = 0 WHERE home_room = ?", sqlConnection);
            preparedStatement.setInt(1, roomId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Executes reset online status for this storage contract.
     */
    public static void resetOnlineStatus() {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE players SET online = '0' WHERE online = '1'", sqlConnection);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates rank for this storage contract.
     *
     * @param rank Rank supplied by the caller.
     * @param username Username supplied by the caller.
     */
    public static void updateRank(int rank, String username) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("UPDATE players SET rank = ?  WHERE username = ?", sqlConnection);
            preparedStatement.setInt(1, rank);
            preparedStatement.setString(2, username);
            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }


    /**
     * Persists batch for this storage contract.
     *
     * @param playerData Player data supplied by the caller.
     */
    public static void saveBatch(Map<Integer, PlayerData> playerData) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE players SET username = ?, motto = ?, figure = ?, credits = ?, gender = ?, favourite_group = ?, quest_id = ?, achievement_points = ? WHERE id = ?;", sqlConnection);

            for (PlayerData playerDataInstance : playerData.values()) {
                preparedStatement.setString(1, playerDataInstance.getUsername());
                preparedStatement.setString(2, playerDataInstance.getMotto());
                preparedStatement.setString(3, playerDataInstance.getFigure());
                preparedStatement.setInt(4, playerDataInstance.getCredits());
                preparedStatement.setString(5, playerDataInstance.getGender());
                preparedStatement.setInt(6, playerDataInstance.getFavouriteGroup());
                preparedStatement.setInt(7, playerDataInstance.getQuestId());
                preparedStatement.setInt(8, playerDataInstance.getAchievementPoints());
                preparedStatement.setInt(9, playerDataInstance.getId());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Executes daily player update for this storage contract.
     *
     * @param dailyRespects Daily respects supplied by the caller.
     * @param dailyScratches Daily scratches supplied by the caller.
     * @param dailyRolls Daily rolls supplied by the caller.
     */
    public static void dailyPlayerUpdate(int dailyRespects, int dailyScratches, int dailyRolls) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_stats SET daily_respects = ?, daily_scratches = ?, daily_rolls = ?;", sqlConnection);

            preparedStatement.setInt(1, dailyRespects);
            preparedStatement.setInt(2, dailyScratches);
            preparedStatement.setInt(3, dailyRolls);

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Adds time mute to this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param timeMuted Time muted supplied by the caller.
     */
    public static void addTimeMute(int playerId, int timeMuted) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = sqlConnection.prepareStatement("UPDATE players SET time_muted = ? WHERE id = ?");
            preparedStatement.setInt(1, timeMuted);
            preparedStatement.setInt(2, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates disable whisper for this storage contract.
     *
     * @param disableWhisper Disable whisper supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public static void updateDisableWhisper(final boolean disableWhisper, int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = sqlConnection.prepareStatement("UPDATE players SET disable_whisper = ? WHERE player_id = ?");
            preparedStatement.setString(1, disableWhisper ? "1" : "0");
            preparedStatement.setInt(2, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the username already exist for this storage contract.
     *
     * @param username Username supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int getUsernameAlreadyExist(String username) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT COUNT(0) as exist FROM players WHERE username = ?", sqlConnection);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("exist");
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 1;
    }

    /**
     * Updates players username for this storage contract.
     *
     * @param newName New name supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public static void updatePlayersUsername(String newName, int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE players SET username = ? WHERE id = ?", sqlConnection);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates rooms username for this storage contract.
     *
     * @param newName New name supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public static void updateRoomsUsername(String newName, int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE rooms SET owner = ? WHERE owner_id = ?", sqlConnection);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists name change log for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param newName New name supplied by the caller.
     * @param oldName Old name supplied by the caller.
     */
    public static void saveNameChangeLog(int playerId, String newName, String oldName) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("INSERT INTO logs_namechange (`user_id`, `new_name`, `old_name`, `timestamp`) VALUES(?, ?, ?, ?)", sqlConnection);

            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, newName);
            preparedStatement.setString(3, oldName);
            preparedStatement.setInt(4, (int) Comet.getTime());

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists trade log for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param byplayerid Byplayerid supplied by the caller.
     * @param baseitem Baseitem supplied by the caller.
     * @param itemid Itemid supplied by the caller.
     * @param timestamp Timestamp supplied by the caller.
     */
    public static void saveTradeLog(int playerId, int byplayerid, int baseitem, long itemid, int timestamp) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("INSERT INTO `logs_trades` (`player_id`, `by_id`, `items_id`, `item_id`, `times`) VALUES (?, ?, ?, ?, ?)", sqlConnection);

            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, byplayerid);
            preparedStatement.setInt(3, baseitem);
            preparedStatement.setLong(4, itemid);
            preparedStatement.setInt(5, timestamp);

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists exchange log for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param itemId Item id supplied by the caller.
     * @param baseId Base id supplied by the caller.
     * @param currencyData Currency data supplied by the caller.
     */
    public static void saveExchangeLog(int playerId, long itemId, int baseId, String currencyData) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("INSERT INTO logs_exchanges (`player_id`, `item_id`, `base_item`, `currency_change`, `timestamp`) VALUES(?, ?, ?, ?, ?)", sqlConnection);

            preparedStatement.setInt(1, playerId);
            preparedStatement.setLong(2, itemId);
            preparedStatement.setInt(3, baseId);
            preparedStatement.setString(4, currencyData);
            preparedStatement.setInt(5, (int)Comet.getTime());

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists navigator settings for this storage contract.
     *
     * @param navigatorX Navigator x supplied by the caller.
     * @param navigatorY Navigator y supplied by the caller.
     * @param navigatorHeight Navigator height supplied by the caller.
     * @param navigatorWidth Navigator width supplied by the caller.
     * @param navigatorShowSearches Navigator show searches supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public static void saveNavigatorSettings(int navigatorX, int navigatorY, int navigatorHeight, int navigatorWidth, boolean navigatorShowSearches, int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE player_settings SET navigator_x = ?, navigator_y = ?, navigator_height = ?, navigator_width = ?, navigator_show_searches = ? WHERE player_id = ?", sqlConnection);

            preparedStatement.setInt(1, navigatorX);
            preparedStatement.setInt(2, navigatorY);
            preparedStatement.setInt(3, navigatorHeight);
            preparedStatement.setInt(4, navigatorWidth);
            preparedStatement.setString(5, navigatorShowSearches ? "1" : "0");
            preparedStatement.setInt(6, playerId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the favourite rooms for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static Set<Integer> getFavouriteRooms(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final Set<Integer> data = new ConcurrentHashSet<>();

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `room_id` FROM `player_favourite_rooms` WHERE `player_id` = ?", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.add(resultSet.getInt("room_id"));
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return data;
    }

    /**
     * Persists favourite room for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param roomId Room identifier used by the operation.
     */
    public static void saveFavouriteRoom(int playerId, int roomId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("INSERT into player_favourite_rooms (player_id, room_id) VALUES(?, ?);", sqlConnection);

            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, roomId);
            preparedStatement.execute();

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Deletes favourite room for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param roomId Room identifier used by the operation.
     */
    public static void deleteFavouriteRoom(int playerId, int roomId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("DELETE FROM player_favourite_rooms WHERE player_id = ? AND room_id = ?", sqlConnection);

            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, roomId);
            preparedStatement.execute();

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the saved searches for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static Map<Integer, ISavedSearch> getSavedSearches(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final Map<Integer, ISavedSearch> data = new ConcurrentHashMap<>();

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `id`, `view`, `search_query` FROM player_saved_searches WHERE player_id = ? LIMIT 50;", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.put(resultSet.getInt("id"), new SavedSearch(resultSet.getString("view"), resultSet.getString("search_query")));
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return data;
    }

    /**
     * Returns the view modes for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static Map<String, Integer> getViewModes(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final Map<String, Integer> data = new ConcurrentHashMap<>();

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM player_navigator_view_modes WHERE player_id = ? LIMIT 50;", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.put(resultSet.getString("category"), resultSet.getInt("view_mode"));
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return data;
    }

    /**
     * Persists view mode for this storage contract.
     *
     * @param category Category supplied by the caller.
     * @param viewMode View mode supplied by the caller.
     * @param playerId Player identifier used by the operation.
     */
    public static void saveViewMode(String category, int viewMode, int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("REPLACE into player_navigator_view_modes (player_id, category, view_mode) VALUES(?, ?, ?);", sqlConnection);
            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, category);
            preparedStatement.setInt(3, viewMode);

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Persists search for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param savedSearch Saved search supplied by the caller.
     * @return Result produced by the mutation.
     */
    public static int saveSearch(int playerId, SavedSearch savedSearch) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("INSERT into player_saved_searches (player_id, view, search_query) VALUES(?, ?, ?);", sqlConnection, true);

            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, savedSearch.getView());
            preparedStatement.setString(3, savedSearch.getSearchQuery());

            preparedStatement.execute();

            resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return 0;
    }

    /**
     * Deletes search for this storage contract.
     *
     * @param searchId Search id supplied by the caller.
     */
    public static void deleteSearch(int searchId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("DELETE FROM player_saved_searches WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, searchId);

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Returns the effects for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @return Value exposed by the contract.
     */
    public static Set<Integer> getEffects(int playerId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        final Set<Integer> data = new ConcurrentHashSet<>();

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `effect_id` FROM `player_effects` WHERE `player_id` = ?;", sqlConnection);
            preparedStatement.setInt(1, playerId);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.add(resultSet.getInt("effect_id"));
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

        return data;
    }

    /**
     * Persists effect for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param effectId Effect id supplied by the caller.
     */
    public static void saveEffect(int playerId, int effectId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("INSERT into player_effects (player_id, effect_id) VALUES(?,?);", sqlConnection);
            preparedStatement.setInt(1, playerId);
            preparedStatement.setInt(2, effectId);

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates rank for this storage contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param rank Rank supplied by the caller.
     */
    public static void updateRank(int playerId, int rank) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE players SET rank  = ? WHERE id = ?", sqlConnection);
            preparedStatement.setInt(1, rank);
            preparedStatement.setInt(2, playerId);

            preparedStatement.execute();
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

}
