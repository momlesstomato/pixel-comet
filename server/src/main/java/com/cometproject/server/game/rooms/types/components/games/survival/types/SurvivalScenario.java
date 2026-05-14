package com.cometproject.server.game.rooms.types.components.games.survival.types;

/**
 * Describes survival scenario behavior for the room processing subsystem.
 */
public class SurvivalScenario {
    public int roomId;
    public boolean availability;

    /**
     * Creates a survival scenario instance for the room processing subsystem.
     *
     * @param roomId Room identifier used by the operation.
     * @param availability Availability supplied by the caller.
     */
    public SurvivalScenario(int roomId, boolean availability){
        this.roomId = roomId;
        this.availability = availability;
    }

    /**
     * Updates availability for this room processing contract.
     *
     * @param status Status supplied by the caller.
     */
    public void updateAvailability(boolean status){
        this.availability = status;
    }
}
