package com.cometproject.api.game.rooms.models;

/**
 * Defines the i room model contract for the room subsystem.
 */
public interface IRoomModel {

    /**
     * Returns the relative heightmap associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getRelativeHeightmap();

    /**
     * Returns the id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getId();

    /**
     * Returns the room model data associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomModelData getRoomModelData();

    /**
     * Returns the square state associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomTileState[][] getSquareState();

    /**
     * Returns the square height associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int[][] getSquareHeight();

    /**
     * Returns the map associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMap();

    /**
     * Returns the door x associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDoorX();

    /**
     * Returns the door y associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDoorY();

    /**
     * Returns the door z associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDoorZ();

    /**
     * Returns the size x associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSizeX();

    /**
     * Returns the size y associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSizeY();

    /**
     * Returns the door rotation associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDoorRotation();
}
