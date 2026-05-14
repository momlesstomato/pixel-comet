package com.cometproject.api.game.players.data;


import com.cometproject.api.game.players.IPlayer;

/**
 * Defines the i player component contract for the player subsystem.
 */
public interface IPlayerComponent {
    /**
     * Returns the player associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IPlayer getPlayer();

    /**
     * Executes the dispose operation for this player contract.
     */
    void dispose();
}
