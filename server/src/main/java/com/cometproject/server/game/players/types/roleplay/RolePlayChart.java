package com.cometproject.server.game.players.types.roleplay;

/**
 * Describes role play chart behavior for the player subsystem.
 */
public class RolePlayChart {
    private int id;
    private String productname;
    private int itemId;
    private int cost;
    private int currency;
    private String type;
    private String image;

    /**
     * Creates a role play chart instance for the player subsystem.
     *
     * @param id Id supplied by the caller.
     * @param productname Productname supplied by the caller.
     * @param itemId Item id supplied by the caller.
     * @param cost Cost supplied by the caller.
     * @param currency Currency supplied by the caller.
     * @param type Type supplied by the caller.
     * @param image Image supplied by the caller.
     */
    public RolePlayChart(int id, String productname, int itemId, int cost, int currency, String type, String image){
        this.id = id;
        this.productname = productname;
        this.itemId = itemId;
        this.cost = cost;
        this.currency = currency;
        this.type = type;
        this.image = image;
    }

    /**
     * Returns the id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the productname for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getProductname() {
        return this.productname;
    }

    /**
     * Returns the item id for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getItemId() {
        return this.itemId;
    }

    /**
     * Returns the cost for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCost() {
        return this.cost;
    }

    /**
     * Returns the currency for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getCurrency() {
        return this.currency;
    }

    /**
     * Returns the type for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the image for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getImage() {
        return this.image;
    }
}
