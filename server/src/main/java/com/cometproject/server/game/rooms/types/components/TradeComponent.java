package com.cometproject.server.game.rooms.types.components;

import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;
import com.cometproject.server.game.rooms.types.Room;
import com.cometproject.server.game.rooms.types.components.types.Trade;

import java.util.ArrayList;
import java.util.List;


/**
 * Owns trade behavior inside the room processing subsystem.
 */
public class TradeComponent {
    private final Room room;
    private List<Trade> trades;

    /**
     * Creates a trade component instance for the room processing subsystem.
     *
     * @param room Room participating in the operation.
     */
    public TradeComponent(Room room) {
        this.room = room;
        this.trades = new ArrayList<>();
    }

    /**
     * Executes add for this room processing contract.
     *
     * @param trade Trade supplied by the caller.
     */
    public void add(Trade trade) {
        trade.setTradeComponent(this);

        this.trades.add(trade);
    }

    /**
     * Executes get for this room processing contract.
     *
     * @param client Client supplied by the caller.
     * @return Value exposed by the contract.
     */
    public Trade get(PlayerEntity client) {
        for (Trade trade : this.getTrades()) {
            if (trade.getUser1() == client || trade.getUser2() == client)
                return trade;
        }

        return null;
    }

    /**
     * Executes remove for this room processing contract.
     *
     * @param trade Trade supplied by the caller.
     */
    public void remove(Trade trade) {
        this.trades.remove(trade);
    }

    /**
     * Returns the trades for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public synchronized List<Trade> getTrades() {
        return this.trades;
    }

    /**
     * Returns the room for this room processing contract.
     *
     * @return Value exposed by the contract.
     */
    public Room getRoom() {
        return room;
    }
}
