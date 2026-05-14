package com.cometproject.server.network.websockets.interfaces;

/**
 * Describes shop offer behavior for the networking subsystem.
 */
public class ShopOffer {
    private final int id;
    private final String name;
    private final int diamonds;
    private final int pixels;
    private final int days;
    private final int itemId;
    private final int amount;

    /**
     * Creates a shop offer instance for the networking subsystem.
     *
     * @param recipeId Recipe id supplied by the caller.
     * @param name Name supplied by the caller.
     * @param diamonds Diamonds supplied by the caller.
     * @param pixels Pixels supplied by the caller.
     * @param days Days supplied by the caller.
     * @param itemId Item id supplied by the caller.
     * @param amount Amount supplied by the caller.
     */
    public ShopOffer(int recipeId, String name, int diamonds, int pixels, int days, int itemId, int amount) {
        this.id = recipeId;
        this.name = name;
        this.diamonds = diamonds;
        this.pixels = pixels;
        this.days = days;
        this.itemId = itemId;
        this.amount = amount;
    }

    /**
     * Returns the id for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() { return id; }

    /**
     * Returns the name for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the diamonds for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDiamonds() {
        return diamonds;
    }

    /**
     * Returns the pixels for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public int getPixels() {
        return pixels;
    }

    /**
     * Returns the days for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public int getDays() {
        return days;
    }

    /**
     * Returns the item id for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Returns the amount for this networking contract.
     *
     * @return Value exposed by the contract.
     */
    public int getAmount() {
        return amount;
    }
}
