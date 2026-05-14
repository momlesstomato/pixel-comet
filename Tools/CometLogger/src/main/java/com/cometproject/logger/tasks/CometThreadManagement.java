package com.cometproject.logger.tasks;

import com.cometproject.api.networking.messages.IMessageComposer;
import javolution.util.FastTable;

import java.lang.ref.WeakReference;
import java.util.UUID;
import java.util.concurrent.*;


/**
 * Describes comet thread management behavior for the task scheduling subsystem.
 */
public class CometThreadManagement {
    private final ScheduledExecutorService scheduledExecutorService;

    private final FastTable<WeakReference<Thread>> threadMonitoring = new FastTable<>();

    /**
     * Creates a comet thread management instance for the task scheduling subsystem.
     */
    public CometThreadManagement() {
        this.scheduledExecutorService = Executors.newScheduledThreadPool(2, new ThreadFactory() {
            /**
             * Executes new thread for this task scheduling contract.
             *
             * @param r R supplied by the caller.
             * @return Result produced by the operation.
             */
            @Override
            public Thread newThread(Runnable r) {
                UUID randomId = UUID.randomUUID();

                Thread scheduledThread = new Thread(r);
                scheduledThread.setName("Comet-Scheduled-Thread-" + randomId.toString());

                final Logger log = Logger.getLogger("Comet-Scheduled-Thread-" + randomId);

                scheduledThread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                    /**
                     * Executes uncaught exception for this task scheduling contract.
                     *
                     * @param t T supplied by the caller.
                     * @param e E supplied by the caller.
                     */
                    @Override
                    public void uncaughtException(Thread t, Throwable e) {
                        log.error("Exception in Comet Worker Thread", e);
                        e.printStackTrace();
                    }
                });

                threadMonitoring.add(new WeakReference<Thread>(scheduledThread));
                return scheduledThread;
            }
        });
    }

    /**
     * Executes execute once for this task scheduling contract.
     *
     * @param task Task supplied by the caller.
     * @return Result produced by the operation.
     */
    public Future executeOnce(CometTask task) {
        return this.scheduledExecutorService.submit(task);
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
        return this.scheduledExecutorService.scheduleAtFixedRate(task, initialDelay, period, unit);
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
        return this.scheduledExecutorService.schedule(task, delay, unit);
    }
}