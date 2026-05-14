package com.cometproject.server.game.utilities.validator;

/**
 * Describes player figure color behavior for the Comet subsystem.
 */
public class PlayerFigureColor {
    private int clubCode;
    private boolean selectable;

    /**
     * Creates a player figure color instance for the Comet subsystem.
     *
     * @param clubCode Club code supplied by the caller.
     * @param selectable Selectable supplied by the caller.
     */
    public PlayerFigureColor(final int clubCode, final boolean selectable) {
        this.clubCode = clubCode;
        this.selectable = selectable;
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
     * Indicates whether selectable applies to this Comet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isSelectable() {
        return this.selectable;
    }

}

