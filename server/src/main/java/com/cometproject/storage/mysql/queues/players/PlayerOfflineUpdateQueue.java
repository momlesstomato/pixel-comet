package com.cometproject.storage.mysql.queues.players;

import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.cometproject.storage.mysql.MySQLStorageQueue;

import java.sql.PreparedStatement;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Describes player offline update queue behavior for the MySQL storage subsystem.
 */
public class PlayerOfflineUpdateQueue extends MySQLStorageQueue<Integer, Object> {

    /**
     * Creates a player offline update queue instance for the MySQL storage subsystem.
     *
     * @param delayMilliseconds Delay milliseconds supplied by the caller.
     * @param executorService Executor service supplied by the caller.
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public PlayerOfflineUpdateQueue(long delayMilliseconds, ScheduledExecutorService executorService, MySQLConnectionProvider connectionProvider) {
        super("UPDATE players SET online = '0' WHERE id = ?;", delayMilliseconds, executorService, connectionProvider);
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
    public void processBatch(PreparedStatement preparedStatement, Integer id, Object object) throws Exception {
        preparedStatement.setInt(1, id);

        preparedStatement.addBatch();
    }
}
