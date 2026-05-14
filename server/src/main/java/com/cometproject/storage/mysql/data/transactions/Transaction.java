package com.cometproject.storage.mysql.data.transactions;

import java.sql.Connection;

/**
 * Defines the transaction contract for the MySQL storage subsystem.
 */
public interface Transaction {
    Transaction NULL = null;

    /**
     * Executes start transaction for this Comet contract.
     */
    void startTransaction() throws Exception;

    /**
     * Returns the connection for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    Connection getConnection();

    /**
     * Executes commit for this Comet contract.
     */
    void commit() throws Exception;

    /**
     * Executes rollback for this Comet contract.
     */
    void rollback() throws Exception;
}
