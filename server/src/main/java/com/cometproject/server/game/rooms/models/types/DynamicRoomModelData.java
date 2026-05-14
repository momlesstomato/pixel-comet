package com.cometproject.server.game.rooms.models.types;

/**
 * Carries dynamic room model data data for the room subsystem.
 */
public class DynamicRoomModelData {
    private String name;
    private String heightmap;
    private int doorX;
    private int doorY;
    private int doorZ;
    private int doorRotation;
    private int wallHeight;

    /**
     * Creates a dynamic room model data instance for the room subsystem.
     *
     * @param name Name supplied by the caller.
     * @param heightmap Heightmap supplied by the caller.
     * @param doorX Door x supplied by the caller.
     * @param doorY Door y supplied by the caller.
     * @param doorZ Door z supplied by the caller.
     * @param doorRotation Door rotation supplied by the caller.
     * @param wallHeight Wall height supplied by the caller.
     */
    public DynamicRoomModelData(String name, String heightmap, int doorX, int doorY, int doorZ, int doorRotation, int wallHeight) {
        this.name = name;
        this.heightmap = heightmap;
        this.doorX = doorX;
        this.doorY = doorY;
        this.doorZ = doorZ;
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
     * Updates the name for this room contract.
     *
     * @param name Name supplied by the caller.
     */
    public void setName(String name) {
        this.name = name;
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
     * Updates the heightmap for this room contract.
     *
     * @param heightmap Heightmap supplied by the caller.
     */
    public void setHeightmap(String heightmap) {
        this.heightmap = heightmap;
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
     * Updates the door x for this room contract.
     *
     * @param doorX Door x supplied by the caller.
     */
    public void setDoorX(int doorX) {
        this.doorX = doorX;
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
     * Updates the door y for this room contract.
     *
     * @param doorY Door y supplied by the caller.
     */
    public void setDoorY(int doorY) {
        this.doorY = doorY;
    }

    /**
     * Returns the door z for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDoorZ() {
        return doorZ;
    }

    /**
     * Updates the door z for this room contract.
     *
     * @param doorZ Door z supplied by the caller.
     */
    public void setDoorZ(int doorZ) {
        this.doorZ = doorZ;
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
     * Updates the door rotation for this room contract.
     *
     * @param doorRotation Door rotation supplied by the caller.
     */
    public void setDoorRotation(int doorRotation) {
        this.doorRotation = doorRotation;
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
