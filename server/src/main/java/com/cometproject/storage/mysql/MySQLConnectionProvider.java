package com.cometproject.storage.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Describes my SQL connection provider behavior for the MySQL storage subsystem.
 */
public abstract class MySQLConnectionProvider {

    /**
     * Returns the connection for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     * @throws Exception When the operation cannot complete.
     */
    public abstract Connection getConnection() throws Exception;

    /**
     * Executes close connection for this MySQL storage contract.
     *
     * @param connection Connection supplied by the caller.
     */
    public abstract void closeConnection(Connection connection);

    /**
     * Executes close statement for this MySQL storage contract.
     *
     * @param statement Statement supplied by the caller.
     */
    public abstract void closeStatement(PreparedStatement statement);

    /**
     * Executes close results for this MySQL storage contract.
     *
     * @param resultSet Result set supplied by the caller.
     */
    public abstract void closeResults(ResultSet resultSet);

}

