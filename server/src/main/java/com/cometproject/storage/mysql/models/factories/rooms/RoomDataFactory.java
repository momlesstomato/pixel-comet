package com.cometproject.storage.mysql.models.factories.rooms;

import com.cometproject.api.game.rooms.IRoomData;
import com.cometproject.api.game.rooms.RoomType;
import com.cometproject.api.game.rooms.settings.*;
import com.cometproject.storage.api.data.rooms.RoomData;

import java.util.List;
import java.util.Map;

/**
 * Creates room data instances for the MySQL storage subsystem.
 */
public class RoomDataFactory {

    /**
     * Creates room data for this MySQL storage contract.
     *
     * @param id Id supplied by the caller.
     * @param type Type supplied by the caller.
     * @param name Name supplied by the caller.
     * @param description Description supplied by the caller.
     * @param ownerId Owner id supplied by the caller.
     * @param owner Owner supplied by the caller.
     * @param category Category supplied by the caller.
     * @param maxUsers Max users supplied by the caller.
     * @param access Access supplied by the caller.
     * @param password Password supplied by the caller.
     * @param originalPassword Original password supplied by the caller.
     * @param tradeState Trade state supplied by the caller.
     * @param score Score supplied by the caller.
     * @param tags Tags supplied by the caller.
     * @param decorations Decorations supplied by the caller.
     * @param model Model supplied by the caller.
     * @param hideWalls Hide walls supplied by the caller.
     * @param thicknessWall Thickness wall supplied by the caller.
     * @param thicknessFloor Thickness floor supplied by the caller.
     * @param allowWalkthrough Allow walkthrough supplied by the caller.
     * @param allowPets Allow pets supplied by the caller.
     * @param heightmap Heightmap supplied by the caller.
     * @param muteState Mute state supplied by the caller.
     * @param kickState Kick state supplied by the caller.
     * @param banState Ban state supplied by the caller.
     * @param bubbleMode Bubble mode supplied by the caller.
     * @param bubbleType Bubble type supplied by the caller.
     * @param bubbleScroll Bubble scroll supplied by the caller.
     * @param chatDistance Chat distance supplied by the caller.
     * @param antiFloodSettings Anti flood settings supplied by the caller.
     * @param disabledCommands Disabled commands supplied by the caller.
     * @param groupId Group id supplied by the caller.
     * @param requiredBadge Required badge supplied by the caller.
     * @param thumbnail Thumbnail supplied by the caller.
     * @param wiredHidden Wired hidden supplied by the caller.
     * @param userIdleTicks User idle ticks supplied by the caller.
     * @param rollerSpeed Roller speed supplied by the caller.
     * @param hasEntitySort Has entity sort supplied by the caller.
     * @param advancedCollision Advanced collision supplied by the caller.
     * @return Value exposed by the contract.
     */
    public IRoomData createRoomData(int id, RoomType type, String name, String description, int ownerId, String owner,
                                    int category, int maxUsers, RoomAccessType access, String password,
                                    String originalPassword, RoomTradeState tradeState, int score, String[] tags,
                                    Map<String, String> decorations, String model, boolean hideWalls, int thicknessWall,
                                    int thicknessFloor, boolean allowWalkthrough, boolean allowPets, String heightmap,
                                    RoomMuteState muteState, RoomKickState kickState, RoomBanState banState,
                                    int bubbleMode, int bubbleType, int bubbleScroll, int chatDistance,
                                    int antiFloodSettings, List<String> disabledCommands, int groupId,String requiredBadge,
                                    String thumbnail, boolean wiredHidden, int userIdleTicks, int rollerSpeed, boolean hasEntitySort, boolean advancedCollision) {

        return new RoomData(id, type, name, description, ownerId, owner, category, maxUsers, access, password,
                originalPassword, tradeState, score, tags, decorations, model, hideWalls, thicknessWall, thicknessFloor,
                allowWalkthrough, allowPets, heightmap, muteState, kickState, banState, bubbleMode, bubbleType,
                bubbleScroll, chatDistance, antiFloodSettings, disabledCommands, groupId,
                requiredBadge, thumbnail, wiredHidden, userIdleTicks, rollerSpeed, hasEntitySort, advancedCollision);
    }
}
