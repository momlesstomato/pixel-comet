package com.cometproject.server.logging.entries;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.logging.AbstractLogEntry;
import com.cometproject.server.logging.LogEntryType;

/**
 * Describes alfa log entry behavior for the logging subsystem.
 */
public class AlfaLogEntry extends AbstractLogEntry {
    private int senderId;
    private int receiverId;
    private String message;
    private int timestamp;

    /**
     * Creates a alfa log entry instance for the logging subsystem.
     *
     * @param senderId Sender id supplied by the caller.
     * @param receiverId Receiver id supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public AlfaLogEntry(int senderId, int receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = (int) Comet.getTime();
    }

    /**
     * Returns the type for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public LogEntryType getType() {
        return LogEntryType.ALFA_CHATLOG;
    }

    /**
     * Returns the string for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getString() {
        return "To: " + this.receiverId + ", Message: " + this.message;
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
     * Returns the player id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getPlayerId() {
        return this.senderId;
    }
}
