package com.cometproject.server.game.players.components.types.settings;

import com.cometproject.api.game.players.data.types.IWardrobeItem;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Describes wardrobe item behavior for the player subsystem.
 */
public class WardrobeItem implements IWardrobeItem {
    private int slot;
    private String gender;
    private String figure;

    /**
     * Creates a wardrobe item instance for the player subsystem.
     *
     * @param slot Slot supplied by the caller.
     * @param gender Gender supplied by the caller.
     * @param figure Figure supplied by the caller.
     */
    public WardrobeItem(int slot, String gender, String figure) {
        this.slot = slot;
        this.gender = gender;
        this.figure = figure;
    }

    /**
     * Returns the slot for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSlot() {
        return slot;
    }

    /**
     * Updates the slot for this player contract.
     *
     * @param slot Slot supplied by the caller.
     */
    public void setSlot(int slot) {
        this.slot = slot;
    }

    /**
     * Returns the gender for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Updates the gender for this player contract.
     *
     * @param gender Gender supplied by the caller.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Returns the figure for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public String getFigure() {
        return figure;
    }

    /**
     * Updates the figure for this player contract.
     *
     * @param figure Figure supplied by the caller.
     */
    public void setFigure(String figure) {
        this.figure = figure;
    }

    /**
     * Executes to JSON for this player contract.
     *
     * @return Result produced by the operation.
     */
    @Override
    public JsonElement toJson() {
        final JsonObject coreObject = new JsonObject();

        coreObject.addProperty("slot", slot);
        coreObject.addProperty("gender", gender);
        coreObject.addProperty("figure", figure);

        return coreObject;
    }
}
