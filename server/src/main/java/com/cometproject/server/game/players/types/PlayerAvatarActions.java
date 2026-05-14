package com.cometproject.server.game.players.types;

/**
 * Enumerates player avatar actions values used by the player subsystem.
 */
public enum PlayerAvatarActions {
    EXPRESSION_WAVE(1),
    EXPRESSION_BLOW_A_KISS(2),
    EXPRESSION_LAUGH(3),
    EXPRESSION_CRY(4),
    EXPRESSION_IDLE(5),
    DANCE(6),
    EXPRESSION_RESPECT(7),
    EXPRESSION_SNOWBOARD_OLLIE(8),
    EXPRESSION_SNOWBORD_360(9),
    EXPRESSION_RIDE_JUMP(10);

    private final int id;

    private PlayerAvatarActions(int id) {
        this.id = id;
    }

    /**
     * Returns the value for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getValue() {
        return this.id;
    }
}

