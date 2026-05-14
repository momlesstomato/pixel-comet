package com.cometproject.server.network.messages.outgoing.crafting;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.items.crafting.CraftingMachine;
import com.cometproject.server.game.items.crafting.CraftingRecipe;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the craftable products message for the Pixel Protocol client.
 */
public class CraftableProductsMessageComposer extends MessageComposer {
    private final CraftingMachine machine;

    /**
     * Creates a craftable products message composer instance for the network message subsystem.
     *
     * @param machine Machine supplied by the caller.
     */
    public CraftableProductsMessageComposer(CraftingMachine machine) {
        this.machine = machine;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CraftableProductsMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        if(this.machine == null){
            msg.writeInt(0);
            msg.writeInt(0);
            return;
        }

        if(this.machine.getAllowedItems() == null || this.machine.getPublicRecipes() == null){
            msg.writeInt(0);
            msg.writeInt(0);
            return;
        }

        msg.writeInt(this.machine.getPublicRecipes().size());
        for(CraftingRecipe recipe : this.machine.getPublicRecipes()) {
            msg.writeString(recipe.getResultProductData());
            msg.writeString(recipe.getResultProductData());
        }

        msg.writeInt(this.machine.getAllowedItems().size());
        for(String productdata : this.machine.getAllowedItems().values()) {
            msg.writeString(productdata);
        }
    }
}
