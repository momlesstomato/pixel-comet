package com.cometproject.games.snowwar.gameevents;
import com.cometproject.games.snowwar.gameobjects.HumanGameObject;
/**
 * Describes player left behavior for the Snow War game subsystem.
 */
public class PlayerLeft extends Event {
    public HumanGameObject player;

    /**
     * Creates a player left instance for the Snow War game subsystem.
     *
     * @param player Player participating in the operation.
     */
    public PlayerLeft(HumanGameObject player) {
        this.EventType = 1;
        this.player = player;
    }

    /**
     * Executes apply for this Snow War game contract.
     */
    public void apply() {
        this.player.currentSnowWar.queueDeleteObject(this.player);
        this.player.cleanTiles();
    }
}