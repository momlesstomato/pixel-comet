package com.cometproject.game.items.inventory.items;

import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.furniture.types.FurnitureDefinition;
import com.cometproject.api.game.groups.types.IGroupData;
import com.cometproject.api.game.players.data.components.inventory.InventoryItemData;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.game.items.inventory.InventoryItem;
import org.apache.commons.lang3.StringUtils;

/**
 * Describes group inventory item behavior for the item subsystem.
 */
public class GroupInventoryItem extends InventoryItem {
    /**
     * Creates a group inventory item instance for the item subsystem.
     *
     * @param inventoryItemData Inventory item data supplied by the caller.
     * @param furnitureDefinition Furniture definition supplied by the caller.
     */
    public GroupInventoryItem(InventoryItemData inventoryItemData, FurnitureDefinition furnitureDefinition) {
        super(inventoryItemData, furnitureDefinition);
    }

    /**
     * Executes compose data for this item contract.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean composeData(IComposer msg) {
        int groupId = 0;

        msg.writeInt(17);

        if (StringUtils.isNumeric(this.getExtraData())) {
            groupId = Integer.parseInt(this.getExtraData());
        }

        IGroupData groupData = groupId == 0 ? null : GameContext.getCurrent().getGroupService().getData(groupId);

        if (groupData == null) {
            msg.writeInt(2);
            msg.writeInt(0);
        } else {
            msg.writeInt(2);
            msg.writeInt(5);
            msg.writeString("0"); //state
            msg.writeString(groupId);
            msg.writeString(groupData.getBadge());

            String colourA = GameContext.getCurrent().getGroupService().getItemService().getSymbolColours().get(groupData.getColourA()) != null ? GameContext.getCurrent().getGroupService().getItemService().getSymbolColours().get(groupData.getColourA()).getFirstValue() : "ffffff";
            String colourB = GameContext.getCurrent().getGroupService().getItemService().getBackgroundColours().get(groupData.getColourB()) != null ?  GameContext.getCurrent().getGroupService().getItemService().getBackgroundColours().get(groupData.getColourB()).getFirstValue() : "ffffff";

            msg.writeString(colourA);
            msg.writeString(colourB);
        }

        return true;
    }
}
