package com.cometproject.server.game.items.music;

import com.cometproject.api.game.furniture.types.SongItem;
import com.cometproject.api.game.players.data.components.inventory.PlayerItemSnapshot;
import com.cometproject.server.game.players.components.types.inventory.InventoryItemSnapshot;

/**
 * Carries song item data data for the item subsystem.
 */
public class SongItemData implements SongItem {

    private InventoryItemSnapshot itemSnapshot;
    private int songId;

    /**
     * Creates a song item data instance for the item subsystem.
     *
     * @param itemSnapshot Item snapshot supplied by the caller.
     * @param songId Song id supplied by the caller.
     */
    public SongItemData(InventoryItemSnapshot itemSnapshot, int songId) {
        this.itemSnapshot = itemSnapshot;
        this.songId = songId;
    }

    /**
     * Returns the song id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSongId() {
        return songId;
    }

    /**
     * Updates the song id for this item contract.
     *
     * @param songId Song id supplied by the caller.
     */
    public void setSongId(int songId) {
        this.songId = songId;
    }

    /**
     * Returns the item snapshot for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public PlayerItemSnapshot getItemSnapshot() {
        return itemSnapshot;
    }
}
