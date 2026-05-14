package com.cometproject.server.storage.queries.moderation;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.moderation.types.Ban;
import com.cometproject.server.game.moderation.types.BanType;
import com.cometproject.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Describes ban dao behavior for the storage subsystem.
 */
public class BanDao {
    /**
     * Returns the active bans for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public static Map<String, Ban> getActiveBans() {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<String, Ban> data = new ConcurrentHashMap<>();

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT * FROM bans WHERE expire = 0 OR expire > " + Comet.getTime(), sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.put(resultSet.getString("data"), new Ban(resultSet));
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
     * Creates ban for this storage contract.
     *
     * @param type Type supplied by the caller.
     * @param length Length supplied by the caller.
     * @param expire Expire supplied by the caller.
     * @param data Data supplied by the caller.
     * @param addedBy Added by supplied by the caller.
     * @param reason Reason supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int createBan(BanType type, long length, long expire, String data, int addedBy, String reason) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("INSERT into bans (`type`, `expire`, `data`, `reason`, `added_by`) VALUES(?, ?, ?, ?, ?);", sqlConnection, true);

            preparedStatement.setString(1, type.toString().toLowerCase());
            preparedStatement.setLong(2, length == 0 ? 0 : expire);
            preparedStatement.setString(3, data);
            preparedStatement.setString(4, reason);
            preparedStatement.setInt(5, addedBy);

            SqlHelper.executeStatementSilently(preparedStatement, false);
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
     * Deletes ban for this storage contract.
     *
     * @param data Data supplied by the caller.
     */
    public static void deleteBan(String data) {

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("DELETE FROM bans WHERE data = ?", sqlConnection, true);

            preparedStatement.setString(1, data);

            SqlHelper.executeStatementSilently(preparedStatement, false);
            resultSet = preparedStatement.getGeneratedKeys();

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }

    }
}
