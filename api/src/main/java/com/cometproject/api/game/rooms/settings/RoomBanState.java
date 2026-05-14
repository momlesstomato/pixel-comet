package com.cometproject.api.game.rooms.settings;

/**
 * Enumerates room ban state values used by the room subsystem.
 */
public enum RoomBanState {
    NONE(0),
    RIGHTS(1);

    private int state;

    RoomBanState(int state) {
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
    public static RoomBanState valueOf(int state) {
        if (state == 0) return NONE;
        else return RIGHTS;
    }
}
