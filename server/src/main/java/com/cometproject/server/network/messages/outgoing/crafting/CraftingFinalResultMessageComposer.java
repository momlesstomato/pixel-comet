package com.cometproject.server.network.messages.outgoing.crafting;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.items.crafting.CraftingRecipe;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the crafting final result message for the Pixel Protocol client.
 */
public class CraftingFinalResultMessageComposer extends MessageComposer {
    private final boolean achieved;
    private final CraftingRecipe craftingRecipe;

    /**
     * Creates a crafting final result message composer instance for the network message subsystem.
     *
     * @param achieved Achieved supplied by the caller.
     * @param recipe Recipe supplied by the caller.
     */
    public CraftingFinalResultMessageComposer(boolean achieved, CraftingRecipe recipe) {
        this.achieved = achieved;
        this.craftingRecipe = recipe;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CraftingFinalResultMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeBoolean(achieved);
        msg.writeString(craftingRecipe.getResultProductData());
        msg.writeString(craftingRecipe.getResultProductData());
    }
}
