package com.cometproject.storage.mysql.data;

import java.sql.PreparedStatement;

/**
 * Defines the statement consumer contract for the MySQL storage subsystem.
 */
public interface StatementConsumer {
    /**
     * Executes accept for this Comet contract.
     *
     * @param statement Statement supplied by the caller.
     */
    void accept(final PreparedStatement statement) throws Exception;
}
