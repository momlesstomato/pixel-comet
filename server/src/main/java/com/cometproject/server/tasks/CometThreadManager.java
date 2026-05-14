package com.cometproject.server.tasks;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cometproject.api.config.Configuration;
import com.cometproject.api.utilities.Startable;
import com.cometproject.server.boot.CometBootstrap;
import com.cometproject.server.game.rooms.types.components.ItemProcessComponent;
import com.cometproject.server.game.rooms.types.components.ProcessComponent;


/**
 * Manages comet thread runtime state for the task scheduling subsystem.
 */
public class CometThreadManager implements Startable {
    public static int POOL_SIZE = 0;
    private ScheduledExecutorService coreExecutor;
    private ScheduledExecutorService roomProcessingExecutor;

    /**
     * Creates a comet thread manager instance for the task scheduling subsystem.
     */
    public CometThreadManager() {

    }

    /**
     * Returns the instance for this task scheduling contract.
     *
     * @return Value exposed by the contract.
     */
    public static CometThreadManager getInstance() {
        return CometBootstrap.resolve(CometThreadManager.class);
    }

    /**
     * Starts this task scheduling component.
     */
    @Override
    public void start() {
        int poolSize = Integer.parseInt((String) Configuration.currentConfig().getOrDefault("comet.system.threads", "8"));

        this.coreExecutor = Executors.newScheduledThreadPool(poolSize, r -> {
            POOL_SIZE++;

            Thread scheduledThread = new Thread(r);
            scheduledThread.setName("Comet-Scheduler-Thread-" + POOL_SIZE);

            final Logger LOGGER = LoggerFactory.getLogger("Comet-Scheduler-Thread-" + POOL_SIZE);
            scheduledThread.setUncaughtExceptionHandler((t, e) -> LOGGER.error("Exception in worker thread", e));

            return scheduledThread;
        });

        final int roomProcessingPool = Integer.parseInt(Configuration.currentConfig().get("comet.system.taskRoomThreads"));
        final AtomicInteger counter = new AtomicInteger();

        this.roomProcessingExecutor = Executors.newScheduledThreadPool(roomProcessingPool, r -> {
            Thread scheduledThread = new Thread(r);
            scheduledThread.setName("Room-Processor-" + counter.incrementAndGet());

            final Logger LOGGER = LoggerFactory.getLogger(scheduledThread.getName());
            scheduledThread.setUncaughtExceptionHandler((t, e) -> LOGGER.error("Exception in room worker thread", e));

            return scheduledThread;
        });
    }

    /**
     * Stops this task scheduling component.
     */
    @Override
    public void stop() {
        if (this.roomProcessingExecutor != null) {
            this.roomProcessingExecutor.shutdownNow();
        }

        if (this.coreExecutor != null) {
            this.coreExecutor.shutdownNow();
        }
    }

    /**
     * Executes execute once for this task scheduling contract.
     *
     * @param task Task supplied by the caller.
     * @return Result produced by the operation.
     */
    public Future executeOnce(CometTask task) {
        return this.coreExecutor.submit(task);
    }

    /**
     * Executes execute periodic for this task scheduling contract.
     *
     * @param task Task supplied by the caller.
     * @param initialDelay Initial delay supplied by the caller.
     * @param period Period supplied by the caller.
     * @param unit Unit supplied by the caller.
     * @return Result produced by the operation.
     */
    public ScheduledFuture executePeriodic(CometTask task, long initialDelay, long period, TimeUnit unit) {
        if (task instanceof ProcessComponent || task instanceof ItemProcessComponent) {
            // Handle room processing in a different pool, this should help against
            return this.roomProcessingExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
        }

        return this.coreExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    /**
     * Executes execute schedule for this task scheduling contract.
     *
     * @param task Task supplied by the caller.
     * @param delay Delay supplied by the caller.
     * @param unit Unit supplied by the caller.
     * @return Result produced by the operation.
     */
    public ScheduledFuture executeSchedule(CometTask task, long delay, TimeUnit unit) {
        if (task instanceof ProcessComponent) {
            return this.roomProcessingExecutor.schedule(task, delay, unit);
        }

        return this.coreExecutor.schedule(task, delay, unit);
    }

    /**
     * Returns the core executor for this task scheduling contract.
     *
     * @return Value exposed by the contract.
     */
    public ScheduledExecutorService getCoreExecutor() {
        return coreExecutor;
    }
}