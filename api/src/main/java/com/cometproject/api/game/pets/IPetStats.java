package com.cometproject.api.game.pets;

/**
 * Defines the i pet stats contract for the pet subsystem.
 */
public interface IPetStats {

    /**
     * Returns the id associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getId();

    /**
     * Returns the level associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getLevel();

    /**
     * Returns the happiness associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getHappiness();

    /**
     * Returns the experience associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getExperience();

    /**
     * Returns the experience goal associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getExperienceGoal();

    /**
     * Returns the energy associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getEnergy();

    /**
     * Returns the scratches associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getScratches();

    /**
     * Returns the hunger associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getHunger();

}
