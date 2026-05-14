package com.cometproject.games.snowwar.gameevents;

import com.cometproject.games.snowwar.gameobjects.HumanGameObject;

/**
 * Describes ball throw to position behavior for the Snow War game subsystem.
 */
public class BallThrowToPosition extends Event {
    public HumanGameObject attacker;
    public int x;
    public int y;
    public int type;

    /**
     * Creates a ball throw to position instance for the Snow War game subsystem.
     *
     * @param attacker Attacker supplied by the caller.
     * @param x X supplied by the caller.
     * @param y Y supplied by the caller.
     * @param type Type supplied by the caller.
     */
    public BallThrowToPosition(HumanGameObject attacker, int x, int y, int type) {
        this.EventType = 4;
        this.attacker = attacker;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Executes apply for this Snow War game contract.
     */
    public void apply() {
        this.attacker._vs(this.x, this.y);
        this.attacker.increaseFireLimiter();
    }
}