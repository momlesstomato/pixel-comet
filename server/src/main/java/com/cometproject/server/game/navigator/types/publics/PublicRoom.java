package com.cometproject.server.game.navigator.types.publics;

/**
 * Describes public room behavior for the navigator subsystem.
 */
public class PublicRoom {
    private final int roomId;
    private final String caption;
    private final String description;
    private final String imageUrl;
    private final String category;

    /**
     * Creates a public room instance for the navigator subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param caption Caption supplied by the caller.
     * @param description Description supplied by the caller.
     * @param imageUrl Image url supplied by the caller.
     * @param category Category supplied by the caller.
     */
    public PublicRoom(int roomId, String caption, String description, String imageUrl, String category) {
        this.roomId = roomId;
        this.caption = caption;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    /**
     * Returns the room id for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRoomId() {
        return roomId;
    }

    /**
     * Returns the caption for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCaption() {
        return caption;
    }

    /**
     * Returns the description for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the image URL for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Returns the category for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public String getCategory() {
        return category;
    }
}
