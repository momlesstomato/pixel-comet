package com.cometproject.api.game.players.data.types;

import com.google.gson.JsonElement;

/**
 * Defines the i wardrobe item contract for the player subsystem.
 */
public interface IWardrobeItem {
    /**
     * Returns the slot associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSlot();

    /**
     * Updates the slot value for this player contract.
     *
     * @param slot Slot value supplied by the caller.
     */
    void setSlot(int slot);

    /**
     * Returns the gender associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getGender();

    /**
     * Updates the gender value for this player contract.
     *
     * @param gender Gender value supplied by the caller.
     */
    void setGender(String gender);

    /**
     * Returns the figure associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    String getFigure();

    /**
     * Updates the figure value for this player contract.
     *
     * @param figure Figure value supplied by the caller.
     */
    void setFigure(String figure);

    /**
     * Executes the to JSON operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    JsonElement toJson();
}
