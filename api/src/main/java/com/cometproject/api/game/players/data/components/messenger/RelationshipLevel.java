package com.cometproject.api.game.players.data.components.messenger;

/**
 * Enumerates relationship level values used by the player subsystem.
 */
public enum RelationshipLevel {
    BOBBA(3),
    SMILE(2),
    HEART(1);

    private int levelId;

    RelationshipLevel(int id) {
        this.levelId = id;
    }

    /**
     * Returns the level id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLevelId() {
        return levelId;
    }
}
