package com.cometproject.server.game.players.components;

import com.cometproject.api.game.bots.IBotData;
import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.components.PlayerBots;
import com.cometproject.server.game.players.types.PlayerComponent;
import com.cometproject.server.storage.queries.bots.PlayerBotDao;

import java.util.Map;


/**
 * Owns inventory bot behavior inside the player subsystem.
 */
public class InventoryBotComponent extends PlayerComponent implements PlayerBots {
    private Map<Integer, IBotData> bots;

    /**
     * Creates a inventory bot component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public InventoryBotComponent(IPlayer player) {
        super(player);

        this.bots = PlayerBotDao.getBotsByPlayerId(player.getId());
    }

    /**
     * Adds bot to this player contract.
     *
     * @param bot Bot supplied by the caller.
     */
    @Override
    public void addBot(IBotData bot) {
        this.bots.put(bot.getId(), bot);

        this.getPlayer().flush();
    }

    /**
     * Returns the bot for this player contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IBotData getBot(int id) {
        if (this.bots.containsKey(id)) {
            return this.bots.get(id);
        }

        return null;
    }

    /**
     * Executes remove for this player contract.
     *
     * @param id Id supplied by the caller.
     */
    @Override
    public void remove(int id) {
        this.bots.remove(id);

        this.getPlayer().flush();
    }

    /**
     * Indicates whether bot applies to this player contract.
     *
     * @param id Id supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isBot(int id) {
        return this.bots.containsKey(id);
    }

    /**
     * Returns the bots for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<Integer, IBotData> getBots() {
        return this.bots;
    }

    /**
     * Executes clear bots for this player contract.
     */
    @Override
    public void clearBots() {
        this.bots.clear();

        this.getPlayer().flush();
    }

    /**
     * Releases resources owned by this player component.
     */
    @Override
    public void dispose() {
        super.dispose();

        this.bots.clear();
        this.bots = null;
    }
}
