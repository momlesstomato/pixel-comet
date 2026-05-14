package com.cometproject.api.game.rooms.models;

/**
 * Defines the i room model factory contract for the room subsystem.
 */
public interface IRoomModelFactory {

    /**
     * Creates model data for this room contract.
     *
     * @param roomModelData Room model data value supplied by the caller.
     * @return Result produced by the mutation.
     * @throws InvalidModelException When the implementation cannot complete the operation.
     */
    IRoomModel createModel(RoomModelData roomModelData) throws InvalidModelException;

}
