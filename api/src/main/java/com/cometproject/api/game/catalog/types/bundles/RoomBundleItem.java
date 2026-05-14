package com.cometproject.api.game.catalog.types.bundles;

/**
 * Describes room bundle item behavior for the catalog subsystem.
 */
public class RoomBundleItem {
    private int itemId;

    private int x;
    private int y;
    private double z;
    private int rotation;

    private String wallPosition;

    private String extraData;

    /**
     * Creates a room bundle item instance for the catalog subsystem.
     *
     * @param itemId Item id value supplied by the caller.
     * @param x X value supplied by the caller.
     * @param y Y value supplied by the caller.
     * @param z Z value supplied by the caller.
     * @param rotation Rotation value supplied by the caller.
     * @param wallPosition Wall position value supplied by the caller.
     * @param extraData Extra data value supplied by the caller.
     */
    public RoomBundleItem(int itemId, int x, int y, double z, int rotation, String wallPosition, String extraData) {
        this.itemId = itemId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.rotation = rotation;
        this.extraData = extraData;
        this.wallPosition = wallPosition;
    }

    /**
     * Returns the item id for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Updates the item id for this catalog contract.
     *
     * @param itemId Item id supplied by the caller.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the x for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getX() {
        return x;
    }

    /**
     * Updates the x for this catalog contract.
     *
     * @param x X supplied by the caller.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getY() {
        return y;
    }

    /**
     * Updates the y for this catalog contract.
     *
     * @param y Y supplied by the caller.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the z for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public double getZ() {
        return z;
    }

    /**
     * Updates the z for this catalog contract.
     *
     * @param z Z supplied by the caller.
     */
    public void setZ(int z) {
        this.z = z;
    }

    /**
     * Returns the extra data for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Updates the extra data for this catalog contract.
     *
     * @param extraData Extra data supplied by the caller.
     */
    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    /**
     * Returns the wall position for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public String getWallPosition() {
        return wallPosition;
    }

    /**
     * Returns the rotation for this catalog contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRotation() {
        return rotation;
    }
}
