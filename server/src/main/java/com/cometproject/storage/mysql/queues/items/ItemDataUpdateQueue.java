package com.cometproject.storage.mysql.queues.items;

import com.cometproject.storage.mysql.BlockingMySQLStorageQueue;
import com.cometproject.storage.mysql.MySQLConnectionProvider;

import java.sql.PreparedStatement;

/**
 * Describes item data update queue behavior for the MySQL storage subsystem.
 */
public class ItemDataUpdateQueue extends BlockingMySQLStorageQueue<Long, String> {

    /**
     * Creates a item data update queue instance for the MySQL storage subsystem.
     *
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public ItemDataUpdateQueue(MySQLConnectionProvider connectionProvider) {
        super(connectionProvider, "UPDATE items SET extra_data = ? WHERE id = ?;", 25);
    }

    /**
     * Processes batch for this MySQL storage contract.
     *
     * @param preparedStatement Prepared statement supplied by the caller.
     * @param id Id supplied by the caller.
     * @param object Object supplied by the caller.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    public void processBatch(PreparedStatement preparedStatement, Long id, String object) throws Exception {
        preparedStatement.setString(1, object);
        preparedStatement.setLong(2, id);

        preparedStatement.addBatch();
    }
}
