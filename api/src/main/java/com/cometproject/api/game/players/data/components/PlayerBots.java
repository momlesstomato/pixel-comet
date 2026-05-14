package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.bots.IBotData;

import java.util.Map;

/**
 * Defines the player bots contract for the player subsystem.
 */
public interface PlayerBots {
    /**
     * Executes the remove operation for this player contract.
     *
     * @param id Id value supplied by the caller.
     */
    void remove(int id);

    /**
     * Indicates whether bot is enabled for this player contract.
     *
     * @param id Id value supplied by the caller.
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isBot(int id);

    /**
     * Returns the bot associated with this player contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IBotData getBot(int id);

    /**
     * Adds bot data to this player contract.
     *
     * @param bot Bot value supplied by the caller.
     */
    void addBot(IBotData bot);

    /**
     * Returns the bots associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, IBotData> getBots();

    /**
     * Executes the clear bots operation for this player contract.
     */
    void clearBots();
}
