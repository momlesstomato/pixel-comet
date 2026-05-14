package com.cometproject.api.game.players.data.components;

import com.cometproject.api.game.pets.IPetData;
import com.cometproject.api.game.players.data.IPlayerComponent;

import java.util.Map;

/**
 * Defines the player pets contract for the player subsystem.
 */
public interface PlayerPets extends IPlayerComponent {
    /**
     * Returns the pet associated with this player contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IPetData getPet(int id);

    /**
     * Executes the clear pets operation for this player contract.
     */
    void clearPets();

    /**
     * Executes the flush operation for this player contract.
     */
    void flush();

    /**
     * Returns the pets associated with this player contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Map<Integer, IPetData> getPets();

    /**
     * Removes pet data from this player contract.
     *
     * @param id Id value supplied by the caller.
     */
    void removePet(int id);

    /**
     * Adds pet data to this player contract.
     *
     * @param pet Pet value supplied by the caller.
     */
    void addPet(IPetData pet);


}
