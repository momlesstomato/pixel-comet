package com.cometproject.server.game.pets.races.plants;


/**
 * Describes pet monster plant behavior for the pet subsystem.
 */
public class PetMonsterPlant {

    private String name;
    private int rarity;
    private int lifeTime;
    private int id;
    private int growthTime;

    /**
     * Creates a pet monster plant instance for the pet subsystem.
     *
     * @param id Id supplied by the caller.
     * @param name Name supplied by the caller.
     * @param rarity Rarity supplied by the caller.
     * @param lifeTime Life time supplied by the caller.
     * @param growthTime Growth time supplied by the caller.
     */
    public PetMonsterPlant(int id, String name, int rarity, int lifeTime, int growthTime) {
        this.name = name;
        this.rarity = rarity;
        this.lifeTime = lifeTime;
        this.id = id;
        this.growthTime = growthTime;
    }

    /**
     * Returns the growth time for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getGrowthTime() {
        return growthTime;
    }

    /**
     * Returns the name for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public String getName() {
        return name;
    }

    /**
     * Updates the name for this pet contract.
     *
     * @param name Name supplied by the caller.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the life time for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getLifeTime() {
        return lifeTime;
    }

    /**
     * Returns the id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the rarity for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getRarity() {
        return rarity;
    }


}
