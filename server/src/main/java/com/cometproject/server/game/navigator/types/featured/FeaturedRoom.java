package com.cometproject.server.game.navigator.types.featured;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.game.rooms.types.RoomWriter;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes featured room behavior for the navigator subsystem.
 */
public class FeaturedRoom {
    private int id;
    private boolean isCategory;
    private BannerType bannerType;
    private String caption;
    private String description;
    private String image;
    private ImageType imageType;
    private int roomId;
    private int categoryId;
    private boolean enabled;
    private boolean recommended;

    private IRoomData room;

    /**
     * Creates a featured room instance for the navigator subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public FeaturedRoom(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.bannerType = BannerType.get(data.getString("banner_type"));
        this.caption = data.getString("caption");
        this.description = data.getString("description");
        this.image = data.getString("image");
        this.imageType = ImageType.get(data.getString("image_type"));
        this.roomId = data.getInt("room_id");
        this.categoryId = data.getInt("category_id");
        this.enabled = Boolean.parseBoolean(data.getString("enabled"));
        this.recommended = data.getString("recommended").equals("1");
        this.isCategory = data.getString("type").equals("category");

        // cache the room data so we dont have to get it every time we load the nav
        if (!isCategory) this.room = GameContext.getCurrent().getRoomService().getRoomData(roomId);
    }

    /**
     * Creates a featured room instance for the navigator subsystem.
     *
     * @param id Id supplied by the caller.
     * @param bannerType Banner type supplied by the caller.
     * @param caption Caption supplied by the caller.
     * @param description Description supplied by the caller.
     * @param image Image supplied by the caller.
     * @param imageType Image type supplied by the caller.
     * @param roomId Room identifier used by the operation.
     * @param categoryId Category id supplied by the caller.
     * @param enabled Enabled supplied by the caller.
     * @param recommended Recommended supplied by the caller.
     * @param isCategory Is category supplied by the caller.
     */
    public FeaturedRoom(int id, BannerType bannerType, String caption, String description, String image, ImageType imageType, int roomId, int categoryId, boolean enabled, boolean recommended, boolean isCategory) {
        this.id = id;
        this.bannerType = bannerType;
        this.caption = caption;
        this.description = description;
        this.image = image;
        this.imageType = imageType;
        this.roomId = roomId;
        this.categoryId = categoryId;
        this.enabled = enabled;
        this.recommended = recommended;
        this.isCategory = isCategory;

        if (!isCategory) this.room = GameContext.getCurrent().getRoomService().getRoomData(roomId);
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        final boolean isActive = !isCategory && room != null && RoomManager.getInstance().isActive(room.getId());

        msg.writeInt(id);
        msg.writeString((!isCategory) ? room.getName() : caption);
        msg.writeString((!isCategory) ? (description != null && description.isEmpty() ? room.getDescription() : description) : description);

        msg.writeInt(bannerType == BannerType.BIG ? 0 : 1);
        msg.writeString(!isCategory ? caption : "");
        msg.writeString(imageType == ImageType.EXTERNAL ? image : "");
        msg.writeInt(categoryId);

        msg.writeInt(isActive ? RoomManager.getInstance().get(roomId).getEntities().playerCount() : 0);
        msg.writeInt(isCategory ? 4 : 2); // is room

        if (isCategory) {
            msg.writeBoolean(false);
        } else {
            RoomWriter.write(room, msg);
        }
    }

    /**
     * Returns the id for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the banner type for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public BannerType getBannerType() {
        return bannerType;
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
     * Returns the image for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public String getImage() {
        return image;
    }

    /**
     * Returns the image type for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public ImageType getImageType() {
        return imageType;
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
     * Returns the category id for this navigator contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Indicates whether enabled applies to this navigator contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Indicates whether recommended applies to this navigator contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isRecommended() {
        return recommended;
    }

    /**
     * Indicates whether category applies to this navigator contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isCategory() {
        return this.isCategory;
    }
}
