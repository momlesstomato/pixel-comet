package com.cometproject.server.network.messages.outgoing.crafting;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

/**
 * Serializes the crafting recipe result message for the Pixel Protocol client.
 */
public class CraftingRecipeResultMessageComposer extends MessageComposer {
    private final int recipes;
    private final boolean found;

    /**
     * Creates a crafting recipe result message composer instance for the network message subsystem.
     *
     * @param recipes Recipes supplied by the caller.
     * @param found Found supplied by the caller.
     */
    public CraftingRecipeResultMessageComposer(Integer recipes, boolean found) {
        this.recipes = recipes;
        this.found = found;
    }

    /**
     * Returns the id for this network message contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public short getId() {
        return Composers.CraftingRecipeResultMessageComposer;
    }

    /**
     * Writes this message body using the Pixel Protocol field order.
     *
     * @param msg Composer buffer that receives serialized protocol fields.
     */
    @Override
    public void compose(IComposer msg) {
        msg.writeInt(recipes);
        msg.writeBoolean(found);
    }
}
