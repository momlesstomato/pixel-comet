package com.cometproject.server.game.rooms.objects.entities.types.enums;

/**
 * Enumerates room controller level values used by the room subsystem.
 */
public enum RoomControllerLevel {
    NONE(0),
    GUEST(1),
    GUILD_MEMBER(2),
    GUILD_ADMIN(3),
    ROOM_OWNER(4),
    MODERATOR(5);

    private final int level;

    RoomControllerLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the level for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLevel() {
        return level;
    }
}