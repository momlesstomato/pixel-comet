package com.cometproject.api.game.rooms.objects.data;

/**
 * Describes limited edition item data behavior for the room subsystem.
 */
public class LimitedEditionItemData implements com.cometproject.api.game.furniture.types.LimitedEditionItem {
    public static final LimitedEditionItemData NONE = new LimitedEditionItemData(0, 0, 0);

    private long itemId;
    private int limitedRare;
    private int limitedRareTotal;

    /**
     * Creates a limited edition item data instance for the room subsystem.
     *
     * @param itemId Item id value supplied by the caller.
     * @param limitedRare Limited rare value supplied by the caller.
     * @param limitedRareTotal Limited rare total value supplied by the caller.
     */
    public LimitedEditionItemData(long itemId, int limitedRare, int limitedRareTotal) {
        this.itemId = itemId;
        this.limitedRare = limitedRare;
        this.limitedRareTotal = limitedRareTotal;
    }

    /**
     * Returns the item id for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Returns the limited rare for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLimitedRare() {
        return limitedRare;
    }

    /**
     * Returns the limited rare total for this room contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLimitedRareTotal() {
        return limitedRareTotal;
    }
}
