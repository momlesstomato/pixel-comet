package com.cometproject.api.game.furniture.types;

/**
 * Defines the i gift data contract for the furniture subsystem.
 */
public interface IGiftData {
    /**
     * Returns the page id associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getPageId();

    /**
     * Returns the item id associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getItemId();

    /**
     * Returns the receiver associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getReceiver();

    /**
     * Returns the message associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getMessage();

    /**
     * Returns the sprite id associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSpriteId();

    /**
     * Returns the wrapping paper associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getWrappingPaper();

    /**
     * Returns the decoration type associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getDecorationType();

    /**
     * Indicates whether show username is enabled for this furniture contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isShowUsername();

    /**
     * Returns the sender id associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSenderId();

    /**
     * Returns the extra data associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getExtraData();

    /**
     * Updates the extra data value for this furniture contract.
     *
     * @param extraData Extra data value supplied by the caller.
     */
    void setExtraData(String extraData);
}
