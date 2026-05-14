package com.cometproject.games.snowwar.gameevents;

import com.cometproject.games.snowwar.gameobjects.HumanGameObject;

/**
 * Describes ball throw to human behavior for the Snow War game subsystem.
 */
public class BallThrowToHuman extends Event {
    public HumanGameObject attacker;
    public HumanGameObject victim;
    public int type;

    /**
     * Creates a ball throw to human instance for the Snow War game subsystem.
     *
     * @param attacker Attacker supplied by the caller.
     * @param victim Victim supplied by the caller.
     * @param type Type supplied by the caller.
     */
    public BallThrowToHuman(HumanGameObject attacker, HumanGameObject victim, int type) {
        this.EventType = 3;
        this.attacker = attacker;
        this.victim = victim;
        this.type = type;
    }

    /**
     * Executes apply for this Snow War game contract.
     */
    public void apply() {
        this.attacker._vs(this.victim.location3D().x(), this.victim.location3D().y());
        this.attacker.increaseFireLimiter();
    }
}
