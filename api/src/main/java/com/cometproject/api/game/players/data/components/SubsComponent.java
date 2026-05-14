package com.cometproject.api.game.players.data.components;

import com.cometproject.api.networking.messages.IMessageComposer;

/**
 * Defines the subs component contract for the player subsystem.
 */
public interface SubsComponent {
    /**
     * Executes the add operation for this player contract.
     *
     * @param days Days value supplied by the caller.
     */
    void add(int days);
    /**
     * Executes the delete operation for this player contract.
     */
    void delete();

    /**
     * Indicates whether valid is enabled for this player contract.
     *
     * @return True when the contract condition is satisfied; otherwise false.
     */
    boolean isValid();
    /**
     * Executes the deliver operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    IMessageComposer deliver();
    /**
     * Executes the confirm operation for this player contract.
     *
     * @return Result produced by the operation.
     */
    IMessageComposer confirm();
    /**
     * Executes the update operation for this player contract.
     *
     * @return Result produced by the mutation.
     */
    IMessageComposer update();
}
