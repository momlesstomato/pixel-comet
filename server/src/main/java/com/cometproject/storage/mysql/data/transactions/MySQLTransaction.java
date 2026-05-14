package com.cometproject.storage.mysql.data.transactions;

import java.sql.Connection;

/**
 * Describes my SQL transaction behavior for the MySQL storage subsystem.
 */
public class MySQLTransaction implements Transaction {

    private final Connection connection;

    /**
     * Creates a my SQL transaction instance for the MySQL storage subsystem.
     *
     * @param connection Connection supplied by the caller.
     */
    public MySQLTransaction(Connection connection) {
        this.connection = connection;
    }

    /**
     * Executes start transaction for this MySQL storage contract.
     *
     * @throws Exception When the operation cannot complete.
     */
    public void startTransaction() throws Exception {
        this.connection.setAutoCommit(false);
    }

    /**
     * Returns the connection for this MySQL storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * Executes commit for this MySQL storage contract.
     *
     * @throws Exception When the operation cannot complete.
     */
    public void commit() throws Exception {
        this.connection.commit();
    }

    /**
     * Executes rollback for this MySQL storage contract.
     *
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void rollback() throws Exception {
        this.connection.rollback();
    }
}
