package com.cometproject.api.game.players.data.components.inventory;

import com.cometproject.api.game.furniture.types.LimitedEditionItem;

/**
 * Describes inventory item data behavior for the player subsystem.
 */
public final class InventoryItemData {

    private final long id;
    private final int baseId;
    private final String extraData;
    private final LimitedEditionItem limitedEditionItem;

    /**
     * Creates a inventory item data instance for the player subsystem.
     *
     * @param id Id value supplied by the caller.
     * @param baseId Base id value supplied by the caller.
     * @param extraData Extra data value supplied by the caller.
     * @param limitedEditionItem Limited edition item value supplied by the caller.
     */
    public InventoryItemData(long id, int baseId, String extraData, LimitedEditionItem limitedEditionItem) {
        this.id = id;
        this.baseId = baseId;
        this.extraData = extraData;
        this.limitedEditionItem = limitedEditionItem;
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
     * Returns the base id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBaseId() {
        return baseId;
    }

    /**
     * Returns the extra data for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getExtraData() {
        return extraData;
    }

    /**
     * Returns the limited edition item for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public LimitedEditionItem getLimitedEditionItem() {
        return limitedEditionItem;
    }
}
