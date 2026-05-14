package com.cometproject.server.game.players.types;

import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.IPlayerComponent;

/**
 * Owns player behavior inside the player subsystem.
 */
public abstract class PlayerComponent implements IPlayerComponent {
    public final IPlayer player;

    /**
     * Creates a player component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public PlayerComponent(IPlayer player) {
        this.player = player;
    }

    /**
     * Releases resources owned by this player component.
     */
    public void dispose() {
    }

    /**
     * Returns the player for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public IPlayer getPlayer() {
        return this.player;
    }
}
