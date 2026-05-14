package com.cometproject.server.game.pets.commands;

import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;

/**
 * Describes pet command behavior for the pet subsystem.
 */
public abstract class PetCommand {
    /**
     * Executes execute for this pet contract.
     *
     * @param executor Executor supplied by the caller.
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    public abstract boolean execute(PlayerEntity executor, PetEntity entity);

    /**
     * Returns the required level for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    public abstract int getRequiredLevel();

    /**
     * Executes requires owner for this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    public abstract boolean requiresOwner();

    /**
     * Executes experience gain for this pet contract.
     *
     * @return Result produced by the operation.
     */
    public int experienceGain() {
        return 0;
    }
}
