package com.cometproject.server.storage.queue;

import com.cometproject.api.utilities.Startable;
import com.cometproject.server.tasks.CometTask;

public interface StorageQueue<T> extends Startable, CometTask {

    void queueSave(T object);

    void unqueue(T object);

    boolean isQueued(T object);

    void shutdown();
}
