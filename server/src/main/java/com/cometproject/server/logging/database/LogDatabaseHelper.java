package com.cometproject.server.logging.database;

import com.cometproject.server.logging.database.queries.LogQueries;
import com.cometproject.server.storage.SqlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes log database helper behavior for the logging subsystem.
 */
public class LogDatabaseHelper {
    private static Logger LOGGER = LoggerFactory.getLogger(LogDatabaseHelper.class.getName());

    /**
     * Executes init for this logging contract.
     */
    public static void init() {
        LogQueries.updateRoomEntries();
    }

    /**
     * Returns the connection for this logging contract.
     *
     * @return Value exposed by the contract.
     * @throws SQLException When the operation cannot complete.
     */
    public static Connection getConnection() throws SQLException {
        return SqlHelper.getConnection();
    }

    /**
     * Executes close silently for this logging contract.
     *
     * @param connection Connection supplied by the caller.
     */
    public static void closeSilently(Connection connection) {
        try {
            if (connection == null) {
                return;
            }
            connection.close();
        } catch (SQLException e) {
            handleSqlException(e);
        }
    }

    /**
     * Executes close silently for this logging contract.
     *
     * @param resultSet Result set supplied by the caller.
     */
    public static void closeSilently(ResultSet resultSet) {
        try {
            if (resultSet == null) {
                return;
            }
            resultSet.close();
        } catch (SQLException e) {
            handleSqlException(e);
        }
    }

    /**
     * Executes close silently for this logging contract.
     *
     * @param statement Statement supplied by the caller.
     */
    public static void closeSilently(PreparedStatement statement) {
        try {
            if (statement == null) {
                return;
            }
            statement.close();
        } catch (SQLException e) {
            handleSqlException(e);
        }
    }

    /**
     * Executes execute statement silently for this logging contract.
     *
     * @param statement Statement supplied by the caller.
     * @param autoClose Auto close supplied by the caller.
     */
    public static void executeStatementSilently(PreparedStatement statement, boolean autoClose) {
        try {
            if (statement == null) {
                return;
            }
            statement.execute();

            if (autoClose) {
                statement.close();
            }
        } catch (SQLException e) {
            handleSqlException(e);
        }
    }

    /**
     * Executes prepare for this logging contract.
     *
     * @param query Query supplied by the caller.
     * @param con Con supplied by the caller.
     * @return Result produced by the operation.
     * @throws SQLException When the operation cannot complete.
     */
    public static PreparedStatement prepare(String query, Connection con) throws SQLException {
        return prepare(query, con, false);
    }

    /**
     * Executes prepare for this logging contract.
     *
     * @param query Query supplied by the caller.
     * @param con Con supplied by the caller.
     * @param returnKeys Return keys supplied by the caller.
     * @return Result produced by the operation.
     * @throws SQLException When the operation cannot complete.
     */
    public static PreparedStatement prepare(String query, Connection con, boolean returnKeys) throws SQLException {
        return returnKeys ? con.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS) : con.prepareStatement(query);
    }

    /**
     * Handles SQL exception for this logging contract.
     *
     * @param e E supplied by the caller.
     */
    public static void handleSqlException(SQLException e) {
        LOGGER.error("Error while executing query", e);
    }

}
