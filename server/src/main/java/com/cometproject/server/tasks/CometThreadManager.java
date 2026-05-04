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


public class CometThreadManager implements Startable {
    public static int POOL_SIZE = 0;
    private ScheduledExecutorService coreExecutor;
    private ScheduledExecutorService roomProcessingExecutor;

    public CometThreadManager() {

    }

    public static CometThreadManager getInstance() {
        return CometBootstrap.resolve(CometThreadManager.class);
    }

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

    @Override
    public void stop() {
        if (this.roomProcessingExecutor != null) {
            this.roomProcessingExecutor.shutdownNow();
        }

        if (this.coreExecutor != null) {
            this.coreExecutor.shutdownNow();
        }
    }

    public Future executeOnce(CometTask task) {
        return this.coreExecutor.submit(task);
    }

    public ScheduledFuture executePeriodic(CometTask task, long initialDelay, long period, TimeUnit unit) {
        if (task instanceof ProcessComponent || task instanceof ItemProcessComponent) {
            // Handle room processing in a different pool, this should help against
            return this.roomProcessingExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
        }

        return this.coreExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }

    public ScheduledFuture executeSchedule(CometTask task, long delay, TimeUnit unit) {
        if (task instanceof ProcessComponent) {
            return this.roomProcessingExecutor.schedule(task, delay, unit);
        }

        return this.coreExecutor.schedule(task, delay, unit);
    }

    public ScheduledExecutorService getCoreExecutor() {
        return coreExecutor;
    }
}