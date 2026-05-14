package com.cometproject.api.game.utilities;

/**
 * Defines the positionable contract for the Comet subsystem.
 */
public interface Positionable {
    /**
     * Returns the position associated with this utility contract.
     *
     * @return Requested value, or the implementation-defined missing value documented by the contract.
     */
    Position getPosition();
}
