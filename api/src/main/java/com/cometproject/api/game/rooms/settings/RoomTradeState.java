package com.cometproject.api.game.rooms.settings;

/**
 * Enumerates room trade state values used by the room subsystem.
 */
public enum RoomTradeState {
    DISABLED(0),
    ENABLED(2),
    OWNER_ONLY(1);

    private int state;

    RoomTradeState(int state) {
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
    public static RoomTradeState valueOf(int state) {
        if (state == 0) return DISABLED;
        else if (state == 2) return ENABLED;
        else return OWNER_ONLY;
    }
}
