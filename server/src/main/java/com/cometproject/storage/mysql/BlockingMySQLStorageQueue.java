package com.cometproject.storage.mysql;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.utilities.Pair;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Describes blocking my SQL storage queue behavior for the MySQL storage subsystem.
 */
public abstract class BlockingMySQLStorageQueue<T, O> extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(BlockingMySQLStorageQueue.class);

    private final MySQLConnectionProvider connectionProvider;

    private final String batchQuery;
    private final int batchThreshold;

    private final Map<T, O> mapping;
    private final BlockingQueue<Pair<T, O>> queue;

    /**
     * Creates a blocking my SQL storage queue instance for the MySQL storage subsystem.
     *
     * @param connectionProvider Connection provider supplied by the caller.
     * @param batchQuery Batch query supplied by the caller.
     * @param batchThreshold Batch threshold supplied by the caller.
     */
    public BlockingMySQLStorageQueue(final MySQLConnectionProvider connectionProvider, final String batchQuery,
                                     final int batchThreshold) {
        this.batchQuery = batchQuery;
        this.connectionProvider = connectionProvider;
        this.batchThreshold = batchThreshold;
        this.queue = new ArrayBlockingQueue<>(25000);
        this.mapping = Maps.newConcurrentMap();
    }

    /**
     * Runs this MySQL storage task.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(10000);// sleep for 10 seconds give the server chance to boot up

            final Set<Pair<T, O>> entriesToProcess = new HashSet<>();

            while (GameContext.getCurrent() != null) {

                for (int i = 0; i < batchThreshold; i++) {
                    final Pair<T, O> entry = this.queue.poll(50, TimeUnit.MILLISECONDS);

                    if (entry != null) {
                        entriesToProcess.add(entry);
                    }
                }

                this.processEntries(entriesToProcess);
            }

            while (!this.queue.isEmpty()) {
                final Pair<T, O> entry = this.queue.poll(50, TimeUnit.MILLISECONDS);

                if (entry != null) {
                    entriesToProcess.add(entry);
                }

                this.processEntries(entriesToProcess);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to process batch", e);
        }
    }

    /**
     * Returns the queued for this MySQL storage contract.
     *
     * @param key Key supplied by the caller.
     * @return Value exposed by the contract.
     */
    public O getQueued(T key) {
        return this.mapping.get(key);
    }

    private void processEntries(Set<Pair<T, O>> entriesToProcess) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = this.connectionProvider.getConnection();

            preparedStatement = sqlConnection.prepareStatement(this.batchQuery);

            for (Pair<T, O> obj : entriesToProcess) {
                try {
                    this.mapping.remove(obj.getLeft());
                    this.processBatch(preparedStatement, obj.getLeft(), obj.getRight());
                } catch (Exception e) {
                    LOGGER.error("Failed to process batch entry", e);
                }
            }

            preparedStatement.executeBatch();
            entriesToProcess.clear();
        } catch (Exception e) {
            LOGGER.error("Failed to prepare batch process");
        } finally {
            this.connectionProvider.closeStatement(preparedStatement);
            this.connectionProvider.closeConnection(sqlConnection);
        }
    }

    /**
     * Executes add for this MySQL storage contract.
     *
     * @param key Key supplied by the caller.
     * @param obj Obj supplied by the caller.
     */
    public void add(T key, O obj) {
        this.mapping.put(key, obj);
        this.queue.add(new Pair<>(key, obj));
    }

    /**
     * Adds all to this MySQL storage contract.
     *
     * @param all All supplied by the caller.
     */
    public void addAll(Collection<Pair<T, O>> all) {
        for (Pair<T, O> obj : all) {
            this.add(obj.getLeft(), obj.getRight());
        }
    }

    /**
     * Processes batch for this MySQL storage contract.
     *
     * @param preparedStatement Prepared statement supplied by the caller.
     * @param id Id supplied by the caller.
     * @param object Object supplied by the caller.
     * @throws Exception When the operation cannot complete.
     */
    protected abstract void processBatch(PreparedStatement preparedStatement, T id, O object) throws Exception;
}
