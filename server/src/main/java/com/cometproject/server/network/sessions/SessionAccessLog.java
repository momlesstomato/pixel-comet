package com.cometproject.server.network.sessions;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Describes session access log behavior for the network session subsystem.
 */
public class SessionAccessLog {
    private final AtomicInteger counter = new AtomicInteger(1);
    private long lastConnection = System.currentTimeMillis();

    /**
     * Executes increment counter for this network session contract.
     */
    public void incrementCounter() {
        this.counter.incrementAndGet();
        this.lastConnection = System.currentTimeMillis();
    }

    /**
     * Executes reset counter for this network session contract.
     */
    public void resetCounter() {
        this.counter.set(0);
        this.lastConnection = 0;
    }

    /**
     * Indicates whether suspicious applies to this network session contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isSuspicious() {
        return this.counter.get() >= 1000 && ((System.currentTimeMillis() - this.lastConnection) < 2000); // Value 1000 TESTED and wont down
    }
}
