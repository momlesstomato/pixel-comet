package com.cometproject.api.game.catalog.types.bundles;

/**
 * Describes room bundle config behavior for the catalog subsystem.
 */
public class RoomBundleConfig {
    private String roomName;
    private String decorations;
    private int thicknessWall;
    private int thicknessFloor;
    private boolean hideWalls;

    /**
     * Creates a room bundle config instance for the catalog subsystem.
     *
     * @param roomName Room name value supplied by the caller.
     * @param decorations Decorations value supplied by the caller.
     * @param thicknessWall Thickness wall value supplied by the caller.
     * @param thicknessFloor Thickness floor value supplied by the caller.
     * @param hideWalls Hide walls value supplied by the caller.
     */
    public RoomBundleConfig(String roomName, String decorations, int thicknessWall, int thicknessFloor, boolean hideWalls) {
        this.roomName = roomName;
        this.decorations = decorations;
        this.thicknessWall = thicknessWall;
        this.thicknessFloor = thicknessFloor;
        this.hideWalls = hideWalls;
    }

    /**
     * Returns the room name for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public String getRoomName() {
        return roomName;
    }

    /**
     * Updates the room name for this catalog contract.
     *
     * @param roomName Room name supplied by the caller.
     */
    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    /**
     * Returns the decorations for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public String getDecorations() {
        return decorations;
    }

    /**
     * Updates the decorations for this catalog contract.
     *
     * @param decorations Decorations supplied by the caller.
     */
    public void setDecorations(String decorations) {
        this.decorations = decorations;
    }

    /**
     * Returns the thickness wall for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getThicknessWall() {
        return thicknessWall;
    }

    /**
     * Updates the thickness wall for this catalog contract.
     *
     * @param thicknessWall Thickness wall supplied by the caller.
     */
    public void setThicknessWall(int thicknessWall) {
        this.thicknessWall = thicknessWall;
    }

    /**
     * Returns the thickness floor for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getThicknessFloor() {
        return thicknessFloor;
    }

    /**
     * Updates the thickness floor for this catalog contract.
     *
     * @param thicknessFloor Thickness floor supplied by the caller.
     */
    public void setThicknessFloor(int thicknessFloor) {
        this.thicknessFloor = thicknessFloor;
    }

    /**
     * Indicates whether hide walls applies to this catalog contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isHideWalls() {
        return hideWalls;
    }

    /**
     * Updates the hide walls for this catalog contract.
     *
     * @param hideWalls Hide walls supplied by the caller.
     */
    public void setHideWalls(boolean hideWalls) {
        this.hideWalls = hideWalls;
    }
}
