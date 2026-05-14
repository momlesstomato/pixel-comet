package com.cometproject.api.game.rooms.settings;

/**
 * Enumerates room kick state values used by the room subsystem.
 */
public enum RoomKickState {
    EVERYONE(2),
    RIGHTS(1),
    NONE(0);

    private int state;

    RoomKickState(int state) {
        this.state = state;
    }

    /**
     * Returns the state for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getState() {
        return this.state;
    }

    /**
     * Executes the value of operation for this room contract.
     *
     * @param state State value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static RoomKickState valueOf(int state) {
        if (state == 0) return NONE;
        else if (state == 1) return RIGHTS;
        else return EVERYONE;
    }
}
