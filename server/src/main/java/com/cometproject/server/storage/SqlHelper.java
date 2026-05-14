package com.cometproject.server.storage;

import com.cometproject.storage.mysql.MySQLConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Describes SQL helper behavior for the storage subsystem.
 */
public class SqlHelper {
    public static boolean queryLogEnabled = false;
    public static Map<Integer, QueryLog> queryLog = new ConcurrentHashMap<>();
    private static MySQLConnectionProvider connectionProvider;
    private static Logger LOGGER = LoggerFactory.getLogger(SqlHelper.class.getName());
    private static Map<String, AtomicInteger> queryCounters = new ConcurrentHashMap<>();

    /**
     * Executes init for this storage contract.
     *
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public static void init(MySQLConnectionProvider connectionProvider) {
        SqlHelper.connectionProvider = connectionProvider;
    }

    /**
     * Returns the connection for this storage contract.
     *
     * @return Value exposed by the contract.
     * @throws SQLException When the operation cannot complete.
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            connection = connectionProvider.getConnection();
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.error("Unexpected error while retrieving connection", e);
        }

        return connection;
    }

    /**
     * Executes close silently for this storage contract.
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
     * Executes close silently for this storage contract.
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
     * Executes close silently for this storage contract.
     *
     * @param statement Statement supplied by the caller.
     */
    public static void closeSilently(PreparedStatement statement) {
        try {
            if (statement == null) {
                return;
            }

            if (queryLogEnabled && queryLog.containsKey(statement.hashCode())) {
                final QueryLog log = queryLog.get(statement.hashCode());
                final long timeTaken = (System.currentTimeMillis() - log.startTime);

                System.out.println("[QUERY] " + log.query + " took " + timeTaken + "ms");

                queryLog.remove(statement.hashCode());
            }

            statement.close();
        } catch (SQLException e) {
            handleSqlException(e);
        }
    }

    /**
     * Executes execute statement silently for this storage contract.
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
     * Executes prepare for this storage contract.
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
     * Executes prepare for this storage contract.
     *
     * @param query Query supplied by the caller.
     * @param con Con supplied by the caller.
     * @param returnKeys Return keys supplied by the caller.
     * @return Result produced by the operation.
     * @throws SQLException When the operation cannot complete.
     */
    public static PreparedStatement prepare(String query, Connection con, boolean returnKeys) throws SQLException {
        if (Thread.currentThread().getName().startsWith("Room-Processor"))
            LOGGER.trace("Executing query from room processor: " + query);

        if (!queryCounters.containsKey(query)) {
            queryCounters.put(query, new AtomicInteger(1));
        } else {
            queryCounters.get(query).incrementAndGet();
        }

        final PreparedStatement statement = returnKeys ? con.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS) : con.prepareStatement(query);

        if (queryLogEnabled) {
            final QueryLog log = new QueryLog();
            log.query = query;

            queryLog.put(statement.hashCode(), log);
        }

        return statement;
    }

    /**
     * Handles SQL exception for this storage contract.
     *
     * @param e E supplied by the caller.
     */
    public static void handleSqlException(SQLException e) {
        final String msg = e.getMessage();
        if (msg == null) return;
        if (msg.equals("Pool has been shutdown")
                || msg.contains("has been closed")
                || msg.contains("Communications link failure")
                || msg.contains("Data too long for column")) {
            return;
        }
        LOGGER.error("Error while executing query", e);
    }

    /**
     * Executes escape wildcards for this storage contract.
     *
     * @param s S supplied by the caller.
     * @return Result produced by the operation.
     */
    public static String escapeWildcards(String s) {
        return s.replaceAll("_", "\\\\_").replaceAll("%", "\\\\%");
    }

    /**
     * Returns the query counters for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    public static Map<String, AtomicInteger> getQueryCounters() {
        return queryCounters;
    }

    /**
     * Describes query log behavior for the storage subsystem.
     */
    public static class QueryLog {
        public long startTime = System.currentTimeMillis();
        public String query;
    }
}
