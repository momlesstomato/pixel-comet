package com.cometproject.storage.mysql.queues.players;

import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.cometproject.storage.mysql.MySQLStorageQueue;
import com.cometproject.storage.mysql.queues.players.objects.PlayerStatusUpdate;

import java.sql.PreparedStatement;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Describes player status update queue behavior for the MySQL storage subsystem.
 */
public class PlayerStatusUpdateQueue extends MySQLStorageQueue<Integer, PlayerStatusUpdate> {
    /**
     * Creates a player status update queue instance for the MySQL storage subsystem.
     *
     * @param delayMilliseconds Delay milliseconds supplied by the caller.
     * @param executorService Executor service supplied by the caller.
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public PlayerStatusUpdateQueue(long delayMilliseconds, ScheduledExecutorService executorService, MySQLConnectionProvider connectionProvider) {
        super("UPDATE players SET online = ?, last_ip = ?, last_online = ? WHERE id = ?;", delayMilliseconds, executorService, connectionProvider);
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
    public void processBatch(PreparedStatement preparedStatement, Integer id, PlayerStatusUpdate object) throws Exception {
        preparedStatement.setString(1, object.isPlayerOnline() ? "1" : "0");
        preparedStatement.setString(2, object.getIpAddress());
        preparedStatement.setLong(3, System.currentTimeMillis() / 1000L);
        preparedStatement.setInt(4, object.getPlayerId());

        preparedStatement.addBatch();
    }
}
