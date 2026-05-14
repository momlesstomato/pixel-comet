package com.cometproject.server.logging;

/**
 * Describes abstract log entry behavior for the logging subsystem.
 */
public abstract class AbstractLogEntry {
    /**
     * Returns the type for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract LogEntryType getType();

    /**
     * Returns the string for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract String getString();

    /**
     * Returns the timestamp for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract int getTimestamp();

    /**
     * Returns the room id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomId() {
        return -1;
    }

    /**
     * Returns the player id for this logging contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return -1;
    }
}
