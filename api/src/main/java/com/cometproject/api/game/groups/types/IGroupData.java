package com.cometproject.api.game.groups.types;

import com.cometproject.api.game.players.data.PlayerAvatar;

/**
 * Defines the i group data contract for the group subsystem.
 */
public interface IGroupData {
    /**
     * Returns the id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Updates the id value for this group contract.
     *
     * @param id Id value supplied by the caller.
     */
    void setId(int id);

    /**
     * Returns the title associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getTitle();

    /**
     * Updates the title value for this group contract.
     *
     * @param title Title value supplied by the caller.
     */
    void setTitle(String title);

    /**
     * Returns the description associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getDescription();

    /**
     * Updates the description value for this group contract.
     *
     * @param description Description value supplied by the caller.
     */
    void setDescription(String description);

    /**
     * Returns the owner id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getOwnerId();

    /**
     * Updates the owner id value for this group contract.
     *
     * @param id Id value supplied by the caller.
     */
    void setOwnerId(int id);

    /**
     * Returns the badge associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getBadge();

    /**
     * Updates the badge value for this group contract.
     *
     * @param badge Badge value supplied by the caller.
     */
    void setBadge(String badge);

    /**
     * Returns the room id associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRoomId();

    /**
     * Updates the room id value for this group contract.
     *
     * @param roomId Room id value supplied by the caller.
     */
    void setRoomId(int roomId);

    /**
     * Returns the created timestamp associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCreatedTimestamp();

    /**
     * Indicates whether this group contract can members decorate.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean canMembersDecorate();

    /**
     * Updates the can members decorate value for this group contract.
     *
     * @param canMembersDecorate Can members decorate value supplied by the caller.
     */
    void setCanMembersDecorate(boolean canMembersDecorate);

    /**
     * Returns the type associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    GroupType getType();

    /**
     * Updates the type value for this group contract.
     *
     * @param type Type value supplied by the caller.
     */
    void setType(GroupType type);

    /**
     * Returns the colour a associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getColourA();

    /**
     * Updates the colour a value for this group contract.
     *
     * @param colourA Colour a value supplied by the caller.
     */
    void setColourA(int colourA);

    /**
     * Returns the colour b associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getColourB();

    /**
     * Updates the colour b value for this group contract.
     *
     * @param colourB Colour b value supplied by the caller.
     */
    void setColourB(int colourB);

    /**
     * Indicates whether this group contract has forum.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasForum();

    /**
     * Updates the has forum value for this group contract.
     *
     * @param hasForum Has forum value supplied by the caller.
     */
    void setHasForum(boolean hasForum);

    /**
     * Returns the owner name associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getOwnerName();

    /**
     * Returns the owner avatar associated with this group contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerAvatar getOwnerAvatar();
}
