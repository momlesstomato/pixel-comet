package com.cometproject.api.game.rooms.objects;

import com.cometproject.api.game.utilities.Position;

/**
 * Defines the i floor item contract for the room subsystem.
 */
public interface IFloorItem {
    /**
     * Returns the id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getId();

    /**
     * Returns the data object associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getDataObject();

    /**
     * Returns the position associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Position getPosition();

    /**
     * Returns the rotation associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRotation();

}
