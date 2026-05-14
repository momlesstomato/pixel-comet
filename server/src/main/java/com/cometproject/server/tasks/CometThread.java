package com.cometproject.server.tasks;

/**
 * Describes comet thread behavior for the task scheduling subsystem.
 */
public class CometThread extends Thread {

    /**
     * Creates a comet thread instance for the task scheduling subsystem.
     *
     * @param task Task supplied by the caller.
     */
    public CometThread(CometTask task) {
        super(task, "Comet Task");
    }

    /**
     * Creates a comet thread instance for the task scheduling subsystem.
     *
     * @param task Task supplied by the caller.
     * @param identifier Identifier supplied by the caller.
     */
    public CometThread(CometTask task, String identifier) {
        super(task, "Comet Task [" + identifier + "]");
    }

    /**
     * Starts this task scheduling component.
     */
    @Override
    public void start() {
        if (this.isRunning()) {
            return;
        }

        super.start();
    }

    /**
     * Indicates whether running applies to this task scheduling contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isRunning() {
        return super.isAlive();
    }
}
