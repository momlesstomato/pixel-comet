package com.cometproject.server.game.items.crafting;

import com.cometproject.api.game.achievements.types.AchievementType;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes crafting recipe behavior for the item subsystem.
 */
public class CraftingRecipe {

    /**
     * Enumerates recipe mode values used by the item subsystem.
     */
    public enum RecipeMode {
        PUBLIC,
        PRIVATE
    }

    private final int id;
    private RecipeMode mode;
    private final List<Integer> components = new ArrayList<>();
    private final String resultProductData;
    private final int resultBaseId;
    private final int resultLimitedSells;
    private int resultTotalCrafted;
    private final String badge;
    private final AchievementType achievement;

    /**
     * Creates a crafting recipe instance for the item subsystem.
     *
     * @param recipeId Recipe id supplied by the caller.
     * @param components Components supplied by the caller.
     * @param result Result supplied by the caller.
     * @param resultLimitedSells Result limited sells supplied by the caller.
     * @param resultTotalCrafted Result total crafted supplied by the caller.
     * @param badge Badge supplied by the caller.
     * @param achievement Achievement supplied by the caller.
     */
    public CraftingRecipe(int recipeId, String components, String result, int resultLimitedSells, int resultTotalCrafted, String badge, AchievementType achievement) {
        this.id = recipeId;

        for(String component : components.split(",")) {
            String[] c = component.split(":");
            for(int i = 0; i<Integer.parseInt(c[1]); i++) {
                this.components.add(Integer.valueOf(c[0]));
            }
        }

        String[] r = result.split(":");
        this.resultProductData = r[0];
        this.resultBaseId = Integer.parseInt(r[1]);
        this.resultLimitedSells = resultLimitedSells;
        this.resultTotalCrafted = resultTotalCrafted;
        this.badge = badge;
        this.achievement = achievement;
    }

    /**
     * Returns the id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() { return this.id; }

    /**
     * Returns the mode for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public RecipeMode getMode() { return this.mode; }

    /**
     * Returns the components for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public List<Integer> getComponents() { return this.components; }

    /**
     * Returns the result product data for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public String getResultProductData() { return this.resultProductData; }

    /**
     * Returns the result base id for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getResultBaseId() { return this.resultBaseId; }

    /**
     * Returns the result limited sells for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getResultLimitedSells() { return this.resultLimitedSells; }

    /**
     * Returns the result total crafted for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public int getResultTotalCrafted() { return this.resultTotalCrafted; }

    /**
     * Executes increate total crafted for this item contract.
     */
    public void increateTotalCrafted() { this.resultTotalCrafted++; }

    /**
     * Returns the badge for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public String getBadge() { return this.badge; }

    /**
     * Returns the achievement for this item contract.
     *
     * @return Value exposed by the contract.
     */
    public AchievementType getAchievement() { return this.achievement; }
}
