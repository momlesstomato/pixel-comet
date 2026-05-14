package com.cometproject.server.game.players.types.roleplay;

/**
 * Describes role play attribute behavior for the player subsystem.
 */
public class RolePlayAttribute {
    private final int id;
    private String type;
    private int time;
    private String extradata;

    /**
     * Creates a role play attribute instance for the player subsystem.
     *
     * @param id Id supplied by the caller.
     * @param type Type supplied by the caller.
     * @param time Time supplied by the caller.
     * @param extradata Extradata supplied by the caller.
     */
    public RolePlayAttribute(final int id, String type, int time, String extradata){
        this.id = id;
        this.type = type;
        this.time = time;
        this.extradata = extradata;
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the type for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getType() {
        return type;
    }

    /**
     * Updates the type for this player contract.
     *
     * @param type Type supplied by the caller.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the time for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTime() {
        return time;
    }

    /**
     * Updates the time for this player contract.
     *
     * @param time Time supplied by the caller.
     */
    public void setTime(int time) {
        this.time = time;
    }

    /**
     * Returns the extradata for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getExtradata() {
        return extradata;
    }

    /**
     * Updates the extradata for this player contract.
     *
     * @param extradata Extradata supplied by the caller.
     */
    public void setExtradata(String extradata) {
        this.extradata = extradata;
    }

}
