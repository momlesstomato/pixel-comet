package com.cometproject.server.game.rooms.types.components.games.casino.types;

import com.cometproject.server.game.players.types.Player;

/**
 * Describes casino bets behavior for the room processing subsystem.
 */
public class CasinoBets {
    private final Player player;
    private final int bet;
    private final int amount;
    private boolean paid;

    /**
     * Creates a casino bets instance for the room processing subsystem.
     *
     * @param player Player participating in the operation.
     * @param bet Bet supplied by the caller.
     * @param amount Amount supplied by the caller.
     * @param paid Paid supplied by the caller.
     */
    public CasinoBets(Player player, int bet, int amount, boolean paid) {
        this.player = player;
        this.bet = bet;
        this.amount = amount;
        this.paid = paid;
    }

    /**
     * Returns the player for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Returns the amount for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getAmount() { return amount; }

    /**
     * Returns the bet for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBet() { return bet; }

    /**
     * Returns the player id for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPlayerId() {
        return this.player.getData().getId();
    }

    /**
     * Updates the paid for this room processing contract.
     */
    public void setPaid(){
        this.paid = true;
    }

    /**
     * Indicates whether paid applies to this room processing contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isPaid() {
        return paid;
    }
}
