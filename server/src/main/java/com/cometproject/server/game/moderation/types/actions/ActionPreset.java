package com.cometproject.server.game.moderation.types.actions;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes action preset behavior for the moderation subsystem.
 */
public class ActionPreset {
    private int id;
    private int categoryId;
    private String name;
    private String message;
    private String description;

    private int banLength;
    private int avatarBanLength;
    private int muteLength;
    private int tradeLockLength;

    /**
     * Creates a action preset instance for the moderation subsystem.
     *
     * @param resultSet Result set supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public ActionPreset(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.categoryId = resultSet.getInt("category_id");
        this.name = resultSet.getString("name");
        this.message = resultSet.getString("message");
        this.description = resultSet.getString("description");

        this.banLength = resultSet.getInt("ban_hours");
        this.avatarBanLength = resultSet.getInt("avatar_ban_hours");
        this.muteLength = resultSet.getInt("mute_hours");
        this.tradeLockLength = resultSet.getInt("trade_lock_hours");
    }

    /**
     * Returns the id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the category id for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * Returns the name for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the message for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the description for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the ban length for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBanLength() {
        return banLength;
    }

    /**
     * Returns the avatar ban length for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getAvatarBanLength() {
        return avatarBanLength;
    }

    /**
     * Returns the mute length for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getMuteLength() {
        return muteLength;
    }

    /**
     * Returns the trade lock length for this moderation contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTradeLockLength() {
        return tradeLockLength;
    }
}
