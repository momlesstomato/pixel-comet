package com.cometproject.server.game.players.components.types.inventory;

import com.cometproject.api.game.players.data.components.inventory.PlayerItemSnapshot;

/**
 * Carries inventory item snapshot data for the player subsystem.
 */
public class InventoryItemSnapshot implements PlayerItemSnapshot {
    private long id;
    private int baseItemId;
    private String extraData;

    /**
     * Creates a inventory item snapshot instance for the player subsystem.
     *
     * @param id Id supplied by the caller.
     * @param baseItemId Base item id supplied by the caller.
     * @param extraData Extra data supplied by the caller.
     */
    public InventoryItemSnapshot(long id, int baseItemId, String extraData) {
        this.id = id;
        this.baseItemId = baseItemId;
        this.extraData = extraData;
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the base item id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBaseItemId() {
        return baseItemId;
    }

    /**
     * Returns the extra data for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getExtraData() {
        return extraData;
    }
}
