package com.cometproject.api.game.rooms.models;

/**
 * Describes custom floor map data behavior for the room subsystem.
 */
public class CustomFloorMapData {
    private final int doorX, doorY, doorRotation, wallHeight;
    private final String modelData;

    /**
     * Creates a custom floor map data instance for the room subsystem.
     *
     * @param doorX Door x value supplied by the caller.
     * @param doorY Door y value supplied by the caller.
     * @param doorRotation Door rotation value supplied by the caller.
     * @param modelData Model data value supplied by the caller.
     * @param wallHeight Wall height value supplied by the caller.
     */
    public CustomFloorMapData(int doorX, int doorY, int doorRotation, String modelData, int wallHeight) {
        this.doorX = doorX;
        this.doorY = doorY;
        this.doorRotation = doorRotation;
        this.modelData = modelData;
        this.wallHeight = wallHeight;
    }

    /**
     * Returns the door x for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDoorX() {
        return doorX;
    }

    /**
     * Returns the door y for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDoorY() {
        return doorY;
    }

    /**
     * Returns the door rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDoorRotation() {
        return doorRotation;
    }

    /**
     * Returns the model data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getModelData() {
        return modelData;
    }

    /**
     * Returns the wall height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getWallHeight() {
        return wallHeight;
    }
}
