package com.cometproject.api.game.players.data.components.bots;

import com.cometproject.api.game.bots.IBotData;

/**
 * Defines the i player bot contract for the player subsystem.
 */
public interface IPlayerBot {
    /**
     * Returns the id associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the bot data associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IBotData getBotData();
}
