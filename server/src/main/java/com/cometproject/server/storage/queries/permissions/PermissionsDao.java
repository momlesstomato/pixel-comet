package com.cometproject.server.storage.queries.permissions;

import com.cometproject.server.game.permissions.types.Perk;
import com.cometproject.server.game.permissions.types.Rank;
import com.cometproject.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Describes permissions dao behavior for the storage subsystem.
 */
public class PermissionsDao {

    /**
     * Returns the perks for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public static Map<Integer, Perk> getPerks() {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<Integer, Perk> data = new ConcurrentHashMap<>();

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("SELECT `id`, `title`, `data`, `override_rank`, `override_default`, `min_rank` FROM permission_perks", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                data.put(resultSet.getInt("id"), new Perk(resultSet));
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
     * Returns the permissions for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public static Map<Integer, Rank> getPermissions() {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Map<Integer, Rank> data = new ConcurrentHashMap<>();
        try {
            sqlConnection = SqlHelper.getConnection();
            preparedStatement = SqlHelper.prepare("SELECT * FROM server_permissions", sqlConnection);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
                data.put(resultSet.getInt("id"), new Rank(resultSet));
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(resultSet);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
        return data;
    }

}
