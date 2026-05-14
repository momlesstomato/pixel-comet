package com.cometproject.server.game.players.components.types.settings;

import com.cometproject.api.game.players.data.types.IVolumeData;
import com.google.gson.JsonObject;

/**
 * Carries volume data data for the player subsystem.
 */
public class VolumeData implements IVolumeData {
    private int systemVolume;
    private int furniVolume;
    private int traxVolume;

    /**
     * Creates a volume data instance for the player subsystem.
     *
     * @param systemVolume System volume supplied by the caller.
     * @param furniVolume Furni volume supplied by the caller.
     * @param traxVolume Trax volume supplied by the caller.
     */
    public VolumeData(int systemVolume, int furniVolume, int traxVolume) {
        this.systemVolume = systemVolume;
        this.furniVolume = furniVolume;
        this.traxVolume = traxVolume;
    }

    /**
     * Returns the system volume for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getSystemVolume() {
        return systemVolume;
    }

    /**
     * Updates the system volume for this player contract.
     *
     * @param systemVolume System volume supplied by the caller.
     */
    public void setSystemVolume(int systemVolume) {
        this.systemVolume = systemVolume;
    }

    /**
     * Returns the furni volume for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getFurniVolume() {
        return furniVolume;
    }

    /**
     * Updates the furni volume for this player contract.
     *
     * @param furniVolume Furni volume supplied by the caller.
     */
    public void setFurniVolume(int furniVolume) {
        this.furniVolume = furniVolume;
    }

    /**
     * Returns the trax volume for this player contract.
     *
     * @return Value exposed by the contract.
     */
    public int getTraxVolume() {
        return traxVolume;
    }

    /**
     * Updates the trax volume for this player contract.
     *
     * @param traxVolume Trax volume supplied by the caller.
     */
    public void setTraxVolume(int traxVolume) {
        this.traxVolume = traxVolume;
    }

    /**
     * Executes to JSON for this player contract.
     *
     * @return Result produced by the operation.
     */
    public JsonObject toJson() {
        final JsonObject coreObect = new JsonObject();

        coreObect.addProperty("systemVolume", systemVolume);
        coreObect.addProperty("furniVolume", furniVolume);
        coreObect.addProperty("traxVolume", traxVolume);

        return coreObect;
    }
}
