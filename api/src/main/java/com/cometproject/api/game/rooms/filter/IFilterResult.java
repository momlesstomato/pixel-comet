package com.cometproject.api.game.rooms.filter;

/**
 * Defines the i filter result contract for the room subsystem.
 */
public interface IFilterResult {
    /**
     * Indicates whether blocked is enabled for this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isBlocked();

    /**
     * Returns the message associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMessage();

    /**
     * Executes the was modified operation for this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean wasModified();
}
