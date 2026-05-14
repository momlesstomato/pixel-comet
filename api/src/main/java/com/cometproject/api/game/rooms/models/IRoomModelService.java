package com.cometproject.api.game.rooms.models;

/**
 * Defines the i room model service contract for the room subsystem.
 */
public interface IRoomModelService {

    /**
     * Loads models data for this room contract.
     */
    void loadModels();

    /**
     * Returns the model associated with this room contract.
     *
     * @param id Id value supplied by the caller.
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IRoomModel getModel(String id);

    /**
     * Returns the room model factory associated with this room contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    IRoomModelFactory getRoomModelFactory();
}
