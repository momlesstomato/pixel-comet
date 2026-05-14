package com.cometproject.api.game.rooms.objects;

import com.cometproject.api.game.furniture.types.LimitedEditionItem;
import com.cometproject.api.game.utilities.Position;

/**
 * Defines the i room item data contract for the room subsystem.
 */
public interface IRoomItemData {

    /**
     * Returns the id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    long getId();

    /**
     * Returns the item id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getItemId();

    /**
     * Returns the owner id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getOwnerId();

    /**
     * Returns the owner name associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getOwnerName();

    /**
     * Returns the position associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Position getPosition();

    /**
     * Returns the wall position associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getWallPosition();

    /**
     * Returns the rotation associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRotation();

    /**
     * Updates the rotation value for this room contract.
     *
     * @param rotation Rotation value supplied by the caller.
     */
    void setRotation(int rotation);

    /**
     * Returns the data associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getData();

    /**
     * Updates the data value for this room contract.
     *
     * @param data Data value supplied by the caller.
     */
    void setData(String data);

    /**
     * Updates the data value for this room contract.
     *
     * @param data Data value supplied by the caller.
     */
    void setData(int data);

    /**
     * Updates the position value for this room contract.
     *
     * @param position Position value supplied by the caller.
     */
    void setPosition(Position position);

    /**
     * Updates the wall position value for this room contract.
     *
     * @param wallPosition Wall position value supplied by the caller.
     */
    void setWallPosition(String wallPosition);

    /**
     * Returns the limited edition associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    LimitedEditionItem getLimitedEdition();
}
