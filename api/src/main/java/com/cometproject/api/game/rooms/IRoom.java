package com.cometproject.api.game.rooms;

/**
 * Defines the i room contract for the room subsystem.
 */
public interface IRoom {
    /**
     * Returns the data associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IRoomData getData();
}
