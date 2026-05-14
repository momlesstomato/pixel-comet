package com.cometproject.server.storage.queries.webchat;

import com.cometproject.server.storage.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Describes web chat dao behavior for the storage subsystem.
 */
public class WebChatDao {
    /**
     * Finds player id by auth ticket for this storage contract.
     *
     * @param authTicket Auth ticket supplied by the caller.
     * @return Value exposed by the contract.
     */
    public static int findPlayerIdByAuthTicket(String authTicket) {
        if (authTicket == null || authTicket.isEmpty()) {
            return 0;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = SqlHelper.getConnection();

            preparedStatement = connection.prepareStatement("SELECT id FROM players WHERE chat_ticket = ?");
            preparedStatement.setString(1, authTicket);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(connection);
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(resultSet);
        }

        return 0;
    }
}
