package com.cometproject.storage.mysql.data.transactions;

/**
 * Defines the transaction consumer contract for the MySQL storage subsystem.
 */
public interface TransactionConsumer {
    /**
     * Executes accept for this Comet contract.
     *
     * @param transaction Transaction supplied by the caller.
     */
    void accept(Transaction transaction) throws Exception;
}
