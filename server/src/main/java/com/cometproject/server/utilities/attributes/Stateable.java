package com.cometproject.server.utilities.attributes;

/**
 * Defines the stateable contract for the Comet subsystem.
 */
public interface Stateable {
    /**
     * Returns the state for this Comet contract.
     *
     * @return Value exposed by the contract.
     */
    boolean getState();
}
