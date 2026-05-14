package com.cometproject.logger.tasks;

import com.cometproject.api.networking.messages.IMessageComposer;
/**
 * Defines the comet task contract for the task scheduling subsystem.
 */
public interface CometTask extends Runnable {

    /**
     * Runs this task scheduling task.
     */
    @Override
    public abstract void run();
}
