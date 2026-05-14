package com.cometproject.server.game.players.components;

import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.players.types.PlayerComponent;
import com.cometproject.server.storage.queries.player.PlayerDao;

/**
 * Owns rentable behavior inside the player subsystem.
 */
public class RentableComponent extends PlayerComponent {
    private int rentedId;
    private int expiracy;
    private int cost;

    /**
     * Creates a rentable component instance for the player subsystem.
     *
     * @param p P supplied by the caller.
     */
    public RentableComponent(Player p){
        super(p);

        flush();
    }

    /**
     * Executes flush for this player contract.
     */
    public void flush(){
        this.rentedId = PlayerDao.getRentableId(player.getData().getId());
    }

    /**
     * Indicates whether this player contract has rent.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean hasRent() {
        return rentedId > 0;
    }

    /**
     * Updates the rented id for this player contract.
     *
     * @param i I supplied by the caller.
     */
    public void setRentedId(int i){
        this.rentedId = i;
    }

    /**
     * Returns the rented id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRentedId() {
        return rentedId;
    }
}
