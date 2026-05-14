package com.cometproject.server.storage.queue;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.tasks.CometTask;

/**
 * Defines the storage queue contract for the storage subsystem.
 */
public interface StorageQueue<T> extends Startable, CometTask {

    /**
     * Executes queue save for this Comet contract.
     *
     * @param object Object supplied by the caller.
     */
    void queueSave(T object);

    /**
     * Executes unqueue for this Comet contract.
     *
     * @param object Object supplied by the caller.
     */
    void unqueue(T object);

    /**
     * Indicates whether queued applies to this Comet contract.
     *
     * @param object Object supplied by the caller.
     * @return Value exposed by the contract.
     */
    boolean isQueued(T object);

    /**
     * Executes shutdown for this Comet contract.
     */
    void shutdown();
}
