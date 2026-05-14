package com.cometproject.server.game.utilities.validator;

/**
 * Describes player figure set behavior for the Comet subsystem.
 */
public class PlayerFigureSet {
    private String gender;
    private int clubCode;
    private boolean colorable;
    private boolean selectable;
    private int colorCount;

    /**
     * Creates a player figure set instance for the Comet subsystem.
     *
     * @param gender Gender supplied by the caller.
     * @param clubCode Club code supplied by the caller.
     * @param colorable Colorable supplied by the caller.
     * @param selectable Selectable supplied by the caller.
     * @param colorCount Color count supplied by the caller.
     */
    public PlayerFigureSet(final String gender, final int clubCode, final boolean colorable, final boolean selectable, final int colorCount) {
        this.gender = gender;
        this.clubCode = clubCode;
        this.colorable = colorable;
        this.selectable = selectable;
        this.colorCount = colorCount;
    }

    /**
     * Returns the gender for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGender() {
        return this.gender;
    }

    /**
     * Returns the club code for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getClubCode() {
        return this.clubCode;
    }

    /**
     * Indicates whether colorable applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isColorable() {
        return this.colorable;
    }

    /**
     * Indicates whether selectable applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isSelectable() {
        return this.selectable;
    }

    /**
     * Returns the color count for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getColorCount() {
        return this.colorCount;
    }
}

