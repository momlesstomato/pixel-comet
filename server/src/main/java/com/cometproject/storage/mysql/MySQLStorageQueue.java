package com.cometproject.storage.mysql;

import com.cometproject.api.utilities.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Describes my SQL storage queue behavior for the MySQL storage subsystem.
 */
public abstract class MySQLStorageQueue<T, O> {

    private final String batchQuery;
    private final Map<T, O> storageQueue;
    private final Future processFuture;

    private boolean running = false;

    private final MySQLConnectionProvider connectionProvider;

    /**
     * Creates a my SQL storage queue instance for the MySQL storage subsystem.
     *
     * @param batchQuery Batch query supplied by the caller.
     * @param delayMilliseconds Delay milliseconds supplied by the caller.
     * @param executorService Executor service supplied by the caller.
     * @param connectionProvider Connection provider supplied by the caller.
     */
    public MySQLStorageQueue(String batchQuery, long delayMilliseconds, ScheduledExecutorService executorService,
                             MySQLConnectionProvider connectionProvider) {
        this.batchQuery = batchQuery;
        this.storageQueue = new ConcurrentHashMap<>();

        this.connectionProvider = connectionProvider;
        this.processFuture = executorService.scheduleAtFixedRate(this::process,
                delayMilliseconds + (1000 * (ThreadLocalRandom.current().nextInt(1, 5 + 1))),
                delayMilliseconds, TimeUnit.MILLISECONDS);

    }

    /**
     * Executes add for this MySQL storage contract.
     *
     * @param key Key supplied by the caller.
     * @param obj Obj supplied by the caller.
     */
    public void add(T key, O obj) {
        if (this.storageQueue.containsKey(key)) {
            this.storageQueue.replace(key, obj);
        } else {
            this.storageQueue.put(key, obj);
        }
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

    private void process() {
        this.running = true;

        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = this.connectionProvider.getConnection();

            preparedStatement = sqlConnection.prepareStatement(this.batchQuery);

            for (Map.Entry<T, O> obj : this.storageQueue.entrySet()) {
                try {
                    this.processBatch(preparedStatement, obj.getKey(), obj.getValue());
                } catch (Exception e) {
                    this.onException(e);
                }
            }

            preparedStatement.executeBatch();

            this.storageQueue.clear();
        } catch (Exception e) {
            this.onException(e);
        } finally {
            this.connectionProvider.closeStatement(preparedStatement);
            this.connectionProvider.closeConnection(sqlConnection);
        }

        this.running = false;
    }

    /**
     * Handles the exception callback for this MySQL storage contract.
     *
     * @param e E supplied by the caller.
     */
    public void onException(Exception e) {
        // TODO: log this somewhere useful, though it shouldn't really throw exceptions....
        e.printStackTrace();
    }

    /**
     * Returns the queued for this MySQL storage contract.
     *
     * @param obj Obj supplied by the caller.
     * @return Value exposed by the contract.
     */
    public O getQueued(T obj) {
        return this.storageQueue.get(obj);
    }

    /**
     * Stops this MySQL storage component.
     */
    public void stop() {
        if (!this.running) {
            this.process();
        }

        this.processFuture.cancel(false);
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
