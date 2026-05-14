package com.cometproject.api.game.rooms;

import com.cometproject.api.game.rooms.settings.*;

import java.util.List;
import java.util.Map;

/**
 * Defines the i room data contract for the room subsystem.
 */
public interface IRoomData {
    /**
     * Returns the id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the name associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getName();

    /**
     * Returns the description associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getDescription();

    /**
     * Returns the owner id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getOwnerId();

    /**
     * Returns the owner associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getOwner();

    /**
     * Returns the max users associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getMaxUsers();

    /**
     * Returns the access associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomAccessType getAccess();

    /**
     * Returns the password associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getPassword();

    /**
     * Returns the score associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getScore();

    /**
     * Returns the tags associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String[] getTags();

    /**
     * Returns the decorations associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<String, String> getDecorations();

    /**
     * Returns the model associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getModel();

    /**
     * Returns the hide walls associated with this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getHideWalls();

    /**
     * Returns the wall thickness associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getWallThickness();

    /**
     * Returns the floor thickness associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getFloorThickness();

    /**
     * Updates the id value for this room contract.
     *
     * @param id Id value supplied by the caller.
     */
    void setId(int id);

    /**
     * Updates the name value for this room contract.
     *
     * @param name Name value supplied by the caller.
     */
    void setName(String name);

    /**
     * Updates the description value for this room contract.
     *
     * @param description Description value supplied by the caller.
     */
    void setDescription(String description);

    /**
     * Updates the owner id value for this room contract.
     *
     * @param ownerId Owner id value supplied by the caller.
     */
    void setOwnerId(int ownerId);

    /**
     * Updates the owner value for this room contract.
     *
     * @param owner Owner value supplied by the caller.
     */
    void setOwner(String owner);

    /**
     * Returns the category id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getCategoryId();

    /**
     * Updates the category id value for this room contract.
     *
     * @param category Category value supplied by the caller.
     */
    void setCategoryId(int category);

    /**
     * Updates the max users value for this room contract.
     *
     * @param maxUsers Max users value supplied by the caller.
     */
    void setMaxUsers(int maxUsers);

    /**
     * Updates the access value for this room contract.
     *
     * @param access Access value supplied by the caller.
     */
    void setAccess(RoomAccessType access);

    /**
     * Updates the password value for this room contract.
     *
     * @param password Password value supplied by the caller.
     */
    void setPassword(String password);

    /**
     * Updates the score value for this room contract.
     *
     * @param score Score value supplied by the caller.
     */
    void setScore(int score);

    /**
     * Updates the tags value for this room contract.
     *
     * @param tags Tags value supplied by the caller.
     */
    void setTags(String[] tags);

    /**
     * Updates the decorations value for this room contract.
     *
     * @param decorations Decorations value supplied by the caller.
     */
    void setDecorations(Map<String, String> decorations);

    /**
     * Updates the model value for this room contract.
     *
     * @param model Model value supplied by the caller.
     */
    void setModel(String model);

    /**
     * Updates the hide walls value for this room contract.
     *
     * @param hideWalls Hide walls value supplied by the caller.
     */
    void setHideWalls(boolean hideWalls);

    /**
     * Updates the thickness wall value for this room contract.
     *
     * @param thicknessWall Thickness wall value supplied by the caller.
     */
    void setThicknessWall(int thicknessWall);

    /**
     * Updates the thickness floor value for this room contract.
     *
     * @param thicknessFloor Thickness floor value supplied by the caller.
     */
    void setThicknessFloor(int thicknessFloor);

    /**
     * Returns the allow walkthrough associated with this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean getAllowWalkthrough();

    /**
     * Indicates whether allow walkthrough is enabled for this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isAllowWalkthrough();

    /**
     * Updates the allow walkthrough value for this room contract.
     *
     * @param allowWalkthrough Allow walkthrough value supplied by the caller.
     */
    void setAllowWalkthrough(boolean allowWalkthrough);

    /**
     * Updates the heightmap value for this room contract.
     *
     * @param heightmap Heightmap value supplied by the caller.
     */
    void setHeightmap(String heightmap);

    /**
     * Returns the heightmap associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getHeightmap();

    /**
     * Indicates whether allow pets is enabled for this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isAllowPets();

    /**
     * Updates the allow pets value for this room contract.
     *
     * @param allowPets Allow pets value supplied by the caller.
     */
    void setAllowPets(boolean allowPets);

    /**
     * Returns the original password associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getOriginalPassword();

    /**
     * Updates the original password value for this room contract.
     *
     * @param originalPassword Original password value supplied by the caller.
     */
    void setOriginalPassword(String originalPassword);

    /**
     * Returns the trade state associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomTradeState getTradeState();

    /**
     * Updates the trade state value for this room contract.
     *
     * @param tradeState Trade state value supplied by the caller.
     */
    void setTradeState(RoomTradeState tradeState);

    /**
     * Returns the bubble mode associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getBubbleMode();

    /**
     * Updates the bubble mode value for this room contract.
     *
     * @param bubbleMode Bubble mode value supplied by the caller.
     */
    void setBubbleMode(int bubbleMode);

    /**
     * Returns the bubble type associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getBubbleType();

    /**
     * Updates the bubble type value for this room contract.
     *
     * @param bubbleType Bubble type value supplied by the caller.
     */
    void setBubbleType(int bubbleType);

    /**
     * Returns the bubble scroll associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getBubbleScroll();

    /**
     * Updates the bubble scroll value for this room contract.
     *
     * @param bubbleScroll Bubble scroll value supplied by the caller.
     */
    void setBubbleScroll(int bubbleScroll);

    /**
     * Returns the chat distance associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getChatDistance();

    /**
     * Updates the chat distance value for this room contract.
     *
     * @param chatDistance Chat distance value supplied by the caller.
     */
    void setChatDistance(int chatDistance);

    /**
     * Returns the anti flood settings associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getAntiFloodSettings();

    /**
     * Updates the anti flood settings value for this room contract.
     *
     * @param antiFloodSettings Anti flood settings value supplied by the caller.
     */
    void setAntiFloodSettings(int antiFloodSettings);

    /**
     * Returns the mute state associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomMuteState getMuteState();

    /**
     * Updates the mute state value for this room contract.
     *
     * @param muteState Mute state value supplied by the caller.
     */
    void setMuteState(RoomMuteState muteState);

    /**
     * Returns the kick state associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomKickState getKickState();

    /**
     * Updates the kick state value for this room contract.
     *
     * @param kickState Kick state value supplied by the caller.
     */
    void setKickState(RoomKickState kickState);

    /**
     * Returns the ban state associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomBanState getBanState();

    /**
     * Updates the ban state value for this room contract.
     *
     * @param banState Ban state value supplied by the caller.
     */
    void setBanState(RoomBanState banState);

    /**
     * Returns the disabled commands associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    List<String> getDisabledCommands();

    /**
     * Returns the group id associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getGroupId();

    /**
     * Returns the required badge associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getRequiredBadge();

    /**
     * Returns the type associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    RoomType getType();

    /**
     * Returns the thumbnail associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getThumbnail();

    /**
     * Updates the group id value for this room contract.
     *
     * @param groupId Group id value supplied by the caller.
     */
    void setGroupId(int groupId);

    /**
     * Returns the decoration string associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getDecorationString();

    /**
     * Updates the thumbnail value for this room contract.
     *
     * @param thumbnail Thumbnail value supplied by the caller.
     */
    void setThumbnail(String thumbnail);

    /**
     * Indicates whether wired hidden is enabled for this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isWiredHidden();

    /**
     * Returns the roller speed associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRollerSpeed();

    /**
     * Indicates whether this room contract has sorting.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasSorting();

    /**
     * Executes the advanced collision operation for this room contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean advancedCollision();

    /**
     * Updates the advanced collision value for this room contract.
     *
     * @param advancedCollision Advanced collision value supplied by the caller.
     */
    void setAdvancedCollision(boolean advancedCollision);

    /**
     * Updates the has entity sort value for this room contract.
     *
     * @param hasEntitySort Has entity sort value supplied by the caller.
     */
    void setHasEntitySort(boolean hasEntitySort);

    /**
     * Updates the roller speed value for this room contract.
     *
     * @param speed Speed value supplied by the caller.
     */
    void setRollerSpeed(int speed);

    /**
     * Updates the is wired hidden value for this room contract.
     *
     * @param hiddenWired Hidden wired value supplied by the caller.
     */
    void setIsWiredHidden(boolean hiddenWired);

    /**
     * Returns the user idle ticks associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getUserIdleTicks();

    /**
     * Updates the user idle ticks value for this room contract.
     *
     * @param ticks Ticks value supplied by the caller.
     */
    void setUserIdleTicks(int ticks);
}
