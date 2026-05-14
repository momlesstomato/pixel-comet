package com.cometproject.api.game.furniture.types;

/**
 * Carries gift wrapping and receiver data for furniture purchases.
 */
public class GiftData implements IGiftData {
    /**
     * The page ID of the item.
     */
    private int pageId;

    /**
     * The item ID of the item.
     */
    private int itemId;

    /**
     * The ID of the player who sent the item.
     */
    private int senderId;

    /**
     * The username of the player who will receive the item.
     */
    private String receiver;

    /**
     * The message shown in the gift.
     */
    private String message;

    /**
     * The ID of the item in furnidata.
     */
    private int spriteId;

    /**
     * The wrapping paper identifier.
     */
    private int wrappingPaper;

    /**
     * The box decoration type.
     */
    private int decorationType;

    /**
     * Indicates whether the sender username should be shown in the gift.
     */
    private boolean showUsername;

    /**
     * The data supplied when purchasing the gift.
     */
    private String extraData;

    /**
     * Creates gift metadata for a catalog gift purchase.
     *
     * @param pageId The page ID of the item.
     * @param itemId The item ID.
     * @param senderId The ID of the player who sent the item.
     * @param receiver The name of the user who will receive the gift.
     * @param message The message that will appear in the gift.
     * @param spriteId The ID of the item in furnidata.
     * @param wrappingPaper The wrapping paper identifier.
     * @param decorationType The box decoration type.
     * @param showUsername Whether the sender username should be shown in the gift.
     * @param extraData The data supplied when purchasing the gift.
     */
    public GiftData(int pageId, int itemId, int senderId, String receiver, String message, int spriteId, int wrappingPaper, int decorationType, boolean showUsername, String extraData) {
        this.pageId = pageId;
        this.itemId = itemId;
        this.senderId = senderId;
        this.receiver = receiver;
        this.message = message;
        this.spriteId = spriteId;
        this.wrappingPaper = wrappingPaper;
        this.decorationType = decorationType;
        this.showUsername = showUsername;
        this.extraData = extraData;
    }

    /**
     * Creates a gift data instance for the furniture subsystem.
     */
    public GiftData() {
        this.pageId = 0;
        this.itemId = 0;
        this.senderId = 0;
        this.receiver = "";
        this.message = "";
        this.spriteId = 0;
        this.wrappingPaper = 0;
        this.decorationType = 0;
        this.showUsername = false;
        this.extraData = "0";
    }

    /**
     * Returns the page id for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * Returns the item id for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Returns the receiver for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Returns the message for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the sprite id for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSpriteId() {
        return spriteId;
    }

    /**
     * Returns the wrapping paper for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getWrappingPaper() {
        return wrappingPaper;
    }

    /**
     * Returns the decoration type for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDecorationType() {
        return decorationType;
    }

    /**
     * Indicates whether show username applies to this furniture contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isShowUsername() {
        return showUsername;
    }

    /**
     * Returns the sender id for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSenderId() {
        return senderId;
    }

    /**
     * Returns the extra data for this furniture contract.
     *
     * @return Value exposed by the contract.
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Updates the extra data for this furniture contract.
     *
     * @param extraData Extra data supplied by the caller.
     */
    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
