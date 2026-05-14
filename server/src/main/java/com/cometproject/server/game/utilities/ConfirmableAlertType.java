package com.cometproject.server.game.utilities;

/**
 * Enumerates confirmable alert type values used by the Comet subsystem.
 */
public enum ConfirmableAlertType {
    MINIGAME(1),
    RP_PACKAGE(2);

    private final int id;
    ConfirmableAlertType(int id) {
        this.id = id;
    }
    /**
     * Returns the id for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return this.id;
    }
}

