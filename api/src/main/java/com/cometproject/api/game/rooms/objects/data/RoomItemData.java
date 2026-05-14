package com.cometproject.api.game.rooms.objects.data;

import com.cometproject.api.game.furniture.types.LimitedEditionItem;
import com.cometproject.api.game.rooms.objects.IRoomItemData;
import com.cometproject.api.game.utilities.Position;

/**
 * Describes room item data behavior for the room subsystem.
 */
public class RoomItemData implements IRoomItemData {
    private final long id;
    private final int itemId;
    private final int ownerId;
    private final String ownerName;

    private String data;

    private int rotation;
    private Position floorPosition;
    private String wallPosition;

    private final LimitedEditionItem limitedEdition;
    public boolean isOnCooldown = false;

    /**
     * Creates a room item data instance for the room subsystem.
     *
     * @param id Id value supplied by the caller.
     * @param itemId Item id value supplied by the caller.
     * @param ownerId Owner id value supplied by the caller.
     * @param ownerName Owner name value supplied by the caller.
     * @param position Position value supplied by the caller.
     * @param rotation Rotation value supplied by the caller.
     * @param data Data value supplied by the caller.
     * @param wallPosition Wall position value supplied by the caller.
     * @param limitedEditionItem Limited edition item value supplied by the caller.
     */
    public RoomItemData(long id, int itemId, int ownerId, String ownerName, Position position, int rotation, String data, String wallPosition, LimitedEditionItem limitedEditionItem) {
        this.id = id;
        this.itemId = itemId;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.data = data;

        this.rotation = rotation;
        this.floorPosition = position;

        this.wallPosition = wallPosition;

        this.limitedEdition = limitedEditionItem;
    }

    /**
     * Returns the id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the item id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getItemId() {
        return this.itemId;
    }

    /**
     * Returns the owner id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Returns the owner name for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * Returns the position for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Position getPosition() {
        return this.floorPosition;
    }

    /**
     * Returns the wall position for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getWallPosition() {
        return this.wallPosition;
    }

    /**
     * Returns the rotation for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRotation() {
        return this.rotation;
    }

    /**
     * Updates the rotation for this room contract.
     *
     * @param rotation Rotation supplied by the caller.
     */
    @Override
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    /**
     * Returns the data for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getData() {
        return data;
    }

    /**
     * Updates the data for this room contract.
     *
     * @param data Data supplied by the caller.
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Updates the data for this room contract.
     *
     * @param data Data supplied by the caller.
     */
    @Override
    public void setData(int data) {
        this.data = "" + data;
    }

    /**
     * Updates the position for this room contract.
     *
     * @param position Position supplied by the caller.
     */
    @Override
    public void setPosition(Position position) {
        this.floorPosition = position;
    }

    /**
     * Updates the wall position for this room contract.
     *
     * @param wallPosition Wall position supplied by the caller.
     */
    @Override
    public void setWallPosition(String wallPosition) {
        this.wallPosition = wallPosition;
    }

    /**
     * Returns the limited edition for this room contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public LimitedEditionItem  getLimitedEdition() {
        return limitedEdition;
    }
}
