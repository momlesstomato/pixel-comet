package com.cometproject.server.game.pets.races;

import com.cometproject.api.game.pets.IPetRace;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Describes pet race behavior for the pet subsystem.
 */
public class PetRace implements IPetRace {
    private int raceId;
    private int colour1;
    private int colour2;

    private boolean hasColour1;
    private boolean hasColour2;

    /**
     * Creates a pet race instance for the pet subsystem.
     *
     * @param data Data supplied by the caller.
     * @throws SQLException When the operation cannot complete.
     */
    public PetRace(ResultSet data) throws SQLException {
        this.raceId = data.getInt("race_id");

        this.colour1 = data.getInt("colour1");
        this.colour2 = data.getInt("colour2");

        this.hasColour1 = data.getString("has1colour").equals("1");
        this.hasColour2 = data.getString("has2colour").equals("1");
    }

    /**
     * Returns the race id for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRaceId() {
        return raceId;
    }

    /**
     * Returns the colour1 for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getColour1() {
        return colour1;
    }

    /**
     * Returns the colour2 for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getColour2() {
        return colour2;
    }

    /**
     * Indicates whether this pet contract has colour1.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasColour1() {
        return hasColour1;
    }

    /**
     * Indicates whether this pet contract has colour2.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean hasColour2() {
        return hasColour2;
    }
}