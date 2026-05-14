package com.cometproject.server.utilities.attributes;

import com.cometproject.server.game.rooms.objects.entities.RoomEntity;


/**
 * Defines the collidable contract for the Comet subsystem.
 */
public interface Collidable {
    /**
     * Returns the collision for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    RoomEntity getCollision();

    /**
     * Updates the collision for this Comet contract.
     *
     * @param entity Entity supplied by the caller.
     */
    void setCollision(RoomEntity entity);

    /**
     * Executes nullify collision for this Comet contract.
     */
    void nullifyCollision();
}
