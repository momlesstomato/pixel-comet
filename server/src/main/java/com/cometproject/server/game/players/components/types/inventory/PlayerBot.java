package com.cometproject.server.game.players.components.types.inventory;

import com.cometproject.api.game.bots.IBotData;
import com.cometproject.api.game.players.data.components.bots.IPlayerBot;


/**
 * Describes player bot behavior for the player subsystem.
 */
public class PlayerBot implements IPlayerBot {
    private IBotData botData;

    /**
     * Creates a player bot instance for the player subsystem.
     *
     * @param botData Bot data supplied by the caller.
     */
    public PlayerBot(IBotData botData) {
        this.botData = botData;
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getId() {
        return this.botData.getId();
    }

    /**
     * Returns the bot data for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IBotData getBotData() {
        return this.botData;
    }
}
