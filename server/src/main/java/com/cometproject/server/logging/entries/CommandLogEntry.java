package com.cometproject.server.logging.entries;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.logging.AbstractLogEntry;
import com.cometproject.server.logging.LogEntryType;

/**
 * Describes command log entry behavior for the logging subsystem.
 */
public class CommandLogEntry extends AbstractLogEntry {
    private int roomId;
    private int playerId;
    private String message;
    private int timestamp;

    /**
     * Creates a command log entry instance for the logging subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     * @param message Message supplied by the caller.
     */
    public CommandLogEntry(int roomId, int playerId, String message) {
        this.roomId = roomId;
        this.playerId = playerId;
        this.message = message;
        this.timestamp = (int) Comet.getTime();
    }

    /**
     * Creates a command log entry instance for the logging subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param playerId Player identifier used by the operation.
     * @param message Message supplied by the caller.
     * @param timestamp Timestamp supplied by the caller.
     */
    public CommandLogEntry(int roomId, int playerId, String message, int timestamp) {
        this.roomId = roomId;
        this.playerId = playerId;
        this.message = message;
        this.timestamp = timestamp;
    }

    /**
     * Returns the type for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public LogEntryType getType() {
        return LogEntryType.COMMAND;
    }

    /**
     * Returns the string for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getString() {
        return this.message;
    }

    /**
     * Returns the timestamp for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getTimestamp() {
        return this.timestamp;
    }

    /**
     * Returns the room id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRoomId() {
        return this.roomId;
    }

    /**
     * Returns the player id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getPlayerId() {
        return this.playerId;
    }
}


