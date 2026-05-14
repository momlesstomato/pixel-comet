package com.cometproject.storage.mysql.queues.players;

import com.cometproject.api.game.players.data.IPlayerData;
import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.cometproject.storage.mysql.MySQLStorageQueue;

import java.sql.PreparedStatement;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Describes player data update queue behavior for the MySQL storage subsystem.
 */
public class PlayerDataUpdateQueue extends MySQLStorageQueue<Integer, IPlayerData> {
    /**
     * Creates a player data update queue instance for the MySQL storage subsystem.
     *
     * @param delayMilliseconds Delay milliseconds supplied by the caller.
     * @param executorService Executor service supplied by the caller.
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public PlayerDataUpdateQueue(long delayMilliseconds, ScheduledExecutorService executorService, MySQLConnectionProvider connectionProvider) {
        super("UPDATE players SET username = ?, motto = ?, figure = ?, credits = ?, gender = ?, favourite_group = ?, quest_id = ?, achievement_points = ? WHERE id = ?;", delayMilliseconds, executorService, connectionProvider);
    }

    /**
     * Processes batch for this MySQL storage contract.
     *
     * @param preparedStatement Prepared statement supplied by the caller.
     * @param id Id supplied by the caller.
     * @param player Player participating in the operation.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    protected void processBatch(PreparedStatement preparedStatement, Integer id, IPlayerData player) throws Exception {
        preparedStatement.setString(1, player.getUsername());
        preparedStatement.setString(2, player.getMotto());
        preparedStatement.setString(3, player.getFigure());
        preparedStatement.setInt(4, player.getCredits());
        preparedStatement.setString(5, player.getGender());
        preparedStatement.setInt(6, player.getFavouriteGroup());
        preparedStatement.setInt(7, player.getQuestId());
        preparedStatement.setInt(8, player.getAchievementPoints());
        preparedStatement.setInt(9, player.getId());

        preparedStatement.addBatch();
    }
}
