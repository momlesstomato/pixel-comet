package com.cometproject.server.utilities;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creates comet thread instances for the Comet subsystem.
 */
public class CometThreadFactory implements ThreadFactory {

    private final String baseName;
    private final AtomicInteger threadCounter;

    /**
     * Creates a comet thread factory instance for the Comet subsystem.
     *
     * @param baseNameFormat Base name format supplied by the caller.
     */
    public CometThreadFactory(String baseNameFormat) {
        this.baseName = baseNameFormat;
        this.threadCounter = new AtomicInteger(0);
    }

    /**
     * Executes new thread for this Comet contract.
     *
     * @param r R supplied by the caller.
     * @return Result produced by the operation.
     */
    @Override
    public Thread newThread(Runnable r) {
        int threadId = this.threadCounter.incrementAndGet();
        final Thread thread = new Thread(r, String.format("Comet-%s-%s", baseName, threadId));

        // Set prioritisation or whatever?

        return thread;
    }
}
