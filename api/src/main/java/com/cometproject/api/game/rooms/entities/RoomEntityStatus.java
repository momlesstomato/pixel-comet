package com.cometproject.api.game.rooms.entities;

/**
 * Enumerates room entity status values used by the room subsystem.
 */
public enum RoomEntityStatus {
    SIT("sit"),
    MOVE("mv"),
    LAY("lay"),
    SIGN("sign"),
    CONTROLLER("flatctrl"),
    TRADE("trd"),
    VOTE("vote"),
    GESTURE("gst"),
    PLAY("pla"),
    PLAY_DEAD("ded"),
    JUMP("jmp"),
    EAT("eat"),
    SWIM("dip"),

    //Monster Plants status
    RIP("rip"),
    GROW("grw"),
    GROW_1("grw1"),
    GROW_2("grw2"),
    GROW_3("grw3"),
    GROW_4("grw4"),
    GROW_5("grw5"),
    GROW_6("grw6"),
    GROW_7("grw7"),
    FLASH("spd"),
    SAD("sad"),
    HAPPY("sml");

    private final String statusCode;

    RoomEntityStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Returns the status code for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public String getStatusCode() {
        return this.statusCode;
    }

    /**
     * Executes the from string operation for this room contract.
     *
     * @param key Key value supplied by the caller.
     * @return Result produced by the operation.
     */
    public static RoomEntityStatus fromString(String key) {
        for (RoomEntityStatus status : values()) {
            if (status.statusCode.equals(key)) {
                return status;
            }
        }

        return null;
    }
}
