package com.cometproject.storage.api.data.rooms;

import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.game.rooms.RoomType;
import com.cometproject.api.game.rooms.settings.*;

import java.util.List;
import java.util.Map;

/**
 * Describes room data behavior for the storage subsystem.
 */
public class RoomData implements IRoomData {

    private int id;
    private RoomType type;

    private String name;
    private String description;
    private int ownerId;
    private String owner;
    private int category;
    private int maxUsers;
    private RoomAccessType access;
    private String password;
    private String originalPassword;
    private RoomTradeState tradeState;

    private int score;

    private String[] tags;
    private Map<String, String> decorations;

    private String model;

    private boolean hideWalls;
    private int thicknessWall;
    private int thicknessFloor;
    private boolean allowWalkthrough;
    private boolean allowPets;
    private String heightmap;

    private RoomMuteState muteState;
    private RoomKickState kickState;
    private RoomBanState banState;

    private int bubbleMode;
    private int bubbleType;
    private int bubbleScroll;
    private int chatDistance;

    private int antiFloodSettings;

    private List<String> disabledCommands;

    private int groupId;
    private int rollerSpeed;

    private String requiredBadge;
    private String thumbnail;

    private boolean wiredHidden;
    private boolean hasEntitySort;
    private boolean advancedCollision;

    private int userIdleTicks;

    public boolean isOnSale = false;
    public int roomPrice = 0;
    public boolean funCommands = true;

    /**
     * Creates a room data instance for the room subsystem.
     *
     * @param id Id value supplied by the caller.
     * @param type Type value supplied by the caller.
     * @param name Name value supplied by the caller.
     * @param description Description value supplied by the caller.
     * @param ownerId Owner id value supplied by the caller.
     * @param owner Owner value supplied by the caller.
     * @param category Category value supplied by the caller.
     * @param maxUsers Max users value supplied by the caller.
     * @param access Access value supplied by the caller.
     * @param password Password value supplied by the caller.
     * @param originalPassword Original password value supplied by the caller.
     * @param tradeState Trade state value supplied by the caller.
     * @param score Score value supplied by the caller.
     * @param tags Tags value supplied by the caller.
     * @param decorations Decorations value supplied by the caller.
     * @param model Model value supplied by the caller.
     * @param hideWalls Hide walls value supplied by the caller.
     * @param thicknessWall Thickness wall value supplied by the caller.
     * @param thicknessFloor Thickness floor value supplied by the caller.
     * @param allowWalkthrough Allow walkthrough value supplied by the caller.
     * @param allowPets Allow pets value supplied by the caller.
     * @param heightmap Heightmap value supplied by the caller.
     * @param muteState Mute state value supplied by the caller.
     * @param kickState Kick state value supplied by the caller.
     * @param banState Ban state value supplied by the caller.
     * @param bubbleMode Bubble mode value supplied by the caller.
     * @param bubbleType Bubble type value supplied by the caller.
     * @param bubbleScroll Bubble scroll value supplied by the caller.
     * @param chatDistance Chat distance value supplied by the caller.
     * @param antiFloodSettings Anti flood settings value supplied by the caller.
     * @param disabledCommands Disabled commands value supplied by the caller.
     * @param groupId Group id value supplied by the caller.
     * @param requiredBadge Required badge value supplied by the caller.
     * @param thumbnail Thumbnail value supplied by the caller.
     * @param wiredHidden Wired hidden value supplied by the caller.
     * @param userIdleTicks User idle ticks value supplied by the caller.
     * @param rollerSpeed Roller speed value supplied by the caller.
     * @param hasEntitySort Has entity sort value supplied by the caller.
     * @param advancedCollision Advanced collision value supplied by the caller.
     */
    public RoomData(int id, RoomType type, String name, String description, int ownerId, String owner, int category,
                    int maxUsers, RoomAccessType access, String password, String originalPassword,
                    RoomTradeState tradeState, int score, String[] tags, Map<String, String> decorations,
                    String model, boolean hideWalls, int thicknessWall, int thicknessFloor, boolean allowWalkthrough,
                    boolean allowPets, String heightmap, RoomMuteState muteState, RoomKickState kickState,
                    RoomBanState banState, int bubbleMode, int bubbleType, int bubbleScroll, int chatDistance,
                    int antiFloodSettings, List<String> disabledCommands, int groupId,
                    String requiredBadge, String thumbnail, boolean wiredHidden, int userIdleTicks, int rollerSpeed, boolean hasEntitySort, boolean advancedCollision) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.owner = owner;
        this.category = category;
        this.maxUsers = maxUsers;
        this.access = access;
        this.password = password;
        this.originalPassword = originalPassword;
        this.tradeState = tradeState;
        this.score = score;
        this.tags = tags;
        this.decorations = decorations;
        this.model = model;
        this.hideWalls = hideWalls;
        this.thicknessWall = thicknessWall;
        this.thicknessFloor = thicknessFloor;
        this.allowWalkthrough = allowWalkthrough;
        this.allowPets = allowPets;
        this.heightmap = heightmap;
        this.muteState = muteState;
        this.kickState = kickState;
        this.banState = banState;
        this.bubbleMode = bubbleMode;
        this.bubbleType = bubbleType;
        this.bubbleScroll = bubbleScroll;
        this.chatDistance = chatDistance;
        this.antiFloodSettings = antiFloodSettings;
        this.disabledCommands = disabledCommands;
        this.groupId = groupId;
        this.requiredBadge = requiredBadge;
        this.thumbnail = thumbnail;
        this.wiredHidden = wiredHidden;
        this.userIdleTicks = userIdleTicks;
        this.rollerSpeed = rollerSpeed;
        this.hasEntitySort = hasEntitySort;
        this.advancedCollision = advancedCollision;
    }

    /**
     * Returns the id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return id;
    }

    /**
     * Updates the id for this storage contract.
     *
     * @param id Id supplied by the caller.
     */
    @Override
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the type for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomType getType() {
        return type;
    }

    /**
     * Updates the type for this storage contract.
     *
     * @param type Type supplied by the caller.
     */
    public void setType(RoomType type) {
        this.type = type;
    }

    /**
     * Returns the name for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Updates the name for this storage contract.
     *
     * @param name Name supplied by the caller.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDescription() {
        return description;
    }

    /**
     * Updates the description for this storage contract.
     *
     * @param description Description supplied by the caller.
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the owner id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Updates the owner id for this storage contract.
     *
     * @param ownerId Owner id supplied by the caller.
     */
    @Override
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Returns the owner for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getOwner() {
        return owner;
    }

    /**
     * Updates the owner for this storage contract.
     *
     * @param owner Owner supplied by the caller.
     */
    @Override
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Returns the category id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getCategoryId() {
        return category;
    }

    /**
     * Updates the category id for this storage contract.
     *
     * @param category Category supplied by the caller.
     */
    @Override
    public void setCategoryId(int category) {
        this.category = category;
    }

    /**
     * Returns the max users for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getMaxUsers() {
        return maxUsers;
    }

    /**
     * Updates the max users for this storage contract.
     *
     * @param maxUsers Max users supplied by the caller.
     */
    @Override
    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    /**
     * Returns the access for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomAccessType getAccess() {
        return access;
    }

    /**
     * Updates the access for this storage contract.
     *
     * @param access Access supplied by the caller.
     */
    @Override
    public void setAccess(RoomAccessType access) {
        this.access = access;
    }

    /**
     * Returns the password for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Updates the password for this storage contract.
     *
     * @param password Password supplied by the caller.
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the original password for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getOriginalPassword() {
        return originalPassword;
    }

    /**
     * Updates the original password for this storage contract.
     *
     * @param originalPassword Original password supplied by the caller.
     */
    @Override
    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    /**
     * Returns the trade state for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomTradeState getTradeState() {
        return tradeState;
    }

    /**
     * Updates the trade state for this storage contract.
     *
     * @param tradeState Trade state supplied by the caller.
     */
    @Override
    public void setTradeState(RoomTradeState tradeState) {
        this.tradeState = tradeState;
    }

    /**
     * Returns the score for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Updates the score for this storage contract.
     *
     * @param score Score supplied by the caller.
     */
    @Override
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns the tags for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String[] getTags() {
        return tags;
    }

    /**
     * Updates the tags for this storage contract.
     *
     * @param tags Tags supplied by the caller.
     */
    @Override
    public void setTags(String[] tags) {
        this.tags = tags;
    }

    /**
     * Returns the decorations for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<String, String> getDecorations() {
        return decorations;
    }

    /**
     * Updates the decorations for this storage contract.
     *
     * @param decorations Decorations supplied by the caller.
     */
    @Override
    public void setDecorations(Map<String, String> decorations) {
        this.decorations = decorations;
    }

    /**
     * Returns the model for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getModel() {
        return model;
    }

    /**
     * Updates the model for this storage contract.
     *
     * @param model Model supplied by the caller.
     */
    @Override
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Returns the hide walls for this storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean getHideWalls() {
        return hideWalls;
    }

    /**
     * Returns the wall thickness for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getWallThickness() {
        return this.thicknessWall;
    }

    /**
     * Returns the floor thickness for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getFloorThickness() {
        return this.thicknessFloor;
    }

    /**
     * Updates the hide walls for this storage contract.
     *
     * @param hideWalls Hide walls supplied by the caller.
     */
    @Override
    public void setHideWalls(boolean hideWalls) {
        this.hideWalls = hideWalls;
    }

    /**
     * Updates the thickness wall for this storage contract.
     *
     * @param thicknessWall Thickness wall supplied by the caller.
     */
    @Override
    public void setThicknessWall(int thicknessWall) {
        this.thicknessWall = thicknessWall;
    }

    /**
     * Updates the thickness floor for this storage contract.
     *
     * @param thicknessFloor Thickness floor supplied by the caller.
     */
    @Override
    public void setThicknessFloor(int thicknessFloor) {
        this.thicknessFloor = thicknessFloor;
    }

    /**
     * Returns the allow walkthrough for this storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean getAllowWalkthrough() {
        return this.allowWalkthrough;
    }

    /**
     * Indicates whether allow walkthrough applies to this storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isAllowWalkthrough() {
        return allowWalkthrough;
    }

    /**
     * Updates the allow walkthrough for this storage contract.
     *
     * @param allowWalkthrough Allow walkthrough supplied by the caller.
     */
    @Override
    public void setAllowWalkthrough(boolean allowWalkthrough) {
        this.allowWalkthrough = allowWalkthrough;
    }

    /**
     * Indicates whether allow pets applies to this storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isAllowPets() {
        return allowPets;
    }

    /**
     * Updates the allow pets for this storage contract.
     *
     * @param allowPets Allow pets supplied by the caller.
     */
    @Override
    public void setAllowPets(boolean allowPets) {
        this.allowPets = allowPets;
    }

    /**
     * Returns the heightmap for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getHeightmap() {
        return heightmap;
    }

    /**
     * Updates the heightmap for this storage contract.
     *
     * @param heightmap Heightmap supplied by the caller.
     */
    @Override
    public void setHeightmap(String heightmap) {
        this.heightmap = heightmap;
    }

    /**
     * Returns the mute state for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomMuteState getMuteState() {
        return muteState;
    }

    /**
     * Updates the mute state for this storage contract.
     *
     * @param muteState Mute state supplied by the caller.
     */
    @Override
    public void setMuteState(RoomMuteState muteState) {
        this.muteState = muteState;
    }

    /**
     * Returns the kick state for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomKickState getKickState() {
        return kickState;
    }

    /**
     * Updates the kick state for this storage contract.
     *
     * @param kickState Kick state supplied by the caller.
     */
    @Override
    public void setKickState(RoomKickState kickState) {
        this.kickState = kickState;
    }

    /**
     * Returns the ban state for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public RoomBanState getBanState() {
        return banState;
    }

    /**
     * Updates the ban state for this storage contract.
     *
     * @param banState Ban state supplied by the caller.
     */
    @Override
    public void setBanState(RoomBanState banState) {
        this.banState = banState;
    }

    /**
     * Returns the bubble mode for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getBubbleMode() {
        return bubbleMode;
    }

    /**
     * Updates the bubble mode for this storage contract.
     *
     * @param bubbleMode Bubble mode supplied by the caller.
     */
    @Override
    public void setBubbleMode(int bubbleMode) {
        this.bubbleMode = bubbleMode;
    }

    /**
     * Returns the bubble type for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getBubbleType() {
        return bubbleType;
    }

    /**
     * Updates the bubble type for this storage contract.
     *
     * @param bubbleType Bubble type supplied by the caller.
     */
    @Override
    public void setBubbleType(int bubbleType) {
        this.bubbleType = bubbleType;
    }

    /**
     * Returns the bubble scroll for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getBubbleScroll() {
        return bubbleScroll;
    }

    /**
     * Updates the bubble scroll for this storage contract.
     *
     * @param bubbleScroll Bubble scroll supplied by the caller.
     */
    @Override
    public void setBubbleScroll(int bubbleScroll) {
        this.bubbleScroll = bubbleScroll;
    }

    /**
     * Returns the chat distance for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getChatDistance() {
        return chatDistance;
    }

    /**
     * Updates the chat distance for this storage contract.
     *
     * @param chatDistance Chat distance supplied by the caller.
     */
    @Override
    public void setChatDistance(int chatDistance) {
        this.chatDistance = chatDistance;
    }

    /**
     * Returns the anti flood settings for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getAntiFloodSettings() {
        return antiFloodSettings;
    }

    /**
     * Updates the anti flood settings for this storage contract.
     *
     * @param antiFloodSettings Anti flood settings supplied by the caller.
     */
    @Override
    public void setAntiFloodSettings(int antiFloodSettings) {
        this.antiFloodSettings = antiFloodSettings;
    }

    /**
     * Returns the disabled commands for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public List<String> getDisabledCommands() {
        return disabledCommands;
    }

    /**
     * Returns the group id for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getGroupId() {
        return groupId;
    }

    /**
     * Updates the group id for this storage contract.
     *
     * @param groupId Group id supplied by the caller.
     */
    @Override
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Returns the decoration string for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getDecorationString() {
        StringBuilder decorString = new StringBuilder();

        for (Map.Entry<String, String> decoration : this.getDecorations().entrySet()) {
            decorString.append(decoration.getKey()).append("=").append(decoration.getValue()).append(",");
        }

        return decorString.toString();
    }

    /**
     * Returns the required badge for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getRequiredBadge() {
        return requiredBadge;
    }

    /**
     * Returns the thumbnail for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * Updates the thumbnail for this storage contract.
     *
     * @param thumbnail Thumbnail supplied by the caller.
     */
    @Override
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * Returns the roller speed for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRollerSpeed() {
        return rollerSpeed;
    }

    /**
     * Updates the roller speed for this storage contract.
     *
     * @param rollerSpeed Roller speed supplied by the caller.
     */
    @Override
    public void setRollerSpeed(int rollerSpeed) {
        this.rollerSpeed = rollerSpeed;
    }

    /**
     * Indicates whether wired hidden applies to this storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isWiredHidden() {
        return wiredHidden;
    }

    /**
     * Updates the is wired hidden for this storage contract.
     *
     * @param hiddenWired Hidden wired supplied by the caller.
     */
    @Override
    public void setIsWiredHidden(boolean hiddenWired) {
        this.wiredHidden = hiddenWired;
    }

    /**
     * Returns the user idle ticks for this storage contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getUserIdleTicks() {
        return this.userIdleTicks;
    }

    /**
     * Updates the user idle ticks for this storage contract.
     *
     * @param ticks Ticks supplied by the caller.
     */
    @Override
    public void setUserIdleTicks(int ticks) {
        this.userIdleTicks = ticks;
    }

    /**
     * Executes has sorting for this storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasSorting() {
        return hasEntitySort;
    }

    /**
     * Updates the advanced collision for this storage contract.
     *
     * @param advancedCollision Advanced collision supplied by the caller.
     */
    @Override
    public void setAdvancedCollision(boolean advancedCollision) {
        this.advancedCollision = advancedCollision;
    }

    /**
     * Updates the has entity sort for this storage contract.
     *
     * @param hasEntitySort Has entity sort supplied by the caller.
     */
    @Override
    public void setHasEntitySort(boolean hasEntitySort) {
        this.hasEntitySort = hasEntitySort;
    }

    /**
     * Executes advanced collision for this storage contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean advancedCollision() {
        return advancedCollision;
    }
}
