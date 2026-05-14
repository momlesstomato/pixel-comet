package com.cometproject.api.game.pets;

/**
 * Defines the i pet race contract for the pet subsystem.
 */
public interface IPetRace {
    /**
     * Returns the race id associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getRaceId();

    /**
     * Returns the colour1 associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getColour1();

    /**
     * Returns the colour2 associated with this pet contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getColour2();

    /**
     * Indicates whether this pet contract has colour1.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasColour1();

    /**
     * Indicates whether this pet contract has colour2.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean hasColour2();
}
