package com.cometproject.game.items.inventory;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.furniture.types.LimitedEditionItem;
import com.cometproject.api.game.players.data.components.inventory.InventoryItemData;
import com.cometproject.api.game.players.data.components.inventory.PlayerItem;
import com.cometproject.api.game.players.data.components.inventory.PlayerItemSnapshot;
import com.cometproject.api.networking.messages.IComposer;

/**
 * Describes inventory item behavior for the item subsystem.
 */
public class InventoryItem implements PlayerItem {

    private final InventoryItemData itemData;
    private final FurnitureDefinition furnitureDefinition;

    /**
     * Creates a inventory item instance for the item subsystem.
     *
     * @param itemData Item data supplied by the caller.
     * @param furnitureDefinition Furniture definition supplied by the caller.
     */
    public InventoryItem(InventoryItemData itemData, FurnitureDefinition furnitureDefinition) {
        this.itemData = itemData;
        this.furnitureDefinition = furnitureDefinition;
    }

    /**
     * Executes compose data for this item contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @return True when the condition is satisfied; otherwise false.
     */
    protected boolean composeData(IComposer msg) {
        msg.writeInt(1);

        if(this.itemData.getLimitedEditionItem() != null) {
            msg.writeString("");
            msg.writeBoolean(true);
            msg.writeBoolean(false);
        } else {
            msg.writeInt(0);
        }

        return false;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    public void compose(IComposer msg) {
        msg.writeInt(this.getVirtualId());
        msg.writeString(this.getDefinition().getType());
        msg.writeInt(this.getVirtualId());
        msg.writeInt(this.getSpriteId());

        if(!this.composeData(msg)) {
            msg.writeString(this.getExtraData());
        }

        if(this.itemData.getLimitedEditionItem() != null) {
            msg.writeInt(this.itemData.getLimitedEditionItem().getLimitedRare());
            msg.writeInt(this.itemData.getLimitedEditionItem().getLimitedRareTotal());
        }

        msg.writeBoolean(this.canRecycle());
        msg.writeBoolean(this.canTrade());
        msg.writeBoolean(this.itemData.getLimitedEditionItem() == null && this.canInventoryStack());
        msg.writeBoolean(this.canMarketplace());

        msg.writeInt(-1);
        msg.writeBoolean(true);
        msg.writeInt(-1);
        msg.writeString("");
        msg.writeInt(this.getExtraInt());
    }

    /**
     * Creates snapshot for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public PlayerItemSnapshot createSnapshot() {
        return null;
    }

    private boolean canTrade() {
        return this.furnitureDefinition.canTrade();
    }

    private boolean canRecycle() {
        return this.furnitureDefinition.canRecycle();
    }

    private boolean canInventoryStack() {
        return this.furnitureDefinition.canInventoryStack();
    }

    private boolean canMarketplace() {
        return this.furnitureDefinition.canMarket();
    }

    /**
     * Returns the extra int for this item contract.
     *
     * @return Value exposed by the contract.
     */
    protected int getExtraInt() {
        return 0;
    }

    /**
     * Returns the sprite id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    protected int getSpriteId() {
        return this.furnitureDefinition.getSpriteId();
    }

    /**
     * Returns the id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public long getId() {
        return this.itemData.getId();
    }

    /**
     * Returns the definition for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public FurnitureDefinition getDefinition() {
        return this.furnitureDefinition;
    }

    /**
     * Returns the base id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getBaseId() {
        return this.itemData.getBaseId();
    }

    /**
     * Returns the extra data for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public String getExtraData() {
        return this.itemData.getExtraData();
    }

    /**
     * Returns the limited edition item for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public LimitedEditionItem getLimitedEditionItem() {
        return this.itemData.getLimitedEditionItem();
    }

    /**
     * Returns the virtual id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getVirtualId() {
        return GameContext.getCurrent().getFurnitureService().getItemVirtualId(this.itemData.getId());
    }
}