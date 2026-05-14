package com.cometproject.server.logging.entries;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.logging.AbstractLogEntry;
import com.cometproject.server.logging.LogEntryType;

/**
 * Describes trade log entry behavior for the logging subsystem.
 */
public class TradeLogEntry extends AbstractLogEntry {
    private int senderId;
    private int receiverId;
    private int roomId;
    private String message;
    private int timestamp;

    /**
     * Creates a trade log entry instance for the logging subsystem.
     *
     * @param senderId Sender id supplied by the caller.
     * @param receiverId Receiver id supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param message Message supplied by the caller.
     */
    public TradeLogEntry(int senderId, int receiverId, int roomId, String message) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.roomId = roomId;
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
        return LogEntryType.TRADE;
    }

    /**
     * Returns the string for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getString() {
        return "To: " + this.receiverId + " " + this.message;
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
        return this.senderId;
    }
}
