package com.cometproject.server.storage.queries.items;

import com.cometproject.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;


/**
 * Describes trade dao behavior for the storage subsystem.
 */
public class TradeDao {

    /**
     * Updates trade items for this storage contract.
     *
     * @param userId User id supplied by the caller.
     * @param itemId Item id supplied by the caller.
     */
    public static void updateTradeItems(int userId, long itemId) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE `items` SET `user_id` = ? WHERE `id` = ?", sqlConnection);

            preparedStatement.setInt(1, userId);
            preparedStatement.setLong(2, itemId);

            SqlHelper.executeStatementSilently(preparedStatement, false);
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /**
     * Updates trade items for this storage contract.
     *
     * @param itemsToUpdate Items to update supplied by the caller.
     */
    public static void updateTradeItems(Map<Long, Integer> itemsToUpdate) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("UPDATE `items` SET `user_id` = ? WHERE `id` = ?;", sqlConnection);

            for (Map.Entry<Long, Integer> item : itemsToUpdate.entrySet()) {
                preparedStatement.setInt(1, item.getValue());
                preparedStatement.setLong(2, item.getKey());

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
}
