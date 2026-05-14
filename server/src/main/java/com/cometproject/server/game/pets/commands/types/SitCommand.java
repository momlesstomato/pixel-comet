package com.cometproject.server.game.pets.commands.types;

import com.cometproject.server.game.pets.commands.PetCommand;
import com.cometproject.server.game.rooms.objects.entities.types.PetEntity;
import com.cometproject.server.game.rooms.objects.entities.types.PlayerEntity;

/**
 * Describes sit command behavior for the pet subsystem.
 */
public class SitCommand extends PetCommand {
    /**
     * Executes execute for this pet contract.
     *
     * @param executor Executor supplied by the caller.
     * @param entity Entity supplied by the caller.
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean execute(PlayerEntity executor, PetEntity entity) {
        entity.cancelWalk();
        entity.getPetAI().sit();
        return true;
    }

    /**
     * Returns the required level for this pet contract.
     *
     * @return Value exposed by the contract.
     */
    @Override
    public int getRequiredLevel() {
        return 0;
    }

    /**
     * Executes requires owner for this pet contract.
     *
     * @return True when the condition is satisfied; otherwise false.
     */
    @Override
    public boolean requiresOwner() {
        return true;
    }
}
