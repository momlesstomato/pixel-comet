package com.cometproject.api.game.furniture.types;

import com.cometproject.api.game.players.data.components.inventory.PlayerItemSnapshot;

/**
 * Defines the song item contract for the furniture subsystem.
 */
public interface SongItem {

    /**
     * Returns the song id associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSongId();

    /**
     * Returns the item snapshot associated with this furniture contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    PlayerItemSnapshot getItemSnapshot();
}
