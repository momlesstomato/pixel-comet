package com.cometproject.server.game.items.types;

import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.furniture.types.ItemType;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes item definition behavior for the item subsystem.
 */
public class ItemDefinition implements FurnitureDefinition {
    private final int id;
    private final String publicName;
    private final String itemName;
    private final String type;
    private final ItemType itemType;
    private final int width;
    private final int length;
    private final double height;
    private final int spriteId;

    private final boolean canStack;
    private final boolean canSit;
    private final boolean canWalk;
    private final boolean canTrade;
    private final boolean canRecycle;
    private final boolean canMarket;
    private final boolean canGift;
    private final boolean canInventoryStack;

    private final int effectId;
    private final int offerId;
    private final String interaction;
    private final int interactionCycleCount;
    private final String[] vendingIds;
    private final boolean requiresRights;
    private final Double[] variableHeights;
    private int songId;

    /**
     * Creates a item definition instance for the item subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public ItemDefinition(ResultSet data) throws SQLException {
        this.id = data.getInt("id");
        this.publicName = data.getString("public_name");
        this.itemName = data.getString("item_name");
        this.type = data.getString("type");
        this.width = data.getInt("width");
        this.length = data.getInt("length");
        final double height = data.getDouble("stack_height");
        this.spriteId = data.getInt("sprite_id");

        this.canStack = data.getString("can_stack").equals("1");
        this.canSit = data.getString("can_sit").equals("1");
        this.canWalk = data.getString("is_walkable").equals("1");
        this.canTrade = data.getString("allow_trade").equals("1");
        this.canInventoryStack = data.getString("allow_inventory_stack").equals("1");

        this.offerId = data.getInt("flat_id");

        this.canRecycle = false;
        this.canMarket = false;
        this.canGift = data.getString("allow_gift").equals("1");

        this.effectId = data.getInt("effect_id");
        this.interaction = data.getString("interaction_type");
        this.interactionCycleCount = data.getInt("interaction_modes_count");
        this.vendingIds = data.getString("vending_ids").isEmpty() ? new String[0] : data.getString("vending_ids").split(",");

        this.requiresRights = data.getString("requires_rights").equals("1");

        this.songId = data.getInt("song_id");

        final String variableHeightData = data.getString("variable_heights");

        if (!variableHeightData.isEmpty() && variableHeightData.contains(",")) {
            String[] variableHeightArray = variableHeightData.split(",");
            this.variableHeights = new Double[variableHeightArray.length];

            for (int i = 0; i < variableHeightArray.length; i++) {
                try {
                    this.variableHeights[i] = Double.parseDouble(variableHeightArray[i]);
                } catch (Exception ignored) {

                }
            }
        } else {
            this.variableHeights = null;
        }

        if (height == 0.0 || height == 0) {
            this.height = 0.001;
        } else {
            this.height = height;
        }

        this.itemType = ItemType.forString(this.type);
    }

    /**
     * Indicates whether ad furni applies to this item contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isAdFurni() {
        return itemName.equals("ads_mpu_720") || this.itemName.equals("ads_background") || this.itemName.equals("ads_mpu_300") || this.itemName.equals("ads_mpu_160") || this.itemName.equals("backgroundk") || this.interaction.equals("ads_background");
    }

    /**
     * Indicates whether room decor applies to this item contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isRoomDecor() {
        return itemName.startsWith("wallpaper") || itemName.startsWith("landscape") || itemName.startsWith("a2 ");
    }

    /**
     * Indicates whether teleporter applies to this item contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isTeleporter() {
        return this.getInteraction().equals("teleport") || this.getInteraction().equals("teleport_door") || this.getInteraction().equals("teleport_pad");
    }

    /**
     * Indicates whether song applies to this item contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean isSong() {
        return this.songId != 0;
    }

    /**
     * Returns the id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the public name for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public String getPublicName() {
        return this.publicName;
    }

    /**
     * Returns the item name for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public String getItemName() {
        return this.itemName;
    }

    /**
     * Returns the type for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the item type for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public ItemType getItemType() {
        return this.itemType;
    }

    /**
     * Returns the width for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns the height for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Returns the sprite id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSpriteId() {
        return spriteId;
    }

    /**
     * Returns the length for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLength() {
        return length;
    }

    /**
     * Returns the interaction for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public String getInteraction() {
        return interaction;
    }

    /**
     * Returns the interaction cycle count for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getInteractionCycleCount() {
        return this.interactionCycleCount;
    }

    /**
     * Returns the effect id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getEffectId() {
        return effectId;
    }

    /**
     * Returns the vending ids for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public String[] getVendingIds() {
        return vendingIds;
    }

    /**
     * Returns the offer id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getOfferId() {
        return offerId;
    }

    /**
     * Indicates whether this item contract can stack.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canStack() {
        return canStack;
    }

    /**
     * Indicates whether this item contract can sit.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canSit() {
        return canSit;
    }

    /**
     * Indicates whether this item contract can walk.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canWalk() {
        return canWalk;
    }

    /**
     * Indicates whether this item contract can trade.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canTrade() {
        return canTrade;
    }

    /**
     * Indicates whether this item contract can recycle.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canRecycle() {
        return canRecycle;
    }

    /**
     * Indicates whether this item contract can market.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canMarket() {
        return canMarket;
    }

    /**
     * Indicates whether this item contract can gift.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canGift() {
        return canGift;
    }

    /**
     * Indicates whether this item contract can inventory stack.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean canInventoryStack() {
        return canInventoryStack;
    }

    /**
     * Returns the variable heights for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public Double[] getVariableHeights() {
        return variableHeights;
    }

    /**
     * Executes requires rights for this item contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public boolean requiresRights() {
        return requiresRights;
    }

    /**
     * Indicates whether wired applies to this item contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean isWired() {
        return this.getInteraction().startsWith("wtf_act") || this.getInteraction().startsWith("wtf_trg") || this.getInteraction().startsWith("wf_cnd");
    }

    /**
     * Returns the song id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSongId() {
        return this.songId;
    }
}
