package com.cometproject.server.game.items.crafting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Describes crafting machine behavior for the item subsystem.
 */
public class CraftingMachine {
    private final int baseId;
    private final Map<Integer, String> allowedItems;
    private final List<CraftingRecipe> publicRecipes;
    private final List<CraftingRecipe> secretRecipes;

    /**
     * Creates a crafting machine instance for the item subsystem.
     *
     * @param baseId Base id supplied by the caller.
     */
    public CraftingMachine(int baseId) {
        this.baseId = baseId;
        this.allowedItems = new HashMap<>();
        this.publicRecipes = new ArrayList<>();
        this.secretRecipes = new ArrayList<>();
    }

    /**
     * Returns the base id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getBaseId() { return this.baseId; }

    /**
     * Returns the allowed items for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public Map<Integer, String> getAllowedItems() { return this.allowedItems; }

    /**
     * Returns the public recipes for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public List<CraftingRecipe> getPublicRecipes() { return this.publicRecipes; }

    /**
     * Returns the secret recipes for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public List<CraftingRecipe> getSecretRecipes() { return this.secretRecipes; }

    /**
     * Returns the recipe by product data for this item contract.
     *
     * @param productdata Productdata supplied by the caller.
     * @return Value exposed by the contract.
     */
    public CraftingRecipe getRecipeByProductData(String productdata) {
        CraftingRecipe result = null;

        for(CraftingRecipe recipe : this.publicRecipes) {
            if(recipe.getResultProductData().equals(productdata))
                result = recipe;
        }

        return result;
    }
}
