package com.cometproject.server.game.pets.races.plants;

/**
 * Describes pet monster plant color behavior for the pet subsystem.
 */
public class PetMonsterPlantColor {

    private String name;
    private int id;

    /**
     * Creates a pet monster plant color instance for the pet subsystem.
     *
     * @param name Name supplied by the caller.
     * @param id Id supplied by the caller.
     */
    public PetMonsterPlantColor(String name, int id) {
        this.name = name;
        this.id = id;
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
     * Returns the id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public int getId() {
        return id;
    }


}