package com.cometproject.api.game.rooms;

/**
 * Defines the i room service contract for the room subsystem.
 */
public interface IRoomService {
    /**
     * Returns the room data associated with this room contract.
     *
     * @param roomId Room id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IRoomData getRoomData(int roomId);

    /**
     * Persists room data data for this room contract.
     *
     * @param roomData Room data value supplied by the caller.
     */
    void saveRoomData(IRoomData roomData);
}
