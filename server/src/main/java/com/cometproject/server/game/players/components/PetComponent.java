package com.cometproject.server.game.players.components;

import com.cometproject.api.game.pets.IPetData;
import com.cometproject.api.game.players.IPlayer;
import com.cometproject.api.game.players.data.components.PlayerPets;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.game.players.types.PlayerComponent;
import com.cometproject.server.storage.queries.pets.PetDao;

import java.util.Map;


/**
 * Owns pet behavior inside the player subsystem.
 */
public class PetComponent extends PlayerComponent implements PlayerPets {
    private Player player;
    private Map<Integer, IPetData> pets;

    /**
     * Creates a pet component instance for the player subsystem.
     *
     * @param player Player participating in the operation.
     */
    public PetComponent(IPlayer player) {
        super(player);

        this.pets = PetDao.getPetsByPlayerId(player.getId());
    }

    /**
     * Returns the pet for this player contract.
     *
     * @param id Id supplied by the caller.
     * @return Value exposed by the contract.
     */
    @Override
    public IPetData getPet(int id) {
        if (this.getPets().containsKey(id)) {
            return this.getPets().get(id);
        }

        return null;
    }

    /**
     * Executes clear pets for this player contract.
     */
    public void clearPets() {
        this.pets.clear();
    }

    /**
     * Executes flush for this player contract.
     */
    @Override
    public void flush() {
        this.pets.clear();
        this.pets = PetDao.getPetsByPlayerId(getPlayer().getId());
    }


    /**
     * Adds pet to this player contract.
     *
     * @param petData Pet data supplied by the caller.
     */
    public void addPet(IPetData petData) {
        this.pets.put(petData.getId(), petData);

        //this.getPlayer().flush();
    }

    /**
     * Removes pet from this player contract.
     *
     * @param id Id supplied by the caller.
     */
    public void removePet(int id) {
        this.pets.remove(id);

        //this.getPlayer().flush();
    }

    /**
     * Returns the player for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public IPlayer getPlayer() {
        return this.player;
    }

    /**
     * Releases resources owned by this player component.
     */
    public void dispose() {
        this.pets.clear();
        this.pets = null;
        this.player = null;
    }

    /**
     * Returns the pets for this player contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public Map<Integer, IPetData> getPets() {
        return this.pets;
    }
}
