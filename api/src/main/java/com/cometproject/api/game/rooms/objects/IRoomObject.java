package com.cometproject.api.game.rooms.objects;

import com.cometproject.api.game.rooms.IRoom;
import com.cometproject.api.game.utilities.Position;

/**
 * Defines the i room object contract for the room subsystem.
 */
public interface IRoomObject {
    /**
     * Returns the position associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Position getPosition();

    /**
     * Indicates whether at door is enabled for this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isAtDoor();

    /**
     * Returns the room associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IRoom getRoom();
}
