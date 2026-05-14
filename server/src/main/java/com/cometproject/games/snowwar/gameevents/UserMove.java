package com.cometproject.games.snowwar.gameevents;
import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
/**
 * Describes user move behavior for the Snow War game subsystem.
 */
public class UserMove extends Event {
    public HumanGameObject player;
    public int x;
    public int y;

    /**
     * Creates a user move instance for the Snow War game subsystem.
     *
     * @param player Player participating in the operation.
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     */
    public UserMove(HumanGameObject player, int x, int y) {
        this.EventType = 2;
        this.player = player;
        this.x = x;
        this.y = y;
    }

    /**
     * Executes apply for this Snow War game contract.
     */
    public void apply() {
        this.player.setMove(this.x, this.y);
    }
}