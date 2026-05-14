package com.cometproject.api.game.players.data.types;

/**
 * Defines the i volume data contract for the player subsystem.
 */
public interface IVolumeData {
    /**
     * Returns the system volume associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getSystemVolume();

    /**
     * Updates the system volume value for this player contract.
     *
     * @param systemVolume System volume value supplied by the caller.
     */
    void setSystemVolume(int systemVolume);

    /**
     * Returns the furni volume associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getFurniVolume();

    /**
     * Updates the furni volume value for this player contract.
     *
     * @param furniVolume Furni volume value supplied by the caller.
     */
    void setFurniVolume(int furniVolume);

    /**
     * Returns the trax volume associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    int getTraxVolume();

    /**
     * Updates the trax volume value for this player contract.
     *
     * @param traxVolume Trax volume value supplied by the caller.
     */
    void setTraxVolume(int traxVolume);
}
