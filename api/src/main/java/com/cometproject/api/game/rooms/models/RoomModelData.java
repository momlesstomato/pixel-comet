package com.cometproject.api.game.rooms.models;

/**
 * Describes room model data behavior for the room subsystem.
 */
public class RoomModelData {
    private final String name, heightmap;
    private final int doorX, doorY, doorRotation;
    private int wallHeight;

    /**
     * Creates a room model data instance for the room subsystem.
     *
     * @param name Name value supplied by the caller.
     * @param heightmap Heightmap value supplied by the caller.
     * @param doorX Door x value supplied by the caller.
     * @param doorY Door y value supplied by the caller.
     * @param doorRotation Door rotation value supplied by the caller.
     * @param wallHeight Wall height value supplied by the caller.
     */
    public RoomModelData(String name, String heightmap, int doorX, int doorY, int doorRotation, int wallHeight) {
        this.name = name;
        this.heightmap = heightmap;
        this.doorX = doorX;
        this.doorY = doorY;
        this.doorRotation = doorRotation;
        this.wallHeight = wallHeight;
    }

    /**
     * Returns the name for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the heightmap for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getHeightmap() {
        return heightmap;
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
     * Returns the wall height for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getWallHeight() {
        return wallHeight;
    }

    /**
     * Updates the wall height for this room contract.
     *
     * @param wallHeight Wall height supplied by the caller.
     */
    public void setWallHeight(int wallHeight) {
        this.wallHeight = wallHeight;
    }
}
