package com.cometproject.server.logging.entries;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.logging.AbstractLogEntry;
import com.cometproject.server.logging.LogEntryType;

/**
 * Describes offline chat log entry behavior for the logging subsystem.
 */
public class OfflineChatLogEntry extends AbstractLogEntry {
    private int senderId;
    private int receiverId;
    private String message;
    private int timestamp;

    /**
     * Creates a offline chat log entry instance for the logging subsystem.
     *
     * @param senderId Sender id supplied by the caller.
     * @param receiverId Receiver id supplied by the caller.
     * @param message Message supplied by the caller.
     */
    public OfflineChatLogEntry(int senderId, int receiverId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.timestamp = (int) Comet.getTime();
    }

    /**
     * Creates a offline chat log entry instance for the logging subsystem.
     *
     * @param senderId Sender id supplied by the caller.
     * @param receiverId Receiver id supplied by the caller.
     * @param message Message supplied by the caller.
     * @param timestamp Timestamp supplied by the caller.
     */
    public OfflineChatLogEntry(int senderId, int receiverId, String message, int timestamp) {
        this.senderId = senderId;
        this.receiverId = receiverId;
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
        return LogEntryType.OFFLINE_CHATLOG;
    }

    /**
     * Returns the string for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getString() {
        return this.senderId + "OFF_MSG]" + this.message;
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
        return this.receiverId;
    }
}
