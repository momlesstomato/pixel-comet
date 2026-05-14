package com.cometproject.server.logging;

import com.cometproject.server.logging.containers.LogEntryContainer;
import com.cometproject.server.logging.containers.RoomVisitContainer;

import java.util.concurrent.TimeUnit;


/**
 * Describes log store behavior for the logging subsystem.
 */
public class LogStore {
    private static final TimeUnit QUEUE_FLUSH_UNIT = TimeUnit.MINUTES;
    private static final int QUEUE_FLUSH_TIME = 1;

    // Containers
    private RoomVisitContainer roomVisitContainer;
    private LogEntryContainer logEntryContainer;

    /**
     * Creates a log store instance for the logging subsystem.
     */
    public LogStore() {
        if (!LogManager.ENABLED)
            return;

        // Register the containers
        roomVisitContainer = new RoomVisitContainer();
        logEntryContainer = new LogEntryContainer();
    }

    /**
     * Returns the room visit container for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public RoomVisitContainer getRoomVisitContainer() {
        return roomVisitContainer;
    }

    /**
     * Returns the log entry container for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public LogEntryContainer getLogEntryContainer() {
        return logEntryContainer;
    }
}
