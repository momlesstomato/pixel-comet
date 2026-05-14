package com.cometproject.storage.api.repositories;

import com.cometproject.api.game.players.data.components.inventory.PlayerItem;

import java.util.List;
import java.util.function.Consumer;

/**
 * Defines the i inventory repository contract for the storage subsystem.
 */
public interface IInventoryRepository {

    /**
     * Returns the inventory by player id associated with this storage contract.
     *
     * @param playerId Player id value supplied by the caller.
     * @param itemConsumer Item consumer value supplied by the caller.
     */
    void getInventoryByPlayerId(int playerId, Consumer<List<PlayerItem>> itemConsumer);

}
