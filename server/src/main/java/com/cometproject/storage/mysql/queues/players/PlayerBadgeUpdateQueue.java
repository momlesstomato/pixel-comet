package com.cometproject.storage.mysql.queues.players;

import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.cometproject.storage.mysql.MySQLStorageQueue;
import com.cometproject.storage.mysql.queues.players.objects.PlayerBadgeUpdate;

import java.sql.PreparedStatement;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Describes player badge update queue behavior for the MySQL storage subsystem.
 */
public class PlayerBadgeUpdateQueue extends MySQLStorageQueue<Integer, PlayerBadgeUpdate> {

    /**
     * Creates a player badge update queue instance for the MySQL storage subsystem.
     *
     * @param delayMilliseconds Delay milliseconds supplied by the caller.
     * @param executorService Executor service supplied by the caller.
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public PlayerBadgeUpdateQueue(long delayMilliseconds, ScheduledExecutorService executorService, MySQLConnectionProvider connectionProvider) {
        super("UPDATE player_badges SET slot = ? WHERE badge_code = ? AND player_id = ?;", delayMilliseconds, executorService, connectionProvider);
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
    protected void processBatch(PreparedStatement preparedStatement, Integer id, PlayerBadgeUpdate object) throws Exception {
        preparedStatement.setInt(1, object.getSlot());
        preparedStatement.setString(2, object.getBadgeId());
        preparedStatement.setInt(3, id);

        preparedStatement.addBatch();
    }
}
