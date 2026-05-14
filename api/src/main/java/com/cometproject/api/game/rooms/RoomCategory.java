package com.cometproject.api.game.rooms;

/**
 * Defines the room category contract for the room subsystem.
 */
public interface RoomCategory {
    /**
     * Returns the id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the category associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getCategory();
    /**
     * Returns the category id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getCategoryId();
    /**
     * Returns the public name associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getPublicName();

    /**
     * Indicates whether this room contract can do actions.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean canDoActions();

    /**
     * Returns the colour associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getColour();
}
