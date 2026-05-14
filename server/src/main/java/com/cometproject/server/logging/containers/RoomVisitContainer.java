package com.cometproject.server.logging.containers;

import com.cometproject.server.logging.database.queries.LogQueries;
import com.cometproject.server.logging.entries.RoomVisitLogEntry;

import java.util.List;


/**
 * Describes room visit container behavior for the logging subsystem.
 */
public class RoomVisitContainer {
    /**
     * Executes put for this logging contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param roomId Room identifier used by the operation.
     * @param timeEnter Time enter supplied by the caller.
     * @return Result produced by the operation.
     */
    public RoomVisitLogEntry put(int playerId, int roomId, long timeEnter) {
        return LogQueries.putRoomVisit(playerId, roomId, (int) timeEnter);
    }

    /**
     * Updates exit for this logging contract.
     *
     * @param logEntry Log entry supplied by the caller.
     */
    public void updateExit(RoomVisitLogEntry logEntry) {
        LogQueries.updateRoomEntry(logEntry);
    }

    /**
     * Executes get for this logging contract.
     *
     * @param playerId Player identifier used by the operation.
     * @param count Count supplied by the caller.
     * @return Value exposed by the contract.
     */
    public List<RoomVisitLogEntry> get(int playerId, int count) {
        return null;
    }
}
