package com.cometproject.server.game.rooms.objects.items.types.floor.wired;

import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;


/**
 * Carries wired item snapshot data for the room subsystem.
 */
public class WiredItemSnapshot {
    private long itemId;
    private int x;
    private int y;
    private double z;
    private int rotation;
    private String extraData;

    /**
     * Creates a wired item snapshot instance for the room subsystem.
     *
     * @param floorItem Floor item supplied by the caller.
     */
    public WiredItemSnapshot(RoomItemFloor floorItem) {
        this.itemId = floorItem.getId();
        this.x = floorItem.getPosition().getX();
        this.y = floorItem.getPosition().getY();
        this.z = floorItem.getPosition().getZ();
        this.rotation = floorItem.getRotation();
        this.extraData = floorItem.getItemData().getData();
    }

    /**
     * Returns the item id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Updates the item id for this room contract.
     *
     * @param itemId Item id supplied by the caller.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the x for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getX() {
        return x;
    }

    /**
     * Updates the x for this room contract.
     *
     * @param x X supplied by the caller.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getY() {
        return y;
    }

    /**
     * Updates the y for this room contract.
     *
     * @param y Y supplied by the caller.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the z for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public double getZ() {
        return z;
    }

    /**
     * Updates the z for this room contract.
     *
     * @param z Z supplied by the caller.
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Returns the rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRotation() {
        return rotation;
    }

    /**
     * Updates the rotation for this room contract.
     *
     * @param rotation Rotation supplied by the caller.
     */
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns the extra data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Updates the extra data for this room contract.
     *
     * @param extraData Extra data supplied by the caller.
     */
    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

    /**
     * Defines the refreshable contract for the room subsystem.
     */
    public interface Refreshable {
        /**
         * Executes refresh snapshots for this Comet contract.
         */
        void refreshSnapshots();
    }
}
