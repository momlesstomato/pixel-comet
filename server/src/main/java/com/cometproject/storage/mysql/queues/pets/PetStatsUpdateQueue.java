package com.cometproject.storage.mysql.queues.pets;

import com.cometproject.api.game.pets.IPetStats;
import com.cometproject.storage.mysql.MySQLConnectionProvider;
import com.cometproject.storage.mysql.MySQLStorageQueue;

import java.sql.PreparedStatement;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Describes pet stats update queue behavior for the MySQL storage subsystem.
 */
public class PetStatsUpdateQueue extends MySQLStorageQueue<Integer, IPetStats> {
    /**
     * Creates a pet stats update queue instance for the MySQL storage subsystem.
     *
     * @param delayMilliseconds Delay milliseconds supplied by the caller.
     * @param executorService Executor service supplied by the caller.
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public PetStatsUpdateQueue(long delayMilliseconds, ScheduledExecutorService executorService, MySQLConnectionProvider connectionProvider) {
        super("UPDATE pet_data SET scratches = ?, level = ?, happiness = ?, experience = ?, energy = ?, hunger = ? WHERE id = ?;", delayMilliseconds, executorService, connectionProvider);
    }

    /**
     * Processes batch for this MySQL storage contract.
     *
     * @param preparedStatement Prepared statement supplied by the caller.
     * @param id Id supplied by the caller.
     * @param pet Pet supplied by the caller.
     * @throws Exception When the operation cannot complete.
     */
    @Override
    protected void processBatch(PreparedStatement preparedStatement, Integer id, IPetStats pet) throws Exception {
        preparedStatement.setInt(1, pet.getScratches());
        preparedStatement.setInt(2, pet.getLevel());
        preparedStatement.setInt(3, pet.getHappiness());
        preparedStatement.setInt(4, pet.getExperience());
        preparedStatement.setInt(5, pet.getEnergy());
        preparedStatement.setInt(6, pet.getHunger());

        preparedStatement.setInt(7, pet.getId());

        preparedStatement.addBatch();
    }
}
